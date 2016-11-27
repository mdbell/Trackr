package com.example.matt1.trackr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matt1.trackr.api.ParcelInfo;
import com.example.matt1.trackr.util.UiUtil;

public class TrackingActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener,
        PopupMenu.OnMenuItemClickListener, IntentConstants, AdapterView.OnItemClickListener {

    private ArrayAdapter<ParcelInfo> adapter;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        ListView list = (ListView)findViewById(R.id.parcels_list);
        adapter = new ArrayAdapter<ParcelInfo>(this, R.layout.support_simple_spinner_dropdown_item);
        //TODO parse parcelstes
        for(int i = 1; i <= 100;i++) {
            adapter.add(new ParcelInfo("", i, "Test", null, null));
        }
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
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
                //TODO pass token
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

    private void showDetails() {
        Intent i = new Intent(this, TrackingDetailsActivity.class);
        ParcelInfo p = adapter.getItem(index);
        //TODO pass token
        i.putExtra(PARCEL_INFO_KEY, p.getUid());
        startActivity(i);
    }
}
