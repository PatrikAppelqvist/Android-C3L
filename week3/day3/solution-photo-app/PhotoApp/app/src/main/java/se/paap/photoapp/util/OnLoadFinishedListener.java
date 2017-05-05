package se.paap.photoapp.util;

import java.util.List;

import se.paap.photoapp.model.GalleryItem;

public interface OnLoadFinishedListener {
    void onLoadFinished(List<GalleryItem> result);
}
