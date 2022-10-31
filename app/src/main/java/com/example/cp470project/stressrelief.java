package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class stressrelief extends AppCompatActivity {
public static final long starttimeinms = 5000;
private TextView countdown;
private TextView p;

private Button bpause;
private Button breset;
private CountDownTimer countdowntime;
private boolean mrunning;
private long leftinmls = starttimeinms;
private int i =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stressrelief);
        countdown =  findViewById(R.id.textView);
        bpause = findViewById(R.id.button );
        breset =findViewById(R.id.button2);
         p = findViewById(R.id.textView2);
        String ss = String.format("breath in for %d seconds",(int)(leftinmls/1000)%60);
        p.setText(ss);
        bpause.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
            if (mrunning){
               pausetimer();
            }
            else{
                Startimer();
            }
            }
        });
        breset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            resetimer();
            }
        });
        updatecountdowntext();

    }

    private void Startimer(){
        countdowntime = new CountDownTimer(leftinmls, 1000) {
            @Override
            public void onTick(long l) {
                leftinmls =l;
                updatecountdowntext();
            }

            @Override
            public void onFinish() {
                mrunning= false;
                bpause.setText("Start");
                bpause.setVisibility(View.INVISIBLE);
                breset.setVisibility(View.VISIBLE);


                Log.d("myTag", ""+i);

                    if (i == 0) {
                        resetimer();
                        p.setText("breath out for 5 seconds");
                        SystemClock.sleep(1000);

                        Startimer();

                    }
                    if (i == 1) {
                        resetimer();
                        p.setText("breath in for 5 seconds");

                        Startimer();

                    }
                    if (i == 2) {
                        resetimer();
                        p.setText("breath out for 5 seconds");

                        Startimer();
                    }



                i += 1;

            }
        }.start();
        mrunning= true;
        bpause.setText("Pause");
        breset.setVisibility(View.INVISIBLE);
    }
    private void pausetimer(){
        countdowntime.cancel();
        mrunning =  false;
        bpause.setText("Start");
        breset.setVisibility(View.VISIBLE);
    }
    private void resetimer(){
        leftinmls = starttimeinms;
        updatecountdowntext();
        breset.setVisibility(View.INVISIBLE);
        bpause.setVisibility(View.VISIBLE);
        p.setText("breath in for 5 seconds");
        if(i>3){
            i=0;
        }
    }

    private void updatecountdowntext(){
        int minutes =  (int)(leftinmls/1000)/60;
        int seconds =  (int)(leftinmls/1000)%60;
        String  timeleftformatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        countdown.setText(timeleftformatted);
    }
}