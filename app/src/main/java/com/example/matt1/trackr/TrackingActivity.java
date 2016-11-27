package com.example.matt1.trackr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matt1.trackr.util.UiUtil;

public class TrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        ListView list = (ListView)findViewById(R.id.parcels_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);

        for(int i = 1; i <= 100;i++) {
            adapter.add("This will be a tracking #" + i);
        }
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tracking_list_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tracking_add:
            case R.id.menu_tracking_help:
            case R.id.menu_tracking_search:
            case R.id.menu_tracking_signout:
                UiUtil.alert(this, "Unimplemented", "We Need to do this").show();
                return true;
            case R.id.menu_tracking_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                //TODO pass params
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
