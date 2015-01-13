package com.example.mobiledev;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class HomeActivity extends Activity {
    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String endBaseURL = "&units=metric";
    private EditText etCity;
    private TextView tvWeather;
    private Button btnRequest, btnProfile;
    private ListView lvLinks;
    boolean check = false;
    JSONParse asyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWeather = (TextView) findViewById(R.id.tvWeather);
        etCity = (EditText) findViewById(R.id.etCity);

        btnRequest = (Button) findViewById(R.id.btnGetWeather);
        btnProfile = (Button) findViewById(R.id.btnProfile);
        lvLinks = (ListView) findViewById(R.id.lvLinkuri);
        tvWeather.setText("Current Degrees: Not requested");

        btnRequest.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String etCityString=etCity.getText().toString().trim();
                if (!etCityString.equals("")) {
                    baseURL = baseURL + (etCity.getText().toString())
                            + endBaseURL;

                    check = true;
                }
                if (check == true) {
                    if (isNetworkAvailable() == true) {
                        asyncTask = (JSONParse) new JSONParse().execute();
                    } else {
                        tvWeather.setText("No Internet Connection");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No city inserted",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,
                        ProfileActivity.class));
            }
        });

        lvLinks.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });

    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Getting data...");
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(baseURL);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                JSONArray array = new JSONArray();
                json.toJSONArray(array);
                JSONObject x = json.getJSONObject("main");
                tvWeather.setText("Current Degrees: " + x.getString("temp")+"°C");

            } catch (JSONException e) {
                tvWeather.setText("Current Degrees: Not Available");
                e.printStackTrace();
            }
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
