package com.example.cp470project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StreaksActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StreaksActivity";

    public Button createButton;
    public Button debugButton;
    public ListView listView;
    public ArrayList<String> streakDescs = new ArrayList<String>();
    public ArrayList<Long> times = new ArrayList<Long>();
    SQLiteDatabase database;
    Cursor cursor = null;
    public Long daysOffset = 604800000L;

    private class StreaksAdapter extends ArrayAdapter<String>{
        public StreaksAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){
            return streakDescs.size();
        }
        public String getItem(int position){
            return streakDescs.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = StreaksActivity.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.streaks_post,null);
            TextView desc = result.findViewById(R.id.streakDesc);
            TextView time = result.findViewById(R.id.streakTime);
            ProgressBar elapsedBar = result.findViewById(R.id.streakProgress);
            desc.setText(  getItem(position));
            Date date = getTime(position);
            Long futureTime = (long)(date.getTime()) + daysOffset;
            Long setTime = (long)(date.getTime());
            Long currentTime = new Date().getTime();
            Integer elapsed = (int)((float)(currentTime - setTime)/(futureTime - setTime)*100);
            elapsedBar.setProgress(((int)elapsed));
            time.setText(date.toString());
            return result;
        }
        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndexOrThrow(StreaksDatabaseHelper.KEY_ID));
        }
        public Date getTime(int position){
            return new Date(times.get(position));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaks);

        createButton    =  findViewById(R.id.sendButton);
        listView      =  findViewById(R.id.listView);
        debugButton = findViewById(R.id.debugButton);


        StreaksDatabaseHelper helper = new StreaksDatabaseHelper(this);
        database = helper.getWritableDatabase();
        String[] allItems = new String[]{StreaksDatabaseHelper.KEY_ID,StreaksDatabaseHelper.KEY_STREAK_DESC, StreaksDatabaseHelper.KEY_STREAK_TIME};
        cursor = database.query(StreaksDatabaseHelper.TABLE_NAME,allItems,null,null,null,null,null);

        StreaksAdapter streaksAdapter = new StreaksAdapter( this );
        listView.setAdapter (streaksAdapter);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int index = cursor.getColumnIndexOrThrow(StreaksDatabaseHelper.KEY_STREAK_DESC);
                int index2 = cursor.getColumnIndexOrThrow(StreaksDatabaseHelper.KEY_STREAK_TIME);
                streakDescs.add(cursor.getString(index));
                times.add(cursor.getLong(index2));
                cursor.moveToNext();
            }
        }
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(StreaksActivity.this);
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogLayout = inflater.inflate(R.layout.post_dialog, null);
                builder1.setView(dialogLayout)
                        .setTitle(R.string.enterStreak)
                        .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String message = ((EditText) dialogLayout.findViewById(R.id.messageInput)).getText().toString();
                                Long time = (long) (new Date().getTime());
                                streakDescs.add(message);
                                times.add(time);
                                streaksAdapter.notifyDataSetChanged();
                                ContentValues values = new ContentValues();
                                values.put(StreaksDatabaseHelper.KEY_STREAK_DESC,message);
                                values.put(StreaksDatabaseHelper.KEY_STREAK_TIME,time);
                                database.insert(StreaksDatabaseHelper.TABLE_NAME,null,values);
                                cursor = database.query(StreaksDatabaseHelper.TABLE_NAME,allItems,null,null,null,null,null);
                                streaksAdapter.notifyDataSetChanged();
                                Toast toast = Toast.makeText(StreaksActivity.this, "Streak Started", Toast.LENGTH_SHORT);
                                toast.show();
                                //new_post.setText(dateFormat.format(date) + ": " + s1);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        });
        debugButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String message = "ENTRY";
                Long time = (long) (new Date().getTime()) - 345600000;
                streakDescs.add(message);
                times.add(time);
                streaksAdapter.notifyDataSetChanged();
                ContentValues values = new ContentValues();
                values.put(StreaksDatabaseHelper.KEY_STREAK_DESC,message);
                values.put(StreaksDatabaseHelper.KEY_STREAK_TIME,time);
                database.insert(StreaksDatabaseHelper.TABLE_NAME,null,values);
                cursor = database.query(StreaksDatabaseHelper.TABLE_NAME,allItems,null,null,null,null,null);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Date date = new Date();
                Long timeElapsed = (Long)(date.getTime()) - times.get(i);
                Integer daysElapsed = (int)((float)timeElapsed/86400000);
                String output = "Streak ended at " + daysElapsed + " days";
                Toast toast = Toast.makeText(StreaksActivity.this, output, Toast.LENGTH_SHORT);
                toast.show();
                database.delete(StreaksDatabaseHelper.TABLE_NAME,StreaksDatabaseHelper.KEY_ID + "=" + streaksAdapter.getItemId(i),null);
                cursor = database.query(StreaksDatabaseHelper.TABLE_NAME,allItems,null,null,null,null,null);
                streakDescs.remove(i);
                times.remove(i);
                streaksAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}