package se.paap.intentreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(action.equals(Intent.ACTION_SEND) && type.equals("text/plain")) {
            TextView textView = (TextView) findViewById(R.id.result_tv);
            String message = intent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText(message);
        }
    }
}
