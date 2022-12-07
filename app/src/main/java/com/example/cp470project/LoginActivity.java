package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";

    public static String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton                   = findViewById(R.id.loginButton);
        EditText usernameEntry               = findViewById(R.id.usernameEntry);
        EditText passwordEntry               = findViewById(R.id.passwordEntry);
        SharedPreferences usernamePreference = getSharedPreferences("DefaultUsername", MODE_PRIVATE);
        String defaultUsername               = usernamePreference.getString("DefaultUsername","username");
        if(defaultUsername != "username")
            usernameEntry.setText(defaultUsername);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String inputUsername    = usernameEntry.getText().toString();
                String inputPassword = passwordEntry.getText().toString();
                if(inputPassword.length() > 0) {
                    SharedPreferences.Editor prefEditor = usernamePreference.edit();
                    prefEditor.putString("DefaultUsername", inputUsername);
                    prefEditor.commit();
                    username = inputUsername;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (inputPassword.length() == 0){
                    Toast toast = Toast.makeText(LoginActivity.this,R.string.missing_password,Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(LoginActivity.this,R.string.missing_username,Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }
}