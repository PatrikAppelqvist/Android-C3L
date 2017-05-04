package se.paap.networkuserlistapplication.sql;

import android.database.sqlite.SQLiteDatabase;

public interface SqlCommand<T> {
    T execute(SQLiteDatabase db);
}
