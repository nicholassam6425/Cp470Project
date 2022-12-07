package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class QuoteOfTheDay extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "QuoteOfTheDay";

    public int day = 1;

    private class QuoteQuery extends AsyncTask<String, Integer, String> {
        public String quote;
        TextView textView = findViewById(R.id.quote);
        ProgressBar bar = findViewById(R.id.progressBar);

        QuoteQuery(Context ctx){

        }
        @Override
        protected void onPostExecute(String a){
            bar.setVisibility(View.INVISIBLE);
            textView.setText(quote);
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            bar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                InputStream result = downloadUrl("https://www.zenquotes.io/api/today");
                publishProgress(75);
                GetQuoteFromJson(result);
                publishProgress(100);
                return "true";
            }
            catch(Exception e){
                return null;
            }
        }

        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(1000000 /* milliseconds */);
            conn.setConnectTimeout(1500000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        }

        private void GetQuoteFromJson(InputStream input){
            try {
                // Feed InputStream into string, possibly replace with IOUtils.toString if allowed
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
                StringBuilder jsonString = new StringBuilder();
                String line = inputReader.readLine();
                while (line != null) {
                    jsonString.append(line + "\n");
                    line = inputReader.readLine();
                }
                // API sends a json array despite only having one element, so just read the 0th element
                JSONArray jsonArray = new JSONArray(jsonString.toString());
                JSONObject quoteObj = jsonArray.getJSONObject(0);
                quote = quoteObj.getString("q") + "\n-" + quoteObj.getString("a");
            }
            catch (Exception e){
                // Input is mangled in some way
                quote = "ERROR: Corrupted Input";
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quote_of_the_day);
        ProgressBar bar = findViewById(R.id.progressBar);
        Calendar calendar = Calendar.getInstance();
        bar.setVisibility(View.VISIBLE);
        day = calendar.get(Calendar.DAY_OF_WEEK);
        ResolveDayImage();
        bar.setProgress(50);
        QuoteQuery query = new QuoteQuery(this);
        query.execute();
    }
    private void ResolveDayImage(){
        ImageView quoteBackground = findViewById(R.id.quote_background);
        switch(day){
            case Calendar.SUNDAY:
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.qotd1));
                break;
            case Calendar.MONDAY:
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.qotd2));
                break;
            case Calendar.TUESDAY:
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.qotd3));
                break;
            case Calendar.WEDNESDAY:
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.qotd4));
                break;
            case Calendar.THURSDAY:
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.qotd5));
                break;
            case Calendar.FRIDAY:
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.qotd6));
                break;
            case Calendar.SATURDAY:
                quoteBackground.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.qotd7));
                break;
        }
    }
}