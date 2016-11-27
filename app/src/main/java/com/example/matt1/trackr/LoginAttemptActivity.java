package com.example.matt1.trackr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.matt1.trackr.api.Envelope;
import com.example.matt1.trackr.api.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
        new LoginASyncTask(i.getBooleanExtra(LOGIN_KEY,true)).execute(getIntent().getStringExtra(USERNAME_KEY), getIntent().getStringExtra(PASSWORD_KEY));
    }

    private class LoginASyncTask extends AsyncTask<String, Void, Envelope<LoginResponse>> {

        private boolean login;

        public LoginASyncTask(boolean login) {
            this.login = login;
        }

        @Override
        protected Envelope<LoginResponse> doInBackground(String... strings) {
            try {
                String action = login ? "login" : "register";
                String user = URLEncoder.encode(strings[0],"UTF-8");
                String pass = URLEncoder.encode(strings[1],"UTF-8");
                String url = String.format("https://trackr.mdbell.me/query.php?action=%s&user=%s&pass=%s",action, user, pass);
                URLConnection conn = new URL(url).openConnection();
                conn.connect();

                InputStreamReader reader = new InputStreamReader(conn.getInputStream());
                Gson gson = new Gson();

                return gson.fromJson(reader,new TypeToken<Envelope<LoginResponse>>(){}.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Envelope<LoginResponse> env) {
            super.onPostExecute(env);

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
