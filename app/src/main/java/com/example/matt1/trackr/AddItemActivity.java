package com.example.matt1.trackr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matt1.trackr.api.AddResponse;
import com.example.matt1.trackr.api.Envelope;
import com.example.matt1.trackr.util.UiUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

public class AddItemActivity extends AppCompatActivity implements IntentConstants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        final Button addButton = (Button) findViewById(R.id.add_add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAdd();
            }
        });
    }

    public void attemptAdd() {
        TextView name = (TextView) findViewById(R.id.add_name);
        TextView code = (TextView) findViewById(R.id.add_code);
        TextView desc = (TextView) findViewById(R.id.add_desc);
        new AddItemAsyncTask(name.getText().toString(), desc.getText().toString(), code.getText().toString()).execute();
    }

    class AddItemAsyncTask extends ApiAsyncTask<AddResponse> {

        private String name;
        private String desc;
        private String code;

        public AddItemAsyncTask(String name, String desc, String tracking) {
            this.name = name;
            this.desc = desc;
            this.code = tracking;
        }

        @Override
        protected String getAction() {
            return "track_add";
        }

        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new TreeMap<>();
            params.put("token", getIntent().getStringExtra(TOKEN_KEY));
            params.put("name", name);
            params.put("desc", desc);
            params.put("tracking", code);
            return params;
        }

        @Override
        protected void onPostQuery(Envelope<AddResponse> env) {
            if (env == null || env.getResponse() == null) {
                UiUtil.alert(AddItemActivity.this, "Error", "Unknown error").show();
                return;
            }
            if (env.getResponse().getCode() / 100 != 2) {
                UiUtil.alert(AddItemActivity.this, env.getResponse().getTitle(),
                        env.getResponse().getMessage()).show();
                return;
            }

            if (env.getData() == null) {
                UiUtil.toast(AddItemActivity.this, "No results from server...").show();
                setResult(RESULT_FAILED);
                return;
            }

            UiUtil.toast(AddItemActivity.this, "Code was added!").show();
            finish();
            setResult(RESULT_OKAY);
        }

        @Override
        protected Envelope<AddResponse> fromJson(Gson gson, Reader r) {
            return gson.fromJson(r, new TypeToken<Envelope<AddResponse>>() {
            }.getType());
        }
    }
}
