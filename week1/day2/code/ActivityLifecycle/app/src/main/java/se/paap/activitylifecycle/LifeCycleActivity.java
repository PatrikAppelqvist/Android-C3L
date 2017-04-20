package se.paap.activitylifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LifeCycleActivity extends AppCompatActivity {
    private static final String TAG = "LifeCycleActivity";
    private static final String BUNDLE_STATE_LOG = "se.paap.activityLifecycle.LifeCycleActivity.log";

    private static List<String> activityLog = new ArrayList<>();
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLog = (TextView) findViewById(R.id.tv_lifecycle_log);

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(BUNDLE_STATE_LOG)) {
                String log = savedInstanceState.getString(BUNDLE_STATE_LOG);
                tvLog.setText(log);
            }
        }

        // Dispay log onStop onDestroy onSaveInstanceState
        for(String logMessage : activityLog) {
            tvLog.append(logMessage + "\n");
        }

        logAndDisplay("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        logAndDisplay("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        logAndDisplay("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        logAndDisplay("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        activityLog.add("onStop");

        logAndDisplay("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        activityLog.add("onDestroy");

        logAndDisplay("onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String log = tvLog.getText().toString();
        outState.putString(BUNDLE_STATE_LOG, log);

        activityLog.add("onSaveInstanceState");

        logAndDisplay("onSaveInstanceState");
    }

    private void logAndDisplay(String logMessage) {
        Log.d(TAG, "logAndDisplay: " + logMessage);
        tvLog.append(logMessage + "\n");
    }
}
