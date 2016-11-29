package com.example.matt1.trackr;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matt1.trackr.api.Envelope;
import com.example.matt1.trackr.api.ParcelInfo;
import com.example.matt1.trackr.util.UiUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

public class TrackingActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener,
        PopupMenu.OnMenuItemClickListener, IntentConstants, AdapterView.OnItemClickListener {

    private static int CODE_ADDED = 1;
    private ArrayAdapter<ParcelInfo> adapter;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        ListView list = (ListView)findViewById(R.id.list);
        adapter = new ParcelArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        update();
    }

    public void update() {
        new TrackingAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tracking_list_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tracking_refresh:
                update();
                return true;
            case R.id.menu_tracking_add:
                addCode();
                return true;
            case R.id.menu_tracking_help:
            case R.id.menu_tracking_signout:
                UiUtil.alert(this, "Unimplemented", "We need to do this").show();
                return true;
            case R.id.menu_tracking_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                i.putExtra(TOKEN_KEY, getIntent().getStringExtra(TOKEN_KEY));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        index = i;
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.tracking_selected_menu);
        popup.show();
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_tracking_selected_details:
                showDetails();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        index = i;
        showDetails();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CODE_ADDED) {
            update();
        }
    }

    private void addCode() {
        Intent i = new Intent(this, AddItemActivity.class);
        i.putExtra(TOKEN_KEY, getIntent().getStringExtra(TOKEN_KEY));
        startActivityForResult(i, CODE_ADDED);
    }

    private void showDetails() {
        Intent i = new Intent(this, TrackingDetailsActivity.class);
        ParcelInfo p = adapter.getItem(index);
        i.putExtra(PARCEL_INFO_KEY, p.getId());
        i.putExtra(TOKEN_KEY, getIntent().getStringExtra(TOKEN_KEY));
        startActivity(i);
    }

    private class TrackingAsyncTask extends ApiAsyncTask<ParcelInfo[]> {

        @Override
        protected String getAction() {
            return "track_all";
        }

        @Override
        protected Map<String, String> getParams() {
            Map<String, String> map = new TreeMap<>();
            map.put("token", getIntent().getStringExtra(TOKEN_KEY));
            return map;
        }

        @Override
        protected void onPostQuery(Envelope<ParcelInfo[]> env) {
            if (env == null || env.getResponse() == null) {
                UiUtil.alert(TrackingActivity.this, "Error", "Unknown error getting results").show();
                return; // no results/error
            }
            if (env.getResponse().getCode() / 100 != 2) {
                UiUtil.alert(TrackingActivity.this, env.getResponse().getTitle(), env.getResponse().getMessage()).show();
            } else if (env.getData() != null) {
                adapter.clear();
                adapter.addAll(env.getData());
            }

            if(env.getData() == null || env.getData().length == 0) {
                UiUtil.toast(TrackingActivity.this,"You have no tracking numbers! Use the menu to add some!").show();
            }
        }

        @Override
        protected Envelope<ParcelInfo[]> fromJson(Gson gson, Reader r) {
            return gson.fromJson(r, new TypeToken<Envelope<ParcelInfo[]>>() {
            }.getType());
        }
    }

    class ParcelArrayAdapter extends ArrayAdapter<ParcelInfo> {
        public ParcelArrayAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.tracking_list_element, parent, false);
            }

            ParcelInfo p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.tracking_name);
                TextView tt2 = (TextView) v.findViewById(R.id.tracking_code);

                if (tt1 != null) {
                    tt1.setText(p.getName());
                }

                if (tt2 != null) {
                    tt2.setText(p.getId());
                }
            }

            return v;
        }
    }
}
