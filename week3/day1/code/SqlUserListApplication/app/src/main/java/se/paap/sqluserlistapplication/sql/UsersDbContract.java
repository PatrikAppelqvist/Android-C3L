package se.paap.sqluserlistapplication.sql;

import android.provider.BaseColumns;

public class UsersDbContract {
    private UsersDbContract() {}

    public static class UsersEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_AGE = "age";
    }
}
