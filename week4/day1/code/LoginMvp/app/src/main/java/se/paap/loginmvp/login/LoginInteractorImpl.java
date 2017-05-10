package se.paap.loginmvp.login;

import android.os.Handler;

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if(username.trim().length() < 4) {
                    listener.onUsernameError();
                    error = true;
                }

                if(password.trim().length() < 6) {
                    listener.onPasswordError();
                    error = true;
                }

                if(!error) {
                    listener.onSuccess();
                }
            }
        }, 2000);
    }
}
