package com.example.cp470project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;

public class MessageBoard extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MessageBoard";

    public Button createButton;
    public ListView listView;
    public EditText boardMessage;
    public ArrayList<String> boardMessages = new ArrayList<String>();
    public ArrayList<String> usernames = new ArrayList<String>();
    SQLiteDatabase database;
    Cursor cursor = null;

    private class MessageBoardAdapter extends ArrayAdapter<String>{
        public MessageBoardAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){
            return boardMessages.size();
        }
        public String getItem(int position){
            return boardMessages.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = MessageBoard.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.message_board_post,null);
            TextView message = (TextView)result.findViewById(R.id.post_text);
            TextView username = (TextView)result.findViewById(R.id.username_text);
            message.setText(getItem(position));
            username.setText(getUsername(position));
            return result;
        }
        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndexOrThrow(MessageBoardDatabaseHelper.KEY_ID));
        }
        public String getUsername(int position){
            return usernames.get(position);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);


        createButton    =  findViewById(R.id.sendButton);
        listView      =  findViewById(R.id.listView);
        boardMessage  = findViewById(R.id.chatMessage);

        MessageBoardDatabaseHelper helper = new MessageBoardDatabaseHelper(this);
        database = helper.getWritableDatabase();
        String[] allItems = new String[]{MessageBoardDatabaseHelper.KEY_ID,MessageBoardDatabaseHelper.KEY_MESSAGE, MessageBoardDatabaseHelper.KEY_USERNAME};
        cursor = database.query(MessageBoardDatabaseHelper.TABLE_NAME,allItems,null,null,null,null,null);

        MessageBoardAdapter messageAdapter = new MessageBoardAdapter( this );
        listView.setAdapter (messageAdapter);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int index = cursor.getColumnIndexOrThrow(MessageBoardDatabaseHelper.KEY_MESSAGE);
                int index2 = cursor.getColumnIndexOrThrow(MessageBoardDatabaseHelper.KEY_USERNAME);
                boardMessages.add(cursor.getString(index));
                usernames.add(cursor.getString(index2));
                cursor.moveToNext();
            }
        }
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = boardMessage.getText().toString();
                boardMessages.add(message);
                usernames.add(LoginActivity.username);
                messageAdapter.notifyDataSetChanged();
                boardMessage.setText("");
                ContentValues values = new ContentValues();
                values.put(MessageBoardDatabaseHelper.KEY_MESSAGE,message);
                values.put(MessageBoardDatabaseHelper.KEY_USERNAME,LoginActivity.username);
                database.insert(MessageBoardDatabaseHelper.TABLE_NAME,null,values);
            }
    });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}