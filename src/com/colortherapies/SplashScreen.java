package com.colortherapies;

import com.colortherapies.helper.Methods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class SplashScreen extends Activity {
	TextView txt_init;
	SharedPreferences account;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		txt_init = (TextView) findViewById(R.id.txt_initialization);

		account = getSharedPreferences("AccountPreference",Context.MODE_PRIVATE);
		
		Log.d("ARR","fresh :"+account.getBoolean("isFreshInstall", true)+ " Name : "+account.contains("isFreshInstall"));
		Log.d("ARR","USername :"+account.getString("username","-"));
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if (account.getBoolean("isFreshInstall", true)) {
					
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
				if(checkPreference()){
					gotoDashboard("d");
				}else{
					gotoDashboard("l");
				}
				
			}

		}, 2000);
	}

	private void checkConnection() {
		// TODO Auto-generated method stub
		if (!Methods.isInternetConnected(this)) {
		
			Methods.showAlertDialog(this, "Internet Connection Unavailable",
					"Try to connect your wifi or mobile internet connection",
					false);
		}
	}

	private boolean checkPreference() {
		SharedPreferences.Editor edit = account.edit();
		if (account.getBoolean("isFreshInstall", true)) {
			
			edit.putBoolean("isLogin", false);
			edit.putBoolean("isFreshInstall", false);
			edit.putBoolean("analytics_sent", false);
			edit.putString("username", null);
			edit.putString("password", null);
			edit.putString("token", null);
			edit.putString("expiration", null);
			edit.putString("activation", null);
			edit.putBoolean("isPremium", false);
			edit.commit();	
			return false; //Its a fresh install
		}
		else if(account.getString("token",null)==null||account.getString("username",null)==null||account.getString("password",null)==null||account.getString("expiration",null)==null||account.getString("activation",null)==null){
			return false; //Need to login again
}else{
	return true; //Everything is alright
}


	}

	void gotoDashboard(String type) {
		Intent i;
		if(type=="d"){
			i = new Intent(SplashScreen.this, Dashboard.class);
		}else if(type=="l"){
			i = new Intent(SplashScreen.this, Login.class);
		}else{
			i = new Intent(SplashScreen.this, MainActivity.class);
		}
		 
		startActivity(i);
		finish();
	}
}
