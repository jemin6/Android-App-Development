package com.example.jeminson.tidever32;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    private DataAccessLayer dal = new DataAccessLayer(this);

    private Spinner locationSpinner;
    String locationSelection = "Wauna";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up location selection spinner
        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                locationSelection = "Wauna";
                break;
            case 1:
                locationSelection = "Garibaldi";
                break;
            case 2:
                locationSelection = "Skamokawa";
                break;
        }
        // Get the selected location
        //getTide("OR", locationSelection);
    } // End onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    public class RestTask extends AsyncTask<String, Void, TideItems> {

        private String stationId;
        private String beginDate;
        private String endDate;

        @Override
        protected TideItems doInBackground(String... params) {


            String baseUrl = "https://opendap.co-ops.nos.noaa.gov";

            stationId = params[0];
            beginDate = params[1];
            endDate = params[2];

            String query = "/axis/webservices/highlowtidepred/response.jsp?stationId=" + stationId +
                    "&beginDate=" + beginDate + "&endDate=" + endDate + "&datum=MLLW&unit=0&timeZone=0&format=xml&Submit=Submit";
            TideItems items = null;

            try {
                URL url = new URL(baseUrl + query);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream in = connection.getInputStream();

                if (in != null) {
                    items = dal.parseXmlStream(in);
                    items.setStationId(stationId);
                    items.setBeginDate(beginDate);
                    items.setEndDate(endDate);

                }
            } catch (Exception e) {
                Log.e("tide", "doInBackground error: " + e.getLocalizedMessage());
            }

            return items;
        }
    }
}
