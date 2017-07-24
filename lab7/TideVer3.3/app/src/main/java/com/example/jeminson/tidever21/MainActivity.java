package com.example.jeminson.tidever21;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


import java.text.SimpleDateFormat;

import static com.example.jeminson.tidever21.TideSQLiteHelper.*;


public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    private static final String TAG = "FirstActivity";
    private SimpleDateFormat dateOutFormat = new SimpleDateFormat("yyyy/MM/dd");

    // Used for setting location on the MainActivity
    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String locationSelection = "97420";
    String stateSelection = "OR";
    String dateSelection = "2017/01/01";
    SimpleCursorAdapter adapter = null;
    ListView itemsListView;


    // Button that goes to SecondActivity
    private Button showTideButton;

    // TextView that shows the selected date from the picker
    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsListView = (ListView)findViewById(R.id.tideListView);


        // Set up location selection spinner
        Spinner locationSpinner = (Spinner)findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

        showTideButton = (Button) findViewById(R.id.showTideButton);
        showTideButton.setOnClickListener(this);

        // For date picker
        displayDate = (TextView) findViewById(R.id.displayDate);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        }); // End onClick

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                year = year - 1900;
                Log.d(TAG, "onDateSet: yyyy/MM/dd: " + year + "/" + month + "/" + day);

                Date date = new Date(year, month, day);
                dateString = dateOutFormat.format(date);

                displayDate.setText(dateString);
            }
        };

    } // End onCreate

    // onClick which send saved data from spinner to SecondActivity when press the showTideButton
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.showTideButton) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("location", locationSelection);
            intent.putExtra("date", dateString);
            startActivity(intent);
        } // End if
    } // End onClick

    // Spinner selection
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                locationSelection = "Wauna";
                break;
            case 1:
                locationSelection = "Charleston";
                break;
            case 2:
                locationSelection = "Southbeach";
                break;
            case 3:
                locationSelection = "Dick Point";
                break;
            case 4:
                locationSelection = "Port Orford";
                break;
            case 5:
                locationSelection = "Depoe Bay";
                break;
            case 6:
                locationSelection = "Cape Horn";
                break;
            case 7:
                locationSelection = "Longview";
                break;
            case 8:
                locationSelection = "Garibaldi";
                break;
            case 9:
                locationSelection = "Skamokawa";
                break;
            case 10:
                locationSelection = "Saint Helens";
                break;
            case 11:
                locationSelection = "Vancouver";
                break;
        }
        // Get the selected location
        //getTide(stateSelection, locationSelection);
    } // End onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
    /************* ----------- Private Methods ------------- ***************/

    private void getTide(String state, String city) {
        cursor = dal.getTideFromDb(state, city, new Date());
        if(cursor.getCount() == 0) {
            new RestTask().execute(state, city);
        }
        else {
            displayTide();
        }
    }

    private void displayTide() {
        // Set up the adapter for the ListView to display the forecast
        adapter = new SimpleCursorAdapter(this, R.layout.listview_items, cursor,
                new String[]{
                        TIME,
                        PRED,
                        TYPE},
                new int[]{
                        R.id.timeTextView,
                        R.id.predTextView,
                        R.id.typeTextView
                },
                0 );	// no flags
        itemsListView.setAdapter(adapter);
    }


    /************* ----------- Nested Class ------------- ***************/

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
                    items.setCity(stationId);
                }
            } catch (Exception e) {
                Log.e("tide", "doInBackground error: " + e.getLocalizedMessage());
            }

            return items;
        }


        @Override
        protected void onPostExecute(TideItems items) {
            if (items != null && items.size() != 0) {
                dal.putTideIntoDb(items);
                cursor = dal.getTideFromDb("OR", stationId, new Date());
                displayTide();
            }
        }
    }
} // End MainActivity
