package com.example.cp470project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AccomplishmentActivity extends AppCompatActivity {

    ArrayList<String> chats = new ArrayList<String>();
    SQLiteDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomplishment);

        ListView accsView = (ListView) findViewById(R.id.accsView); //maybe use ListView reference instread of relative
        ChatAdapter messageAdapter = new ChatAdapter( this );
        accsView.setAdapter (messageAdapter);

        ChatDatabaseHelper cdb = new ChatDatabaseHelper(this);
        DB = cdb.getWritableDatabase();
        String[] allItems = new String[]{ChatDatabaseHelper.KEY_TIME, ChatDatabaseHelper.KEY_CAPTION};
        Cursor res = DB.query(ChatDatabaseHelper.TABLE_NAME, allItems, null, null, null, null, null);

        res.moveToFirst();
        while(res.isAfterLast() == false) {
            String cap = res.getString(res.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_CAPTION));
            chats.add(cap);
            res.moveToNext();
        }

        ContentValues values1 = new ContentValues();
        //ContentValues values2 = new ContentValues();

        Button upload_button = findViewById(R.id.upload_button);
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AccomplishmentActivity.this);
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogLayout = inflater.inflate(R.layout.post_dialog, null);
                builder1.setView(dialogLayout)
                        .setTitle(R.string.your_acc)
                        .setPositiveButton(R.string.post, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String cap = ((EditText) dialogLayout.findViewById(R.id.messageInput)).getText().toString();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                String d = date.toString();
                                String cap2 = "\n" + cap + "\n\nPosted: " + d;
                                chats.add(cap2);
                                messageAdapter.notifyDataSetChanged();
                                values1.put(ChatDatabaseHelper.KEY_CAPTION, cap2);
                                DB.insert(ChatDatabaseHelper.TABLE_NAME, "NullPlaceHolder", values1);
                                Toast toast = Toast.makeText(AccomplishmentActivity.this, "Accomplishment Posted", Toast.LENGTH_SHORT);
                                toast.show();
                                //new_post.setText(dateFormat.format(date) + ": " + s1);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast toast = Toast.makeText(AccomplishmentActivity.this, "Post Discarded", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        })
                        .show();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        DB.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chats.size();
        }

        public String getItem(int position) {
            return chats.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = AccomplishmentActivity.this.getLayoutInflater();
            View result = null ;
            result = inflater.inflate(R.layout.view_accomplishment, null);
            TextView message = (TextView)result.findViewById(R.id.accText);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }
    }

}