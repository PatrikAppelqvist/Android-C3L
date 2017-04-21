package se.paap.solutionrecyclerviewexercise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import se.paap.solutionrecyclerviewexercise.model.User;

public class EditUserActivity extends AppCompatActivity {
    public static final String EXTRA_USER_BACK = "user_extra_back";
    private static final String EXTRA_USER = "user_extra";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        final EditText usernameEditText = (EditText) findViewById(R.id.edit_text_username);
        final EditText ageEditText = (EditText) findViewById(R.id.edit_text_age);
        final Button button = (Button) findViewById(R.id.btn_edit_user);

        ageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }

                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString();
                final int age = Integer.parseInt(ageEditText.getText().toString());
                final User newOrEditedUser = EditUserActivity.this.user == null ? new User(username, age) : new User(user.getId(), username, age);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_USER_BACK, newOrEditedUser);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_USER)) {
            this.user = intent.getParcelableExtra(EXTRA_USER);

            if(this.user != null) {
                usernameEditText.setText(user.getUsername());
                ageEditText.setText(String.valueOf(user.getAge()));
                button.setText(getString(R.string.btn_edit_user));
            } else {
                button.setText(getString(R.string.btn_add_user));
            }
        }
    }

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, EditUserActivity.class);
        intent.putExtra(EXTRA_USER, user);

        return intent;
    }
}
