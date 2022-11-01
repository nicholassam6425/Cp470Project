package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

public class TrackingSystem extends AppCompatActivity {

    private int progressStat = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_system);

        ProgressBar bar = findViewById(R.id.progressBar);
        //used to keep track of Checklist Activity

    }
}