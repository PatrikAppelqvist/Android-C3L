package se.paap.readwritefile;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by patrik on 2017-04-28.
 */

public class ReadStringFileTask extends AsyncTask<Void, Integer, String> {
    private final Context context;
    private final String filename;
    private Callbacks callbacks;

    public ReadStringFileTask(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public ReadStringFileTask setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
        return this;
    }

    @Override
    protected String doInBackground(Void... params) {
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            inputStream = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            int progress = 0;

            while((line = reader.readLine()) != null) {
                sb.append(line);
                progress++;
                publishProgress(progress);
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(callbacks != null) {
            callbacks.onProgressUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(callbacks != null) {
            callbacks.onResult(s);
        }
    }

    interface Callbacks {
        void onProgressUpdate(int progress);
        void onResult(String data);
    }
}
