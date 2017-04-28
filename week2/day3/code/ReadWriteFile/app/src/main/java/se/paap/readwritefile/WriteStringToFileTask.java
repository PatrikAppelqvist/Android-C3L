package se.paap.readwritefile;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;

public class WriteStringToFileTask extends AsyncTask<Void, Void, Void> {
    private final Context context;
    private final String filename;
    private final String data;
    private OnWriteDoneListener listener;

    public WriteStringToFileTask(Context context, String filename, String data) {
        this.context = context;
        this.filename = filename;
        this.data = data;
    }

    public WriteStringToFileTask setOnWriteDoneListener(OnWriteDoneListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OutputStream outputStream = null;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
        } catch(IOException e)  {
            e.printStackTrace();
        } finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(listener != null) {
            listener.onWriteDone();
        }

    }

    interface OnWriteDoneListener {
        void onWriteDone();
    }
}
