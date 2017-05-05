package se.paap.examplephotoapp.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public final class HttpResponse {
    private final int statusCode;
    private final String responseMessage;
    private final Map<String, List<String>> headers;
    private final byte[] response;
    private final Gson gson;

    public HttpResponse(int statusCode, String responseMessage, Map<String, List<String>> headers, byte[] response) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.headers = headers;
        this.response = response;
        this.gson = new Gson();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public byte[] getResponse() {
        return response;
    }

    public JsonObject getAsJsonObject() {
        String responseString = getAsString();
        return gson.fromJson(responseString, JsonObject.class);
    }

    public JsonArray getAsJsonArray() {
        String responseString = getAsString();
        return gson.fromJson(responseString, JsonArray.class);
    }

    public String getAsString() {
        return new String(response);
    }
}
