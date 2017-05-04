package se.paap.networkuserlistapplication.repository.sql;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import se.paap.networkuserlistapplication.repository.UserRepository;
import se.paap.networkuserlistapplication.sql.SqlCommand;

public final class BackgroundTask<T> extends AsyncTask<Void, Void, T> {
    private final SqlCommand<T> command;
    private final UserRepository.OnResultListener<T> onResultListener;
    private final SQLiteDatabase database;

    public BackgroundTask(SqlCommand<T> command, SQLiteDatabase database, UserRepository.OnResultListener<T> onResultListener) {
        this.command = command;
        this.onResultListener = onResultListener;
        this.database = database;
    }

    @Override
    protected T doInBackground(Void... params) {
        return command.execute(database);
    }

    @Override
    protected void onPostExecute(T result) {
        onResultListener.onResult(result);
    }
}
