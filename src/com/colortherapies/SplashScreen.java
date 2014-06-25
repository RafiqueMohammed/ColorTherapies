package com.colortherapies;

import com.colortherapies.helper.AccountPreference;
import com.colortherapies.helper.Methods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class SplashScreen extends Activity {
	TextView txt_init;
	// SharedPreferences account;
	AccountPreference acc_pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		txt_init = (TextView) findViewById(R.id.txt_initialization);

		// account =
		// getSharedPreferences("AccountPreference",Context.MODE_PRIVATE);

		acc_pref = new AccountPreference(this);

		Log.d("ARR", "fresh :" + acc_pref.getBool("isFreshInstall"));
		Log.d("ARR", "USername :" + acc_pref.get("username"));

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (acc_pref.getBool("isFreshInstall")) {

					txt_init.setText("Setting up environment..");
				}

			}

		}, 2000);

		txt_init.setText("Checking out internet connection..");
		checkConnection();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (checkPreference()) {
					gotoDashboard("d");
				} else {
					gotoDashboard("l");
				}

			}

		}, 2000);
	}

	private void checkConnection() {
		// TODO Auto-generated method stub
		if (!Methods.isInternetConnected(this)) {

			Methods.showAlert(this, "Internet Connection Unavailable",
					"Try to connect your wifi or mobile internet connection",
					false);
		}
	}

	private boolean checkPreference() {

		if (acc_pref.getBool("isFreshInstall")) {

			return false; // Its a fresh install
		} else if (acc_pref.get("Authorization") == null
				|| acc_pref.get("username") == null
				|| acc_pref.get("expiration") == null
				|| acc_pref.get("activation") == null) {
			return false; // Need to login again
		} else {
			return true; // Everything is alright
		}

	}

	void gotoDashboard(String type) {
		Intent i;
		if (type == "d") {
			i = new Intent(SplashScreen.this, Dashboard.class);
		} else if (type == "l") {
			i = new Intent(SplashScreen.this, Login.class);
		} else {
			i = new Intent(SplashScreen.this, MainActivity.class);
		}

		startActivity(i);
		finish();
	}
}
