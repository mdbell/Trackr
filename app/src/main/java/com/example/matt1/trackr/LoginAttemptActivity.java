package com.example.matt1.trackr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginAttemptActivity extends AppCompatActivity implements IntentConstants{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_attempt);

        System.out.println(getIntent().getStringExtra(USERNAME_KEY) + " " + getIntent().getStringExtra(PASSWORD_KEY));
        Intent results = new Intent();

        results.putExtra(MESSAGE_KEY,"Invalid password!");
        this.setResult(RESULT_FAILED,results);
        super.onBackPressed();
    }
}
