package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
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

import java.util.Locale;

public class stressrelief extends AppCompatActivity {
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
Animation rounding;
Animation rounding2;
Animation rounding3;
ObjectAnimator anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stressrelief);
        countdown =  findViewById(R.id.textView);
        bpause = findViewById(R.id.button );
        breset =findViewById(R.id.button2);
        icanchor =findViewById(R.id.icanchor);

//    rounding= AnimationUtils.loadAnimation(this,R.anim.rounding);
//    rounding2= AnimationUtils.loadAnimation(this,R.anim.rounding);
//    rounding3 = AnimationUtils.loadAnimation(this, R.anim.rounding);
        anim = ObjectAnimator.ofFloat(icanchor, "rotation", 0, 360);


//        icanchor.setRotation(30);
        anim.setDuration(5999);
        anim.setRepeatCount(5);
        anim.setRepeatMode(ObjectAnimator.RESTART);

        p = findViewById(R.id.textView2);
        String ss = String.format("Inhale",(leftinmls/ 1000)%60);
        p.setText(ss);


//
//        int color = Color.parseColor("black"); //The color u want
//        icanchor.setColorFilter(color);

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
     // updatecountdowntext();

    }

    private void Startimer(){
        countdowntime = new CountDownTimer(leftinmls, 1000) {

            @Override
            public void onTick(long l) {
                leftinmls =l;
                Log.d("time:", ""+leftinmls);

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

                        p.setText("Hold");
                        leftinmls=8000;
                        Startimer();

//                        rounding2.setDuration(8000);
//                        icanchor.startAnimation(rounding2);
//                        anim.setCurrentFraction(0.185f);

                        anim.setDuration(8000);
                        anim.start();

                    }
                    if (i == 1) {
                        resetimer();
                        p.setText("Exhale");
                        leftinmls=9000;

                        Startimer();
//                        rounding3.setDuration(9000);
//                        icanchor.startAnimation(rounding3);
                        anim.setDuration(9000);
                        anim.start();

                    }
//                if (i == 2) {
//                    Log.d("22222222222", ""+i);
//                    p.setText("breath in for 5 seconds");
//                    leftinmls=6000;
//
//                    Startimer();
//                }


                    else{

                    }



                i += 1;

            }
        }.start();
        mrunning= true;
        bpause.setText("Pause");
        breset.setVisibility(View.INVISIBLE);
        updatecountdowntext();
//        icanchor.startAnimation(rounding);
//        anim.start();

    }
    private void pausetimer(){
        countdowntime.cancel();
        mrunning =  false;
        bpause.setText("Start");
        breset.setVisibility(View.VISIBLE);

       anim.pause();
    }

    private void resetimer(){
        updatecountdowntext();
        leftinmls = starttimeinms;
        breset.setVisibility(View.INVISIBLE);
        bpause.setVisibility(View.VISIBLE);
        p.setText("Inhale");
        anim.end();
        countdown.setText("00:00");
        if(i>=3){
            i=0;
        }


    }

    private void updatecountdowntext(){
        int minutes =  (int)(leftinmls/ 1000)/60;
         seconds =  (int)(leftinmls/ 1000)%60;

        Log.d("seconds time when reset:", ""+seconds);

        String  timeleftformatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        if (timeleftformatted.contains("00:00")&&(i==2)){
            anim.end();
            anim.setDuration(5990);


        }
        countdown.setText(timeleftformatted);
    }



}