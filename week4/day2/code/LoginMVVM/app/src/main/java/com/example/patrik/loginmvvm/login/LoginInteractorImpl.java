package com.example.patrik.loginmvvm.login;

import android.os.Handler;

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;

                if(username.length() < 4) {
                    listener.onUsernameError();
                    error = true;
                }

                if(password.length() < 4) {
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
