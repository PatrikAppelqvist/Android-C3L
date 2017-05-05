package se.paap.examplephotoapp.network;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

import se.paap.examplephotoapp.model.GalleryItem;

public final class FlickrApi {
    private static final String API_KEY = "97d62cc8aa88bdc982daf588c0ea8d8f";
    private static final Uri BASE_URI = Uri
            .parse("https://api.flickr.com/services/rest")
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
            .build();

    private static final String RECENT_METHOD_NAME = "flickr.photos.getRecent";

    private final Gson gson;

    public FlickrApi() {
        gson = new Gson();
    }

    public List<GalleryItem> getRecent() {
        String url = buildUrl(RECENT_METHOD_NAME);
        return downloadGalleryItems(url);
    }

    private List<GalleryItem> downloadGalleryItems(String url) {
        HttpResponse response = HttpHelper.get(url);

        if(response != null && response.getStatusCode() == HttpURLConnection.HTTP_OK) {
            return parseResponse(response.getAsJsonObject());
        }

        return Collections.emptyList();
    }

    private List<GalleryItem> parseResponse(JsonObject jsonObject) {
        JsonObject photos = jsonObject.get("photos").getAsJsonObject();
        JsonArray photoList = photos.get("photo").getAsJsonArray();
        Type type = new TypeToken<List<GalleryItem>>() {}.getType();

        return gson.fromJson(photoList, type);
    }

    private String buildUrl(String method) {
        Uri.Builder builder = BASE_URI.buildUpon()
                .appendQueryParameter("method", method);

        return builder.build().toString();
    }
}
