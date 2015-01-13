package com.example.mobiledev;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {

	@Override
	public void onCreate() {
		Parse.initialize(this, "syO66JfVUwFIoUgpcgUo2qkSUiWP3dWi2efEi66P",
				"8R9WodrfZVIeZcLTMEmEtwD8oltOdGoFhPcUyi79");
	}
}
