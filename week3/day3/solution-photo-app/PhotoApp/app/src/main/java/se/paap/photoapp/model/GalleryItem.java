package se.paap.photoapp.model;

import com.google.gson.annotations.SerializedName;

public class GalleryItem {
    private final String id;
    private final String title;
    @SerializedName("url_s") private final String thumbnailUrl;

    public GalleryItem(String id, String title, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %s", id, title, thumbnailUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GalleryItem that = (GalleryItem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return thumbnailUrl != null ? thumbnailUrl.equals(that.thumbnailUrl) : that.thumbnailUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (thumbnailUrl != null ? thumbnailUrl.hashCode() : 0);
        return result;
    }
}
