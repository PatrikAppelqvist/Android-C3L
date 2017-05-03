package se.paap.sqluserlistapplication.repository.sql;

import android.database.Cursor;
import android.database.CursorWrapper;

import se.paap.sqluserlistapplication.model.User;
import se.paap.sqluserlistapplication.sql.UsersDbContract.UsersEntry;

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        long id = getLong(getColumnIndexOrThrow(UsersEntry._ID));
        String username = getString(getColumnIndexOrThrow(UsersEntry.COLUMN_NAME_USERNAME));
        int age = getInt(getColumnIndexOrThrow(UsersEntry.COLUMN_NAME_AGE));

        return new User(id, username, age);
    }

    public User getFirstUser() {
        moveToFirst();
        return getUser();
    }

}
