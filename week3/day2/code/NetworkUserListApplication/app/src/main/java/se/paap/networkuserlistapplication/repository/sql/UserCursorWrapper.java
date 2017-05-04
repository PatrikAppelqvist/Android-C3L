package se.paap.networkuserlistapplication.repository.sql;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import se.paap.networkuserlistapplication.model.User;
import se.paap.networkuserlistapplication.sql.UsersDbContract.UsersEntry;

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        if(getCount() > 0) {
            while(moveToNext()) {
                users.add(getUser());
            }
        }

        return users;
    }

    public User getSingleUser() {
        if(getCount() > 0) {
            moveToFirst();
            User user = getUser();

            return user;
        }

        return null;
    }

    private User getUser() {
        long id = getLong(getColumnIndexOrThrow(UsersEntry._ID));
        String username = getString(getColumnIndexOrThrow(UsersEntry.COLUMN_NAME_USERNAME));
        int age = getInt(getColumnIndexOrThrow(UsersEntry.COLUMN_NAME_AGE));

        return new User(id, username, age);
    }
}
