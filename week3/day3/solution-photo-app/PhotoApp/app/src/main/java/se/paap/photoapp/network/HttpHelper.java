package se.paap.photoapp.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by patrik on 2017-05-05.
 */

public final class HttpHelper {
    public static HttpResponse get(String url) {
        HttpURLConnection connection = null;
        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();

            int statusCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            Map<String, List<String>> headers = connection.getHeaderFields();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writeToOutputStream(is, outputStream);
            byte[] response = outputStream.toByteArray();

            return new HttpResponse(statusCode, responseMessage, headers, response);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    private static void writeToOutputStream(InputStream is, OutputStream out) throws IOException {
        int bytesRead = 0;
        byte[] buffer = new byte[1024];

        try {
            while((bytesRead = is.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
        } finally {
            out.close();
        }

    }
}
