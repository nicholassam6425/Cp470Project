package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button activity1 = findViewById(R.id.activity1);
        Button activity2 = findViewById(R.id.activity2);
        Button activity3 = findViewById(R.id.activity3);
        Button activity4 = findViewById(R.id.activity4);
        Button activity5 = findViewById(R.id.activity5);
        Button activity6 = findViewById(R.id.activity6);

        activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Checklist");
                //Intent intent = new Intent(MainActivity.this,Checklist.class);
                //startActivity(intent);
            }
        });
        activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Stress Relief");
                //Intent intent = new Intent(MainActivity.this,StressRelief.class);
                //startActivity(intent);
            }
        });
        activity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Tracking System");
                //Intent intent = new Intent(MainActivity.this,TrackingSystem.class);
                //startActivity(intent);
            }
        });
        activity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Accomplishment");
                //Intent intent = new Intent(MainActivity.this,Accomplishment.class);
                //startActivity(intent);
            }
        });
        activity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Quote of the Day");
                Intent intent = new Intent(MainActivity.this,QuoteOfTheDay.class);
                startActivity(intent);
            }
        });
        activity6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Activity6");
                //Intent intent = new Intent(MainActivity.this,QuoteOfTheDay.class);
                //startActivity(intent);
            }
        });
    }
}