package se.paap.mysolutionexercise1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public final class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_MESSAGE_BACK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText messageEditText = (EditText) findViewById(R.id.message_edit_text);
        final Button button = (Button) findViewById(R.id.btn_send_message);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = messageEditText.getText().toString();
                final Intent intent = SecondActivity.createIntent(MainActivity.this, message);

                startActivityForResult(intent, REQUEST_CODE_MESSAGE_BACK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_MESSAGE_BACK) {
            if(resultCode == RESULT_OK) {
                String message = data.getStringExtra(SecondActivity.EXTRA_MESSAGE_BACK);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
