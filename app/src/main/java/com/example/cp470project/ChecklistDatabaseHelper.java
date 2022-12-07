package com.example.cp470project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChecklistDatabaseHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "ChecklistDatabaseHelper";

    public static final String DATABASE_NAME = "Messages.db";
    public static final int VERSION_NUM = 1;
    public static final String table_name = "myTable";
    public static final String id = "_id";
    public static final String objective_value = "objvalue";
    public static final String checked_name = "checked";
    public ChecklistDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    private static final String DATABASE_CREATE = "create table "+ table_name+" (" + id +" integer primary key autoincrement, "+objective_value+" text not null, "+checked_name+" binary(1));";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS myTable");
        onCreate(sqLiteDatabase);
    }
    public String tableToString(SQLiteDatabase db, String tableName) {
        Log.d("","tableToString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        tableString += cursorToString(allRows);
        return tableString;
    }

    @SuppressLint("Range")
    public String cursorToString(Cursor cursor){
        String cursorString = "";
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            for (String name: columnNames)
                cursorString += String.format("%s ][ ", name);
            cursorString += "\n";
            do {
                for (String name: columnNames) {
                    cursorString += String.format("%s ][ ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }
}
