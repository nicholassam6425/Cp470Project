package com.example.cp470project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChecklistActivity extends AppCompatActivity {
    public ArrayList<String> objectives = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
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
    }
    private class ChecklistAdapter extends ArrayAdapter<String>
    {
        public ChecklistAdapter(Context ctx) {super(ctx, 0); }
        public int getCount() { return objectives.size();}
        public String getItem(int position) { return objectives.get(position); }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChecklistActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.checklist_item, null);
            TextView objective = (TextView)result.findViewById(R.id.objective);
            objective.setText(getItem(position));
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
                            adapter1.notifyDataSetChanged();
                            edit.setText("");
                        }
                    });
            Dialog dialog = customDialog.create();
            dialog.show();
        }
    }
}