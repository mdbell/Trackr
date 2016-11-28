package com.example.matt1.trackr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.matt1.trackr.api.Envelope;
import com.example.matt1.trackr.api.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class LoginAttemptActivity extends AppCompatActivity implements IntentConstants{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_attempt);
        TextView view = (TextView)findViewById(R.id.login_attempt_message);
        Intent i = getIntent();
        if(i.hasExtra(LOGIN_MESSAGE_KEY)) {
            view.setText(i.getStringExtra(LOGIN_MESSAGE_KEY));
        }
        new LoginASyncTask(i.getBooleanExtra(LOGIN_KEY, true), getIntent().getStringExtra(USERNAME_KEY), getIntent().getStringExtra(PASSWORD_KEY)).execute();
    }

    private class LoginASyncTask extends ApiAsyncTask<LoginResponse> {

        private boolean login;
        private String user, pass;

        public LoginASyncTask(boolean login, String user, String pass) {
            this.login = login;
            this.user = user;
            this.pass = pass;
        }

        //Need to do this due to java's type erasure
        @Override
        protected Envelope<LoginResponse> fromJson(Gson gson, Reader r) {
            return gson.fromJson(r, new TypeToken<Envelope<LoginResponse>>() {
            }.getType());
        }

        @Override
        protected String getAction() {
            return login ? "login" : "register";
        }

        @Override
        protected Map<String, String> getParams() {
            Map<String, String> map = new HashMap<>();
            map.put("user", user);
            map.put("pass", pass);
            return map;
        }

        @Override
        protected void onPostQuery(Envelope<LoginResponse> env) {
            int code;
            Intent i = new Intent();
            if (env == null || env.getResponse() == null) {
                code = RESULT_FAILED;
            } else if (env.getResponse().getCode() / 100 != 2) {
                i.putExtra(TITLE_KEY, env.getResponse().getTitle());
                i.putExtra(MESSAGE_KEY, env.getResponse().getMessage());
                code = RESULT_FAILED;
            } else {
                if(env.getData() == null) {
                    code = RESULT_FAILED;
                }else {
                    i.putExtra(TOKEN_KEY, env.getData().getToken());
                    code = RESULT_OKAY;
                }
            }
            LoginAttemptActivity.this.setResult(code, i);
            LoginAttemptActivity.this.finish();
        }
    }
}
