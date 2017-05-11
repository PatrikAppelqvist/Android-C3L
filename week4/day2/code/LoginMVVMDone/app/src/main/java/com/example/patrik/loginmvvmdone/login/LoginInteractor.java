package com.example.patrik.loginmvvmdone.login;

public interface LoginInteractor {
    interface OnLoginFinishedListener {
        void onUsernameError();
        void onPasswordError();
        void onSuccess();
    }

    void login(String username, String password, OnLoginFinishedListener listener);
}
