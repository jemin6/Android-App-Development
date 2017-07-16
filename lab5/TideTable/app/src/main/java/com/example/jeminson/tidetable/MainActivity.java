package com.example.jeminson.tidetable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements OnItemClickListener {

    private RSSFeed feed;
    static final String DATE = "date";
    static final String TIDE = "tide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileIO io = new FileIO(getApplicationContext());
        feed = io.readFile();

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        for (RSSItem item : feed) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(DATE, item.getTideDateFormatted());
            map.put(TIDE, item.getHighLow()+ ": " + item.getTime());
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.listview_items, new String[]{DATE,TIDE}, new int[]{R.id.dateTextView,R.id.tideTextView});

        // Pass the data adapter to the List View
        ListView itemsListView = (ListView)findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(this);
    }

    // ** Event Handler **

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RSSItem item = feed.get(position);
        Toast.makeText(this, item.getPredInFt() + "ft., " + item.getPredInCm() + " cm", Toast.LENGTH_LONG).show();
    }
}
