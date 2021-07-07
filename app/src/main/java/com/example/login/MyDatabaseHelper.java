package com.example.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_login";
    public static final int DB_VERSION = 1;
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE user" +
                    " (user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " name VARCHAR(10)," +
                    " email VARCHAR(10) UNIQUE,"+
                    " password VARCHAR(10),"+
                    " address VARCHAR(10))";

    public MyDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

