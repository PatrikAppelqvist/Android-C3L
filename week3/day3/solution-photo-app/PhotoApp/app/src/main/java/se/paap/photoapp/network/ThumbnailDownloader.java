package se.paap.photoapp.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.LruCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import se.paap.photoapp.model.ThumbnailHolder;

public class ThumbnailDownloader<T extends ThumbnailHolder> extends HandlerThread {
    private static final String TAG = ThumbnailDownloader.class.getSimpleName();
    private static final int HANDLE_DOWNLOAD = 1;

    private final Handler responseHandler;
    private final Context context;

    private boolean hasQuit = false;
    private Handler requestHandler;
    private ConcurrentMap<T, String> requestMap = new ConcurrentHashMap<>();

    private final LruCache<String, Bitmap> cache;

    public ThumbnailDownloader(Context context, Handler responseHandler) {
        super(TAG);
        this.responseHandler = responseHandler;
        this.context = context;
        this.cache = new LruCache<>(200);
    }

    public void queueThumbnail(T target, String url) {
        if(url == null) {
            requestMap.remove(target);
        } else {
            requestMap.put(target, url);
            requestHandler.obtainMessage(HANDLE_DOWNLOAD, target).sendToTarget();
        }
    }

    @Override
    public boolean quit() {
        hasQuit = true;
        clearQueue();

        return super.quit();
    }

    private void clearQueue() {
        requestHandler.removeMessages(HANDLE_DOWNLOAD);
        requestMap.clear();
    }

    @Override
    protected void onLooperPrepared() {
        requestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == HANDLE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    handleRequest(target);
                }
            }
        };
    }

    private void handleRequest(final T target) {
        final String url = requestMap.get(target);

        if(url == null) {
            return;
        }

        final Bitmap bitmapImage = downloadAndCacheThumbnail(url);

        responseHandler.post(new Runnable() {
            @Override
            public void run() {
                String requestUrl = requestMap.get(target);

                if(requestUrl != null && !(requestUrl.equals(url)) || hasQuit) {
                    return;
                }

                requestMap.remove(target);

                Drawable drawable = new BitmapDrawable(context.getResources(), bitmapImage);
                target.bindThumbnail(drawable);
            }
        });
    }

    private Bitmap downloadAndCacheThumbnail(String url) {
        // Check if bitmap exists in cache, if so return that image directly from cache.
        Bitmap fromCache = cache.get(url);
        if(fromCache != null) {
            return fromCache;
        }

        byte[] image = HttpHelper.get(url).getResponse();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        // Add to cache after downloading
        cache.put(url, bitmap);

        return bitmap;
    }
}
