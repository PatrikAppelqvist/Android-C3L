package se.paap.loginmvp.login;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {
    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.interactor = new LoginInteractorImpl();
    }

    @Override
    public void validateAndLogin(final String username, final String password) {
        loginView.showProgress();
        interactor.login(username, password, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        loginView.setUsernameError();
        loginView.hideProgress();
    }

    @Override
    public void onPasswordError() {
        loginView.setPasswordError();
        loginView.hideProgress();
    }

    @Override
    public void onSuccess() {
        loginView.navigateToHome();
        loginView.hideProgress();
    }
}
