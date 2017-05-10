package se.paap.loginmvp.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import se.paap.loginmvp.MainActivity;
import se.paap.loginmvp.R;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private ProgressBar progressBar;
    private EditText usernameInput;
    private EditText passwordInput;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        usernameInput = (EditText) findViewById(R.id.username_edit_text);
        passwordInput = (EditText) findViewById(R.id.password_edit_text);

        findViewById(R.id.btn_login).setOnClickListener(this);

        presenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUsernameError() {
        String error = getString(R.string.error_message_username);
        usernameInput.setError(error);
    }

    @Override
    public void setPasswordError() {
        String error = getString(R.string.error_message_password);
        passwordInput.setError(error);
    }

    @Override
    public void navigateToHome() {
        Intent intent = MainActivity.createIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        presenter.validateAndLogin(username, password);
    }
}
