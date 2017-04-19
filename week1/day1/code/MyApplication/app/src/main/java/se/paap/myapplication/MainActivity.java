package se.paap.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        Button button = (Button) findViewById(R.id.btn_show_activity);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextValue = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(SecondActivity.EXTRA_MESSAGE, editTextValue);
                startActivity(intent);
            }
        });
    }
}
