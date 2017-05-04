package se.paap.networkuserlistapplication.http.api;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.paap.networkuserlistapplication.http.HttpClient;
import se.paap.networkuserlistapplication.http.HttpResponse;
import se.paap.networkuserlistapplication.http.RequestConfiguration;
import se.paap.networkuserlistapplication.model.User;

public class Api {
    private static final String BASE_URL = "http://10.0.2.2:3000";
    private static final String TAG = Api.class.getSimpleName();

    private final HttpClient httpClient;

    public Api() {
        this.httpClient = new HttpClient();
    }

    public User getUser(long id) {
        String url = getUrl("users/" + id);
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONObject json = response.getResponseAsJsonObject();
            return parseUser(json);
        }

        return null;
    }

    public List<User> getUsers() {
        String url = getUrl("users");
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONArray jsonArray = response.getResponseAsJsonArray();
            if (jsonArray == null) {
                return Collections.emptyList();
            }

            return parseUsers(jsonArray);
        }

        return Collections.emptyList();
    }

    public long addUser(User user) {
        String url = getUrl("users");

        RequestConfiguration requestConfiguration = new RequestConfiguration
                .Builder()
                .setBody(getUserAsJsonString(user))
                .setHeader("Content-type", "application/json")
                .build();

        HttpResponse response = httpClient.post(url, requestConfiguration);
        String location = response.getHeaders().get("location").get(0);
        Log.d(TAG, "addUser: " + location);

        if(response.getStatusCode() == 200) {
            User addedUser = parseUser(response.getResponseAsJsonObject());
            if(addedUser != null) {
                return addedUser.getId();
            }
        }

        return -1;
    }

    public long updateUser(User user) {
        String url = getUrl("users/" + user.getId());
        RequestConfiguration configuration = new RequestConfiguration
                .Builder()
                .setBody(getUserAsJsonString(user))
                .setHeader("Content-type", "application/json")
                .build();

        HttpResponse response = httpClient.put(url, configuration);

        if(response.getStatusCode() == 200) {
            return user.getId();
        }

        return -1;
    }

    public void removeUser(long id) {
        String url = getUrl("users/" + id);
        HttpResponse response = httpClient.delete(url, null);

        if(response.getStatusCode() == 200) {
            Log.d(TAG, "removeUser: SUCCESS");
        } else {
            Log.d(TAG, "removeUser: ERROR, " + response.getResponseMessage());
        }
    }

    private String getUserAsJsonString(User user) {
        JSONObject jsonUser = new JSONObject();
        try {
            jsonUser.put("username", user.getUsername());
            jsonUser.put("age", user.getAge());

            return jsonUser.toString(4);
        } catch (JSONException e) {
            Log.e(TAG, "getUserAsJsonString: Error", e);
        }

        return null;
    }

    private List<User> parseUsers(JSONArray jsonArray) {
        List<User> users = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                users.add(parseUser(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseUsers: Error", e);
        }

        return users;
    }

    private User parseUser(JSONObject object) {
        try {
            long id = object.getLong("id");
            String username = object.getString("username");
            int age = object.getInt("age");

            return new User(id, username, age);
        } catch (JSONException e) {
            Log.e(TAG, "parseUser: Error", e);
        }

        return null;
    }

    private String getUrl(String path) {
        return Uri.parse(BASE_URL)
                .buildUpon()
                .appendEncodedPath(path)
                .build()
                .toString();
    }
}
