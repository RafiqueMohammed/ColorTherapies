package com.colortherapies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.colortherapies.helper.AccountPreference;
import com.colortherapies.helper.Default;
import com.colortherapies.helper.L;
import com.colortherapies.helper.Methods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreen extends Activity {
	TextView txt_init;
	// SharedPreferences account;
	AccountPreference acc_pref;
String Goto="l";
int delay=1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		acc_pref = new AccountPreference(this);
		txt_init = (TextView) findViewById(R.id.txt_initialization);
        txt_init.setText("Checking out internet connection..");
        checkConnection();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        txt_init.setText("Checking Authentication...");

        new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {

						if (acc_pref.getBool("isFreshInstall",true)) {
							txt_init.setText("Setting up environment for the first use...");
							Thread.sleep(delay);
							acc_pref.setInitialPref();
							gotoDashboard("login");
							
						}else{


							checkAuthentication();
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 500);
	}

	private void checkAuthentication() {
		// TODO Auto-generated method stub
		try {
			JSONObject data=new Methods.CheckAuthentication().execute(Default.CHECK_AUTH).get();

			if(data.has("status")){
				if(data.getString("status").equals("ok")){
					
					L.l("Status : OK");
					
					acc_pref.set("activation", data.getString("activation"));
					acc_pref.set("expiration", data.getString("expiration"));
					
					L.l("Activation Before : "+acc_pref.get("activation"));
					L.l("expiration Before : "+data.getString("expiration"));
					
					if(!acc_pref.getBool("isPremium",false)==data.getBoolean("isPremium")){
						
						L.l("isPremium Before : "+acc_pref.getBool("isPremium",false));
						L.l("isPremium After : "+data.getBoolean("isPremium"));
						
						acc_pref.setBool("isPremium",data.getBoolean("isPremium"));
						gotoDashboard("premium_dialog");
						
					}else{
						gotoDashboard("dashboard");
					}
					
                    
				}else if(data.getString("status").equals("no")){
					L.l("Status : NO");
					L.l("Reason : "+data.getString("result"));
					
					L.t(SplashScreen.this,data.getString("result"));
					gotoDashboard("login");
				}
			
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			L.l("InterruptedException "+e.toString());
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			L.l("ExecutionException "+e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			L.l("JSONException "+e.toString());
		}
	}
	
	private void checkConnection() {
		// TODO Auto-generated method stub
		if (!Methods.isInternetConnected(this)) {

			Methods.showAlert(this, "Internet Connection Unavailable",
					"Try to connect your wifi or mobile internet connection",
					false);
		}
	}



	void gotoDashboard(String type) {
		Intent i;
		if (type == "dashboard") {
			i = new Intent(SplashScreen.this, Dashboard.class);
		} else if (type == "login") {
			i = new Intent(SplashScreen.this, Login.class);
		}else if (type == "premium_dialog") {
			i = new Intent(SplashScreen.this, WelcomePremium.class);
			i.putExtra("date",acc_pref.get("activation"));
		} else {
			i = new Intent(SplashScreen.this, MainActivity.class);
		}

		startActivity(i);
		finish();
	}
	
	

}
