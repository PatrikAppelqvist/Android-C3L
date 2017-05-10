package se.paap.loginmvp.login;

public interface LoginPresenter {
    void validateAndLogin(String username, String password);
    void onDestroy();
}
