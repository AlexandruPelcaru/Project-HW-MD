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
import com.parse.SaveCallback;

public class HomeActivity extends Activity {
	String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
	String endBaseURL = "&units=metric";
	private EditText etCity, etAddLink, etAddLinkDenumire;
	private TextView tvWeather;
	private Button btnRequest, btnProfile, btnAddLink, btnNews;
	private ListView lvLinks;
	private AdapterLinks adapter = new AdapterLinks();
	boolean check = false;
	JSONParse asyncTask;
	private List<LinkModel> linkList = new ArrayList<LinkModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		tvWeather = (TextView) findViewById(R.id.tvWeather);
		etCity = (EditText) findViewById(R.id.etCity);
        etAddLink=(EditText) findViewById(R.id.etAddLink);
        etAddLinkDenumire = (EditText) findViewById(R.id.etAddLinkDenumire);

		btnRequest = (Button) findViewById(R.id.btnGetWeather);
        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnAddLink = (Button) findViewById(R.id.btnAddLinks);
        btnNews = (Button) findViewById(R.id.btnSeeNews);

		lvLinks = (ListView) findViewById(R.id.lvLinkuri);
		tvWeather.setText("Current Degrees: Not requested");
		getAllLinks();
		System.out.println(baseURL);
		btnRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                String etCityString=etCity.getText().toString().trim();
				if (!etCityString.equals("")) {
					baseURL = baseURL + (etCity.getText().toString())
							+ endBaseURL;
                   // Log.e("TAG","dasdasfsafsfasdfadfdafadfdfsdfadgsdgsadgsa");
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

        btnNews.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NewsActivity.class));

            }
        });

        btnAddLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject link = new ParseObject("Linkuri");
                String etAddLinkString = etAddLink.getText().toString();
                String etAddLinkDenumireString = etAddLinkDenumire.getText().toString();

                if(etAddLinkString!=null && etAddLinkDenumireString!=null) {
                    link.put("denumire", etAddLinkDenumireString);
                    link.put("adresa", etAddLinkString);
                    link.put("userID", ParseUser.getCurrentUser().getObjectId());

                    link.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                onSaveFinished("");
                            } else {
                                onSaveFinished(e.getMessage());
                            }
                        }
                    });
                }
                else
                {
                    if(etAddLinkString==null) {
                        Toast.makeText(getApplicationContext(), "Fill in the link address",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(etAddLinkDenumireString==null){
                        Toast.makeText(getApplicationContext(), "Fill in the name of the link",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Fill in the name and address of the link",
                                Toast.LENGTH_LONG).show();
                    }
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
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(linkList.get(position).getAddress()));
				startActivity(i);

			}
		});

	}

    public void onSaveFinished(String error) {
        if (error.equals("")) {

            Toast.makeText(getApplicationContext(), "Add Succesfull", Toast.LENGTH_LONG)
                    .show();
            finish();
            startActivity(getIntent());
        } else {
            Toast.makeText(getApplicationContext(), "Add Unsuccesfull", Toast.LENGTH_LONG)
                    .show();
        }
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

	private void getAllLinks() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Linkuri");
		query.whereEqualTo("userID", ParseUser.getCurrentUser().getObjectId());
		try {
			List<ParseObject> objects = query.find();
			if (objects.size() > 0) {
				Log.e("size mai mare ca 0", objects.size() + " ");
				for (ParseObject object : objects) {
					LinkModel link = new LinkModel();
					link.setAddress(object.getString("adresa"));
					link.setName(object.getString("denumire"));
					link.setUser(ParseUser.getCurrentUser().getObjectId());
					linkList.add(link);
				}

				adapter = new AdapterLinks(getApplicationContext(), linkList);
				lvLinks.setAdapter(adapter);

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
