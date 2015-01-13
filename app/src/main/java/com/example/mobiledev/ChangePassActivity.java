package com.example.mobiledev;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ChangePassActivity extends Activity {

    private Button savePass;
    private EditText EtOldPass, EtNewPass, EtReNewPass;
    private ParseUser user;
    private String oldPass, newPass, reNewPass, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        savePass=(Button) findViewById(R.id.btnSavePass);
        EtOldPass = (EditText) findViewById(R.id.etOldPasswordChange);
        EtNewPass = (EditText) findViewById(R.id.etNewPasswordChange);
        EtReNewPass = (EditText) findViewById(R.id.etRePasswordChange);

        savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = ParseUser.getCurrentUser();
                userPass=user.getString("Password");
                oldPass=EtOldPass.getText().toString();
                if(userPass.equals(oldPass)){
                    newPass = EtNewPass.getText().toString();
                    reNewPass=EtReNewPass.getText().toString();

                    if(newPass.equals(reNewPass)){
                        if(newPass.equals(oldPass)){
                            Toast.makeText(getApplicationContext(), "New Password is the same as Old Password", Toast.LENGTH_LONG)
                                    .show();
                        }
                        else
                        {
                            user.setPassword(newPass);
                            user.put("Password",newPass);
                            user.saveInBackground(new SaveCallback() {

                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        onSavePassFinished("");
                                    } else {
                                        onSavePassFinished(e.getMessage());
                                    }
                                }
                            });

                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG)
                                .show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
    }

    public void onSavePassFinished(String error) {
        if (error.equals("")) {

            Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_LONG)
                    .show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Password Changed Unsuccessfully", Toast.LENGTH_LONG)
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_pass, menu);
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
