package com.example.matt1.trackr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginAttempt();
            }
        });
    }

    private void onLoginAttempt(){
        Intent i = new Intent(this,LoginAttemptActivity.class);
        TextView username = (TextView)findViewById(R.id.username);
        TextView password = (TextView)findViewById(R.id.password);

        i.putExtra(LoginAttemptActivity.USERNAME_KEY,username.getText().toString());
        i.putExtra(LoginAttemptActivity.PASSWORD_KEY,password.getText().toString());

        finish();
        startActivity(i);
    }
}
