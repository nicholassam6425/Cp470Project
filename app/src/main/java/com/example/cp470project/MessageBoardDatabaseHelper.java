package com.example.cp470project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class MessageBoardDatabaseHelper extends SQLiteOpenHelper {

    public static final String ACTIVITY_NAME = "ChatDatabaseHelper";

    public static String DATABASE_NAME = "messageboard.db";
    public static int VERSION_NUM = 7;

    public final static String TABLE_NAME = "tableOfBoardMessages";
    public final static String KEY_ID = "_id";
    public final static String KEY_USERNAME = "username";
    public final static String KEY_MESSAGE = "board_message";
    private static final String create = "create table " + TABLE_NAME + " ( " + KEY_ID+ " integer primary key autoincrement, " + KEY_MESSAGE + " text not null,"  + KEY_USERNAME + " text not null)";

    public MessageBoardDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(ACTIVITY_NAME, "Calling onCreate");
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion="+ i + "newVersion=" + i1);
        String destroy = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(destroy);
        onCreate(sqLiteDatabase);
    }
}
