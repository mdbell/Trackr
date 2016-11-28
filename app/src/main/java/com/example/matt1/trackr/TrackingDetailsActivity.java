package com.example.matt1.trackr;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matt1.trackr.api.DetailedParcelInfo;
import com.example.matt1.trackr.api.Envelope;
import com.example.matt1.trackr.util.UiUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

public class TrackingDetailsActivity extends AppCompatActivity implements IntentConstants {

    ArrayAdapter<DetailedParcelInfo.ParcelRow> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_details);
        adapter = new ParcelRowArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);

        ListView view = (ListView) findViewById(R.id.tracking_details_details);
        view.setAdapter(adapter);
        update();
    }

    private void update() {
        TextView name = (TextView) findViewById(R.id.tracking_details_name);
        TextView code = (TextView) findViewById(R.id.tracking_details_code);
        TextView desc = (TextView) findViewById(R.id.tracking_details_desc);

        name.setText("Loading...");
        code.setText("");
        desc.setText("");
        adapter.clear();
        new DetailsApiAsyncTask().execute();
    }

    private class DetailsApiAsyncTask extends ApiAsyncTask<DetailedParcelInfo> {

        @Override
        protected String getAction() {
            return "track_details";
        }

        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new TreeMap<>();
            params.put("token", getIntent().getStringExtra(TOKEN_KEY));
            params.put("id", getIntent().getStringExtra(PARCEL_INFO_KEY));
            return params;
        }

        @Override
        protected void onPostQuery(Envelope<DetailedParcelInfo> env) {
            if (env == null || env.getResponse() == null) {
                UiUtil.alert(TrackingDetailsActivity.this, "Error", "Unknown error").show();
                return;
            }
            if (env.getResponse().getCode() / 100 != 2) {
                UiUtil.alert(TrackingDetailsActivity.this, env.getResponse().getTitle(),
                        env.getResponse().getMessage()).show();
                return;
            }

            if (env.getData() == null) {
                UiUtil.alert(TrackingDetailsActivity.this, "Error", "No results from server...");
                return;
            }
            DetailedParcelInfo info = env.getData();
            TextView name = (TextView) findViewById(R.id.tracking_details_name);
            TextView code = (TextView) findViewById(R.id.tracking_details_code);
            TextView desc = (TextView) findViewById(R.id.tracking_details_desc);

            name.setText(info.getName());
            code.setText(info.getId());
            desc.setText(info.getDesc());
            adapter.addAll(info.getRows());
        }

        @Override
        protected Envelope<DetailedParcelInfo> fromJson(Gson gson, Reader r) {
            return gson.fromJson(r, new TypeToken<Envelope<DetailedParcelInfo>>() {
            }.getType());
        }
    }

    class ParcelRowArrayAdapter extends ArrayAdapter<DetailedParcelInfo.ParcelRow> {
        public ParcelRowArrayAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.tracking_details_element, parent, false);
            }

            DetailedParcelInfo.ParcelRow p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.tracking_details_location);
                TextView tt2 = (TextView) v.findViewById(R.id.tracking_details_status);
                TextView tt3 = (TextView) v.findViewById(R.id.tracking_details_time);

                if (tt1 != null) {
                    String loc = p.getLocation();
                    if (loc == null) {
                        loc = "N/A";
                    }
                    tt1.setText("Location: " + loc);
                }

                if (tt2 != null) {
                    String status = p.getStatus();
                    if (status == null) {
                        status = "N/A";
                    }
                    tt2.setText("Status:" + status);
                }

                if (tt3 != null) {
                    tt3.setText("Timestamp:" + UiUtil.formatTimestamp(p.getTime()));
                }
            }

            return v;
        }
    }
}
