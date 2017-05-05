package se.paap.photoapp.network;

import android.os.AsyncTask;

import java.util.List;

import se.paap.photoapp.model.GalleryItem;

public final class FetchPhotosTask extends AsyncTask<Integer, Void, List<GalleryItem>> {
    private final FlickrApi api;
    private final OnResultListener listener;
    private final String query;

    public FetchPhotosTask(String query, OnResultListener listener) {
        this.api = new FlickrApi();
        this.listener = listener;
        this.query = query;
    }

    @Override
    protected List<GalleryItem> doInBackground(Integer... params) {
        int page = params[0];

        if(query == null) {
            return api.getRecent(page);
        } else {
            return api.search(page, query);
        }
    }

    @Override
    protected void onPostExecute(List<GalleryItem> galleryItems) {
        listener.onResult(galleryItems);
    }

    public interface OnResultListener {
        void onResult(List<GalleryItem> galleryItems);
    }
}
