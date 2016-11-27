package com.example.matt1.trackr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matt1.trackr.util.UiUtil;

public class TrackingActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener,
        PopupMenu.OnMenuItemClickListener{

    private ArrayAdapter<String> adapter;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        ListView list = (ListView)findViewById(R.id.parcels_list);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);

        for(int i = 1; i <= 100;i++) {
            adapter.add("This will be a tracking #" + i);
        }
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(this);
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
            case R.id.menu_tracking_refresh:
            case R.id.menu_tracking_signout:
                UiUtil.alert(this, "Unimplemented", "We need to do this").show();
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
        return false;
    }
}
