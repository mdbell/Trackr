package com.example.matt1.trackr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginAttemptActivity extends AppCompatActivity {

    public static final String USERNAME_KEY = "com.example.matt1.keys.username";
    public static final String PASSWORD_KEY = "com.example.matt1.keys.password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_attempt);

        System.out.println(getIntent().getStringExtra(USERNAME_KEY) + " " + getIntent().getStringExtra(PASSWORD_KEY));

    }
}
