package com.example.patrik.loginmvvmdone.login;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.example.patrik.loginmvvmdone.MainActivity;

/**
 * Created by patrik on 2017-05-11.
 */

public class LoginViewModel implements LoginInteractor.OnLoginFinishedListener {
    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> usernameError = new ObservableField<>();
    public ObservableField<String> passwordError = new ObservableField<>();
    public ObservableBoolean loading = new ObservableBoolean();

    private final LoginInteractor interactor;
    private final Context context;

    public LoginViewModel(Context context) {
        this.interactor = new LoginInteractorImpl();
        this.context = context.getApplicationContext();
    }

    public void login() {
        loading.set(true);

        String usernameString = username.get();
        String passwordString = password.get();

        if(usernameString != null && passwordString != null) {
            interactor.login(username.get(), password.get(), this);
        }
    }

    @Override
    public void onUsernameError() {
        loading.set(false);
        usernameError.set("Bad username");
    }

    @Override
    public void onPasswordError() {
        loading.set(false);
        passwordError.set("Bad password");
    }

    @Override
    public void onSuccess() {
        loading.set(false);
        context.startActivity(MainActivity.createIntent(context));
    }
}
