package com.example.matt1.trackr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.matt1.trackr.util.UiUtil;

public class MainActivity extends AppCompatActivity implements IntentConstants{

    static final int LOGIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_NAME, 0);
        final Button loginButton = (Button)findViewById(R.id.login_button_login);
        final Button registerButton =(Button)findViewById(R.id.login_button_register);
        final TextView username = (TextView)findViewById(R.id.login_username);
        final TextView password = (TextView)findViewById(R.id.login_password);
        final CheckBox remember = (CheckBox) findViewById(R.id.login_remember_me);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean b = username.getText().length() > 0 && password.getText().length() > 0;
                loginButton.setEnabled(b);
                registerButton.setEnabled(b);
            }
        };
        username.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(REMEMBER_ME_KEY, b);
                if (!b) {
                    editor.remove(USERNAME_KEY);
                    editor.remove(PASSWORD_KEY);
                }
                editor.commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefs.getBoolean(REMEMBER_ME_KEY, false)) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(USERNAME_KEY, username.getText().toString());
                    editor.putString(PASSWORD_KEY, password.getText().toString());
                    editor.commit();
                }
                onLoginAttempt();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (prefs.getBoolean(REMEMBER_ME_KEY, false)) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(USERNAME_KEY, username.getText().toString());
                    editor.putString(PASSWORD_KEY, password.getText().toString());
                    editor.commit();
                }
                onRegisterAttempt();
            }
        });

        boolean rem = prefs.getBoolean(REMEMBER_ME_KEY, false);

        remember.setChecked(rem);

        if (rem) {
            username.setText(prefs.getString(USERNAME_KEY, ""));
            password.setText(prefs.getString(PASSWORD_KEY, ""));
        }
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

    private void onRegisterAttempt(){
        Intent i = new Intent(this,LoginAttemptActivity.class);
        TextView username = (TextView)findViewById(R.id.login_username);
        TextView password = (TextView)findViewById(R.id.login_password);

        i.putExtra(LOGIN_KEY,false);
        i.putExtra(LOGIN_MESSAGE_KEY,"Registering...");
        i.putExtra(USERNAME_KEY,username.getText().toString());
        i.putExtra(PASSWORD_KEY,password.getText().toString());

        //finish();
        startActivityForResult(i, LOGIN_REQUEST);
    }

    private void onLoginAttempt(){
        Intent i = new Intent(this,LoginAttemptActivity.class);
        TextView username = (TextView)findViewById(R.id.login_username);
        TextView password = (TextView)findViewById(R.id.login_password);
        i.putExtra(LOGIN_MESSAGE_KEY,"Logging In...");
        i.putExtra(USERNAME_KEY,username.getText().toString());
        i.putExtra(PASSWORD_KEY,password.getText().toString());

        //finish();
        startActivityForResult(i, LOGIN_REQUEST);
    }
}
