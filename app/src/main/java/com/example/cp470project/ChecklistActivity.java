package com.example.cp470project;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class ChecklistActivity extends AppCompatActivity {
    public ArrayList<String> objectives = new ArrayList<>();

    //THIS ARRAY IS AN ARRAY OF CHECKED BOXES. USE FOR PROGRESS
    public boolean checked[] = new boolean[0];



    static SQLiteDatabase database;
    private ChecklistDatabaseHelper checklistDatabaseHelper;
    private boolean ispopulated = false;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        checklistDatabaseHelper = new ChecklistDatabaseHelper(this);
        database = checklistDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.query(checklistDatabaseHelper.table_name,null, null, null,null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            objectives.add(cursor.getString(cursor.getColumnIndex(ChecklistDatabaseHelper.objective_value)));
            boolean temp[] = Arrays.copyOf(checked, checked.length + 1);
            temp[checked.length] = (cursor.getInt(cursor.getColumnIndex(ChecklistDatabaseHelper.checked_name)) == 1);
            checked = temp;
            cursor.moveToNext();
            ispopulated = true;
        }
        if (ispopulated) {
            TextView hint = findViewById(R.id.checklisthint);
            hint.setVisibility(View.GONE);
        }
        FloatingActionButton fab = findViewById(R.id.add_to_checklist);
        ListView checklist = findViewById(R.id.checklist);
        ChecklistAdapter adapter1 = new ChecklistAdapter(this);
        checklist.setAdapter(adapter1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogBuilder builder1 = new CustomDialogBuilder(ChecklistActivity.this,
                        adapter1);
                builder1.build();
            }
        });

        Button track = findViewById(R.id.tracking);

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putBooleanArray("arr", checked);
                Intent i = new Intent(ChecklistActivity.this, TrackingSystem.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }
    private class ChecklistAdapter extends ArrayAdapter<String>
    {
        public ChecklistAdapter(Context ctx) {super(ctx, 0); }
        public int getCount() { return objectives.size();}
        public String getItem(int position) { return objectives.get(position); }
        public boolean getStatus(int position) { return checked[position]; }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChecklistActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.checklist_item, null);
            TextView objective = (TextView)result.findViewById(R.id.objective);
            objective.setText(getItem(position));
            CheckBox chkbox = (CheckBox)result.findViewById(R.id.objectivecheckbox);
            chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ContentValues values = new ContentValues();
                    values.put(checklistDatabaseHelper.checked_name, b ? 1 : 0);
                    int a = database.update(checklistDatabaseHelper.table_name, values, checklistDatabaseHelper.id + " = ?", new String[]{Integer.toString(position+1)});
                }
            });
            chkbox.setChecked(getStatus(position));
            return result;
        }
    }
    private class CustomDialogBuilder {
        AppCompatActivity activity;
        ChecklistAdapter adapter1;
        public CustomDialogBuilder(AppCompatActivity activity, ChecklistAdapter adapter1){
            this.adapter1 = adapter1;
            this.activity = activity;
        }
        public void build() {
            AlertDialog.Builder customDialog = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View view = inflater.inflate(R.layout.new_objective, null);
            customDialog.setView(view)
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText edit = view.findViewById(R.id.new_goal_text_field);
                            objectives.add(edit.getText().toString());
                            ContentValues values = new ContentValues();
                            values.put(checklistDatabaseHelper.objective_value, edit.getText().toString());
                            database.insert(checklistDatabaseHelper.table_name, null, values);
                            boolean temp[] = Arrays.copyOf(checked, checked.length + 1);
                            temp[checked.length] = false;
                            checked = temp;
                            adapter1.notifyDataSetChanged();
                            if (!ispopulated) {
                                ispopulated = true;
                                TextView hint = findViewById(R.id.checklisthint);
                                hint.setVisibility(View.GONE);
                            }
                            edit.setText("");
                            Toast toast = Toast.makeText(ChecklistActivity.this, R.string.goaltoast, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Dialog dialog = customDialog.create();
            dialog.show();

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //database.close();
    }
}