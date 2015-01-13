package com.example.mobiledev;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {
    private EditText etUsername, etPassword;
    private Button btnRegister;
    private boolean registerSuccesful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etUsernameRegister);
        etPassword = (EditText) findViewById(R.id.etPasswordRegister);
        btnRegister = (Button) findViewById(R.id.btnRegisterRegister);

        btnRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onRegisterPressed(getUsername(), getPassword());
            }
        });
    }

    public String getUsername() {
        return etUsername.getEditableText().toString();
    }

    public String getPassword() {
        return etPassword.getEditableText().toString();
    }

    public void onRegisterPressed(String username, String password) {
        ParseUser user = new ParseUser();
        //Log.i("TAG", "da click");
        user.setUsername(username);
        user.setPassword(password);
        user.put("Password",password);
        user.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    onRegisterFinished("");
                } else {
                    onRegisterFinished(e.getMessage());
                }

            }
        });
    }

    public void onRegisterFinished(String error) {
        if (error.equals("")) {

            finish();
        } else {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
