package se.paap.networkuserlistapplication.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import se.paap.networkuserlistapplication.sql.UsersDbContract.UsersEntry;

public class UsersDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "users.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE_USERS =
                    "CREATE TABLE " + UsersEntry.TABLE_NAME + " (" +
                    UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UsersEntry.COLUMN_NAME_USERNAME + " TEXT NOT NULL, " +
                    UsersEntry.COLUMN_NAME_AGE + " INTEGER NOT NULL);";

    private static final String DROP_TABLE_USERS =
            "DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME;

    private static UsersDbHelper instance;

    public static synchronized UsersDbHelper getInstance(Context context) {
        if(instance == null) {
            instance = new UsersDbHelper(context);
        }

        return instance;
    }

    private UsersDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USERS);
        onCreate(db);
    }
}
