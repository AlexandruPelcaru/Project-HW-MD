package com.example.mobiledev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class ProfileActivity extends Activity {

    private Button changePass, saveChanges, logOut;
    private EditText Etfname, Etlname, Etage, Etaddress, Etmobilno, Etgender;
    private String fname, lname, age, address, mobilno, gender;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Etfname = (EditText) findViewById(R.id.etFname);
        Etlname = (EditText) findViewById(R.id.etLName);
        Etage = (EditText) findViewById(R.id.etAge);
        Etaddress = (EditText) findViewById(R.id.etStreetAddress);
        Etmobilno = (EditText) findViewById(R.id.etMobileNo);
        Etgender = (EditText) findViewById(R.id.etGender);


        user = ParseUser.getCurrentUser();
        String parseFname = (String) user.get("FirstName");
        String parseLname = (String) user.get("LastName");
        String parseAge = (String) user.get("Age");
        String parseGender = (String) user.get("gender");
        String parseMobileNo = (String) user.get("phoneNumber");
        String parseAddress = (String) user.get("address");


        if (user.get("FirstName") != null) {
            Etfname.setText(parseFname);
        }
        if (user.get("LastName") != null) {
            Etlname.setText(parseLname);
        }
        if (user.get("Age") != null) {
            Etage.setText(parseAge);
        }
        if (user.get("gender") != null) {
            Etgender.setText(parseGender);
        }
        if (user.get("phoneNumber") != null) {
            Etmobilno.setText(parseMobileNo);
        }
        if (user.get("address") != null) {
            Etaddress.setText(parseAddress);
        }


        changePass = (Button) findViewById(R.id.btnChangePassword);
        saveChanges = (Button) findViewById(R.id.btnSaveProfile);
        logOut = (Button) findViewById(R.id.btnLogOut);


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = Etfname.getText().toString();
                lname = Etlname.getText().toString();
                age = Etage.getText().toString();
                address = Etaddress.getText().toString();
                mobilno = Etmobilno.getText().toString();
                gender = Etgender.getText().toString();
                user.put("FirstName", fname);
                user.put("LastName", lname);
                user.put("Age", age);
                user.put("address", address);
                user.put("phoneNumber", mobilno);
                user.put("gender", gender);
                user.saveInBackground(new SaveCallback() {

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
        });



        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,
                        ChangePassActivity.class));
            }
        });


    }

    public void onSaveFinished(String error) {
        if (error.equals("")) {

            Toast.makeText(getApplicationContext(), "Save sucessfull", Toast.LENGTH_LONG)
                    .show();
            finish();
            startActivity(getIntent());
        } else {
            Toast.makeText(getApplicationContext(), "Save unsucessfull", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
