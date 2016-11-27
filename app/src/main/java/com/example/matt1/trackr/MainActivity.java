package com.example.matt1.trackr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = (Button)findViewById(R.id.login_button);
        final TextView username = (TextView)findViewById(R.id.username);
        final TextView password = (TextView)findViewById(R.id.password);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginButton.setEnabled(username.getText().length() > 0 && password.getText().length() > 0);
            }
        };
        username.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
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

        //finish();
        startActivity(i);
    }
}
