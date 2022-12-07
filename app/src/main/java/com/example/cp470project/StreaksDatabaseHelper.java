package com.example.cp470project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class StreaksDatabaseHelper extends SQLiteOpenHelper {

    public static final String ACTIVITY_NAME = "ChatDatabaseHelper";

    public static String DATABASE_NAME = "streaks.db";
    public static int VERSION_NUM = 8;

    public final static String TABLE_NAME = "tableOfBoardMessages";
    public final static String KEY_ID = "_id";
    public final static String KEY_STREAK_DESC = "streak_desc";
    public final static String KEY_STREAK_TIME = "streak_time";
    private static final String create = "create table " + TABLE_NAME + " ( " + KEY_ID+ " integer primary key autoincrement, " + KEY_STREAK_DESC + " text not null, "  + KEY_STREAK_TIME + " integer)";

    public StreaksDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String destroy = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(destroy);
        onCreate(sqLiteDatabase);
    }
}
