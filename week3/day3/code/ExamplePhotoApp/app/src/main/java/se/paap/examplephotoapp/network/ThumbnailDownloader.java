package se.paap.examplephotoapp.network;

import android.os.HandlerThread;

import se.paap.examplephotoapp.model.ThumbnailHolder;

public class ThumbnailDownloader<T extends ThumbnailHolder> extends HandlerThread {
    private static final String TAG = ThumbnailDownloader.class.getSimpleName();

    public ThumbnailDownloader() {
        super(TAG);
    }
}
