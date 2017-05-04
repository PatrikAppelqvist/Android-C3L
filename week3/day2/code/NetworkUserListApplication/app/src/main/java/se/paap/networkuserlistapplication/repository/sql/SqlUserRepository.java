package se.paap.networkuserlistapplication.repository.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import se.paap.networkuserlistapplication.model.User;
import se.paap.networkuserlistapplication.repository.UserRepository;
import se.paap.networkuserlistapplication.sql.SqlCommand;
import se.paap.networkuserlistapplication.sql.UsersDbContract.UsersEntry;
import se.paap.networkuserlistapplication.sql.UsersDbHelper;

public class SqlUserRepository implements UserRepository {

    private static SqlUserRepository instance;

    public static synchronized SqlUserRepository getInstance(Context context) {
        if(instance == null) {
            instance = new SqlUserRepository(context);
        }

        return instance;
    }

    private final SQLiteDatabase database;

    private SqlUserRepository(Context context) {
        database = UsersDbHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public void getUsers(OnResultListener<List<User>> listener) {
        SqlCommand<List<User>> command = new SqlCommand<List<User>>() {
            @Override
            public List<User> execute(SQLiteDatabase db) {
                UserCursorWrapper cursor = queryUsers(null, null);
                List<User> users = cursor.getUsers();
                cursor.close();

                return users;
            }
        };

        executeCommand(command, listener);
    }

    @Override
    public void getUser(final long id, OnResultListener<User> listener) {
        SqlCommand<User> command = new SqlCommand<User>() {
            @Override
            public User execute(SQLiteDatabase db) {
                UserCursorWrapper cursor = queryUsers(UsersEntry._ID + " = ?", new String[] { String.valueOf(id) });
                User user = cursor.getSingleUser();
                cursor.close();

                return user;
            }
        };

        executeCommand(command, listener);
    }

    @Override
    public void addOrUpdateUser(final User user, OnResultListener<Long> listener) {
        SqlCommand<Long> command = new SqlCommand<Long>() {
            @Override
            public Long execute(SQLiteDatabase db) {
                ContentValues cv = getContentValues(user);

                if(user.hasBeenPersisted()) {
                    cv.put(UsersEntry._ID, user.getId());
                    database.update(UsersEntry.TABLE_NAME, cv, UsersEntry._ID + " = ?", new String[] { String.valueOf(user.getId()) });
                    return user.getId();
                } else {
                    return database.insert(UsersEntry.TABLE_NAME, null, cv);
                }
            }
        };

        executeCommand(command, listener);
    }

    @Override
    public void removeUser(final long id, OnResultListener<Void> listener) {
        SqlCommand<Void> command = new SqlCommand<Void>() {
            @Override
            public Void execute(SQLiteDatabase db) {
                database.delete(UsersEntry.TABLE_NAME, UsersEntry._ID + " = ?", new String[] { String.valueOf(id) });
                return null;
            }
        };

        executeCommand(command, listener);
    }

    private <T> void executeCommand(SqlCommand<T> command, OnResultListener<T> listener) {
        new BackgroundTask<>(command, database, listener).execute();
    }

    private ContentValues getContentValues(User user) {
        ContentValues cv = new ContentValues();
        cv.put(UsersEntry.COLUMN_NAME_USERNAME, user.getUsername());
        cv.put(UsersEntry.COLUMN_NAME_AGE, user.getAge());

        return cv;
    }

    private UserCursorWrapper queryUsers(String where, String[] whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                UsersEntry.TABLE_NAME,
                null,
                where,
                whereArg,
                null,
                null,
                null
        );

        return new UserCursorWrapper(cursor);
    }
}
