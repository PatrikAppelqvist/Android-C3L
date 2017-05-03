package se.paap.sqluserlistapplication.repository.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import se.paap.sqluserlistapplication.model.User;
import se.paap.sqluserlistapplication.repository.UserRepository;
import se.paap.sqluserlistapplication.sql.UsersDbContract;
import se.paap.sqluserlistapplication.sql.UsersDbContract.UsersEntry;
import se.paap.sqluserlistapplication.sql.UsersDbHelper;

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
    public List<User> getUsers() {
        UserCursorWrapper cursor = queryUsers(null, null);

        List<User> users = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                users.add(cursor.getUser());
            }
        }

        cursor.close();

        return users;
    }

    @Override
    public User getUser(long id) {
        UserCursorWrapper cursor = queryUsers(UsersEntry._ID + " = ?", new String[] { String.valueOf(id) });

        if(cursor.getCount() > 0) {
            User user = cursor.getFirstUser();
            cursor.close();

            return user;
        }

        cursor.close();

        return null;
    }

    @Override
    public long addOrUpdateUser(User user) {
        ContentValues cv = getContentValues(user);

        if(user.hasBeenPersisted()) {
            cv.put(UsersEntry._ID, user.getId());
            database.update(UsersEntry.TABLE_NAME, cv, UsersEntry._ID + " = ?", new String[] { String.valueOf(user.getId()) });

            return user.getId();
        } else {
            return database.insert(UsersEntry.TABLE_NAME, null, cv);
        }
    }

    @Override
    public User removeUser(long id) {
        User user = getUser(id);
        database.delete(UsersEntry.TABLE_NAME, UsersEntry._ID + " = ?", new String[] { String.valueOf(id) });

        return user;
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
