package se.paap.examplephotoapp.network;

import android.os.AsyncTask;

import java.util.List;

import se.paap.examplephotoapp.model.GalleryItem;

public final class FetchPhotosTask extends AsyncTask<Void, Void, List<GalleryItem>> {
    private final FlickrApi api;
    private final OnResultListener listener;

    public FetchPhotosTask(OnResultListener listener) {
        this.api = new FlickrApi();
        this.listener = listener;
    }

    @Override
    protected List<GalleryItem> doInBackground(Void... params) {
        return api.getRecent();
    }

    @Override
    protected void onPostExecute(List<GalleryItem> galleryItems) {
        listener.onResult(galleryItems);
    }

    public interface OnResultListener {
        void onResult(List<GalleryItem> galleryItems);
    }
}
