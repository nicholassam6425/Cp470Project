package com.example.cp470project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "Accomplishments.db";
    public static int VERSION_NUM = 1;
    public static final String TABLE_NAME = "accs";
    public static final String KEY_TIME = "time";
    public static final String KEY_CAPTION = "caption";
    public static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + " ( " + KEY_TIME
            + ", " + KEY_CAPTION
            + " text not null);";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int x, int y) {

        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + (VERSION_NUM - 1) +  "newVersion=" + VERSION_NUM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
