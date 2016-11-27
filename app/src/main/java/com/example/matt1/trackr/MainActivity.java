package com.example.matt1.trackr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matt1.trackr.util.UiUtil;

public class MainActivity extends AppCompatActivity implements IntentConstants{


    static final int LOGIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = (Button)findViewById(R.id.login_button);
        final TextView username = (TextView)findViewById(R.id.login_username);
        final TextView password = (TextView)findViewById(R.id.login_password);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOGIN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OKAY) {

                Intent i = new Intent(this, TrackingActivity.class);
                i.putExtra(TOKEN_KEY,data.getStringExtra(TOKEN_KEY));
                finish();
                startActivity(i);
            }else{
                String title = data.hasExtra(TITLE_KEY) ? data.getStringExtra(TITLE_KEY) : "Failed Login";
                String message = data.hasExtra(MESSAGE_KEY) ? data.getStringExtra(MESSAGE_KEY) : "Unknown Error!";
                UiUtil.alert(this,title,message).show();
            }
        }
    }

    private void onLoginAttempt(){
        Intent i = new Intent(this,LoginAttemptActivity.class);
        TextView username = (TextView)findViewById(R.id.login_username);
        TextView password = (TextView)findViewById(R.id.login_password);

        i.putExtra(USERNAME_KEY,username.getText().toString());
        i.putExtra(PASSWORD_KEY,password.getText().toString());

        //finish();
        startActivityForResult(i, LOGIN_REQUEST);
    }
}
