package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class StressRelief extends AppCompatActivity {
public static final long starttimeinms = 5999;
private TextView countdown;
private TextView p;
private Button bpause;
private Button breset;
private CountDownTimer countdowntime;
private boolean mrunning;
private long leftinmls = starttimeinms;
private int i =0;
int seconds;
ImageView icanchor;
ObjectAnimator anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stressrelief);
        countdown =  findViewById(R.id.textView);
        bpause = findViewById(R.id.button );
        breset =findViewById(R.id.button2);
        icanchor =findViewById(R.id.icanchor);
        anim = ObjectAnimator.ofFloat(icanchor, "rotation", 0, 360);
        anim.setDuration(5999);
        anim.setRepeatCount(5);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        p = findViewById(R.id.textView2);
        String ss = String.format("Inhale",(leftinmls/ 1000)%60);
        p.setText(ss);

        bpause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (mrunning){
               pausetimer();
            }
            else{
                Startimer();
                if(anim.isPaused()){
                    anim.resume();
                    }
                else{
                    anim.start();
                }
            }
            }
        });

        breset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetimer();
            }
        });
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
                bpause.setText(R.string.start);
                bpause.setVisibility(View.INVISIBLE);
                breset.setVisibility(View.VISIBLE);
                    if (i == 0) {
                        resetimer();
                        p.setText(R.string.hold);
                        leftinmls=8000;
                        Startimer();
                        anim.setDuration(8000);
                        anim.start();
                    }
                    if (i == 1) {
                        resetimer();
                        p.setText(R.string.exhale);
                        leftinmls=9000;
                        Startimer();
                        anim.setDuration(9000);
                        anim.start();

                    }
                i += 1;

            }
        }.start();
        mrunning= true;
        bpause.setText(R.string.pause);
        breset.setVisibility(View.INVISIBLE);
        updatecountdowntext();
    }
    private void pausetimer(){
        countdowntime.cancel();
        mrunning =  false;
        bpause.setText(R.string.start);
        breset.setVisibility(View.VISIBLE);
       anim.pause();
    }

    private void resetimer(){
        updatecountdowntext();
        leftinmls = starttimeinms;
        breset.setVisibility(View.INVISIBLE);
        bpause.setVisibility(View.VISIBLE);
        p.setText(R.string.inhale);
        anim.end();
        countdown.setText("00:00");
        if(i>=3){
            i=0;
        }
    }

    private void updatecountdowntext(){
        int minutes =  (int)(leftinmls/ 1000)/60;
         seconds =  (int)(leftinmls/ 1000)%60;

        String  timeleftformatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        if (timeleftformatted.contains("00:00")&&(i==2)){
            anim.end();
            anim.setDuration(5990);
            Context context = getApplicationContext();
            CharSequence text = "Reset to run again";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        countdown.setText(timeleftformatted);
    }



}