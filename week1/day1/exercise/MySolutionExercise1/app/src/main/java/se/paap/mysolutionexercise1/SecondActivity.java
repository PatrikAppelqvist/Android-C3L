package se.paap.mysolutionexercise1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public final class SecondActivity extends AppCompatActivity {
    private static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_MESSAGE_BACK = "message_back";

    public static Intent createIntent(Context context, String message) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final TextView tvMessage = (TextView) findViewById(R.id.tv_message);
        final Button button = (Button) findViewById(R.id.btn_send_message_back);
        final EditText editText = (EditText) findViewById(R.id.message_edit_text);

        final Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_MESSAGE)) {
            final String message = intent.getStringExtra(EXTRA_MESSAGE);
            tvMessage.setText(message);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(EXTRA_MESSAGE_BACK, message);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
