package com.example.jeminson.tidetable11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class TideActivity extends AppCompatActivity {

    private RSSFeed feed;
    static final String DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tide);

        FileIO io = new FileIO(getApplicationContext());
        feed = io.readFile();

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        for(RSSItem item : feed) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(DATE, item.getDateFormatted());

            data.add(map);

        } // End for

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.listview_items, new String[]{DATE}, new int[]{R.id.dateTextView,});

        ListView itemsListView = (ListView)findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);

    } // End onCreate

} //End TideActivity
