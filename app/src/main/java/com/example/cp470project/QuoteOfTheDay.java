package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class QuoteOfTheDay extends AppCompatActivity {
    public int day = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quote_of_the_day);
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_WEEK);
        Button button = findViewById(R.id.change_day);
        drawQuote();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day++;
                if(day == 8)
                    day = 1;
                drawQuote();
            }
        });
    }

    public void drawQuote(){
        TextView quote = findViewById(R.id.quote);
        ImageView quoteBackground = findViewById(R.id.quote_background);
        switch(day){
            case Calendar.SUNDAY:
                quote.setText(R.string.sunday_quote);
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.qotd1));
                break;
            case Calendar.MONDAY:
                quote.setText(R.string.monday_quote);
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.qotd2));
                break;
            case Calendar.TUESDAY:
                quote.setText(R.string.tuesday_quote);
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.qotd3));
                break;
            case Calendar.WEDNESDAY:
                quote.setText(R.string.wednesday_quote);
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.qotd4));
                break;
            case Calendar.THURSDAY:
                quote.setText(R.string.thursday_quote);
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.qotd5));
                break;
            case Calendar.FRIDAY:
                quote.setText(R.string.friday_quote);
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.qotd6));
                break;
            case Calendar.SATURDAY:
                quote.setText(R.string.saturday_quote);
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.qotd7));
                break;
        }
    }
}