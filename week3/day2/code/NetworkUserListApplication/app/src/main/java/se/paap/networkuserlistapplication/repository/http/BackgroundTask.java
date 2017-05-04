package se.paap.networkuserlistapplication.repository.http;

import android.os.AsyncTask;

import se.paap.networkuserlistapplication.http.api.ApiCommand;
import se.paap.networkuserlistapplication.http.api.Api;
import se.paap.networkuserlistapplication.repository.UserRepository;

public final class BackgroundTask<T> extends AsyncTask<Void, Void, T> {
    private final ApiCommand<T> command;
    private final UserRepository.OnResultListener<T> onResultListener;
    private final Api listApi;

    public BackgroundTask(ApiCommand<T> command, UserRepository.OnResultListener<T> onResultListener) {
        this.command = command;
        this.onResultListener = onResultListener;
        this.listApi = new Api();
    }

    @Override
    protected T doInBackground(Void... params) {
        return command.execute(listApi);
    }

    @Override
    protected void onPostExecute(T result) {
        onResultListener.onResult(result);
    }
}
