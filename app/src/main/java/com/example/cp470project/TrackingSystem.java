package com.example.cp470project;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TrackingSystem extends ChecklistActivity {

    protected static final String ACTIVITY_NAME = "TrackingSystem";


    //Intent intent = new Intent(this, TrackingSystem.class);
    // intent.putExtra("arr", checked);
    //startActivity(intent);

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_system);
        TextView title =  findViewById(R.id.barTitle);

        Bundle b = this.getIntent().getExtras();
        boolean checked[] = b.getBooleanArray("arr");
        ProgressBar bar = findViewById(R.id.progressBar);
        TextView fraction =  findViewById(R.id.fraction);
        //boolean checked[] = getIntent().getBooleanArrayExtra("arr");

        //boolean checked[] = new boolean[4];
        int max = checked.length;
        int progress = 0;

        //Log.d("Elements", Arrays.deepToString(new boolean[][]{checked}));

        for(int x = 0; x < max; x++){
            if(checked[x]) {
                progress++;
            }
        }

        bar.setMax(max);
        bar.setProgress(progress);
        Log.d("Progress check", "length" + checked.length);
        fraction.setText(progress + "/" + max);


        /*Button re = findViewById(R.id.refresh);

        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}