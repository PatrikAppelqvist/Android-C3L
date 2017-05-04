package se.paap.httpexampleapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.tv_result);

        new GetTask(new GetTask.OnResultListener() {
            @Override
            public void onResult(List<Message> result) {
                StringBuilder sb = new StringBuilder();

                for(Message message : result) {
                    sb.append(message.getMessage() + "\n");
                }

                textView.setText(sb.toString());
            }
        }).execute();
    }

    private static class GetTask extends AsyncTask<Void, Void, List<Message>> {
        private final OnResultListener listener;

        private GetTask(OnResultListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Message> doInBackground(Void... params) {
            Message message = new Message("Hello world!!!!", "Klassen");
            String response = HttpHelper.post("http://10.0.2.2:3000/messages", getAsJsonString(message));

            Log.d(TAG, "doInBackground: " + response);

            String data = HttpHelper.get("http://10.0.2.2:3000/messages");
            List<Message> messages = new ArrayList<>();

            try {
                JSONArray jsonData = new JSONArray(data);

                for(int i = 0; i < jsonData.length(); i++) {
                    JSONObject jsonObject = jsonData.getJSONObject(i);
                    String messageString = jsonObject.getString("message");
                    String from = jsonObject.getString("from");
                    messages.add(new Message(messageString, from));
                }

                return messages;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        private String getAsJsonString(Message message) {
            try {
                JSONObject jsonMessage = new JSONObject();
                jsonMessage.put("message", message.getMessage());
                jsonMessage.put("from", message.getFrom());

                return jsonMessage.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            listener.onResult(messages);
        }

        interface OnResultListener {
            void onResult(List<Message> result);
        }
    }
}
