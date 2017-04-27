package se.paap.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPreferencesActivity extends AppCompatActivity {
    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        tvResult = (TextView) findViewById(R.id.tv_result);
        Button button = (Button) findViewById(R.id.btn_set_text);
        final EditText editText = (EditText) findViewById(R.id.edit_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                preferences
                        .edit().putString(getString(R.string.prefs_name_key), message)
                        .apply();
                updateResultTextView(preferences);
            }
        });

        updateResultTextView(preferences);
    }

    private void updateResultTextView(SharedPreferences preferences) {
        String name = preferences.getString(getString(R.string.prefs_name_key), "");
        tvResult.setText(name);
    }
}
