package com.example.mobiledev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends Activity {
	private EditText etUsername, etPassword;
	private Button btnLogin, btnRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,
						RegisterActivity.class));

			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onLoginPressed(getUsername(), getPassword());
			}
		});
	}

	public String getUsername() {
		return etUsername.getEditableText().toString();
	}

	public String getPassword() {
		return etPassword.getEditableText().toString();
	}

	public void onLoginPressed(String username, String password) {
		ParseUser.logInInBackground(username, password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (e == null) {
					onLoginSuccessful("");
				} else {
					onLoginSuccessful(e.getMessage());
				}

			}
		});
	}

	public void onLoginSuccessful(String error) {
		if (error.equals("")) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
		} else {
			Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG)
					.show();
		}
	}
}
