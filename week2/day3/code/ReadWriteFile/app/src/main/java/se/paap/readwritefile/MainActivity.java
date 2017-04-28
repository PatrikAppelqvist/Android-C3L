package se.paap.readwritefile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.tv_result);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(100);

        WriteStringToFileTask writeTask = new WriteStringToFileTask(this, "test.txt", getString(R.string.long_message));
        new WriteStringToFileTask(this, "test.txt", getString(R.string.long_message))
                .setOnWriteDoneListener(new WriteStringToFileTask.OnWriteDoneListener() {
                    @Override
                    public void onWriteDone() {
                        new ReadStringFileTask(MainActivity.this, "test.txt")
                                .setCallbacks(new ReadStringFileTask.Callbacks() {
                                    @Override
                                    public void onProgressUpdate(int progress) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        progressBar.setProgress(progress);
                                    }

                                    @Override
                                    public void onResult(String data) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        textView.setText(data);
                                    }
                                }).execute();
                    }
                })
                .execute();

        writeTask.execute();
    }
}
