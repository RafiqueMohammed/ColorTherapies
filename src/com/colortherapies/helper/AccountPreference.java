package com.colortherapies.helper;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AccountPreference {

	Context c;
	SharedPreferences pref;

	public AccountPreference(Context context) {
		// TODO Auto-generated constructor stub
		this.c = context;
		this.pref = context.getSharedPreferences("AccountPreference",
				Context.MODE_PRIVATE);

	}

	public void addAccountPreference(HashMap<String,String> data){
		
		
		SharedPreferences.Editor edit = this.pref.edit();
		edit.putBoolean("isLogin", true);
		edit.putString("username", data.get("username"));
		edit.putString("Authorization", data.get("Authorization"));
		edit.putString("expiration", data.get("expiration"));
		edit.putString("activation", data.get("activation"));
		boolean premium=(data.get("isPremium")=="true")?true:false;
		edit.putBoolean("isPremium", premium);
		edit.commit();
	}
	
	public void addLoginPreference(HashMap<String,String> data){
		
		
		SharedPreferences.Editor edit = this.pref.edit();
		edit.putBoolean("isLogin", true);
		edit.putString("username", data.get("username"));
		edit.putString("Authorization", data.get("Authorization"));
		edit.putString("expiration", data.get("expiration"));
		edit.putString("activation", data.get("activation"));
		boolean premium=(data.get("isPremium")=="true")?true:false;
		edit.putBoolean("isPremium", premium);
		edit.commit();
	}
	
	public void printAccountPreference(){
		String res="isLogin : "+this.pref.getBoolean("isLogin", false)+ " Username : "+this.pref.getString("username","null");
		res+="\n Authorization : "+this.pref.getString("Authorization", "null");
		res+="\n expiration : "+this.pref.getString("expiration", "null")+ " activation : "+this.pref.getString("activation","null");
		res+="\n premium : "+this.pref.getBoolean("isPremium", false);
		Log.d("ARR",res);
		L.t(c,res);
	}
	
	public boolean set(String key, String value) {
		SharedPreferences.Editor edit = this.pref.edit();
		if (!key.equals("") && !value.equals("")) {
			edit.putString(key, value);
			edit.commit();
			return true;
		} else {
			return false;
		}

	}

	public boolean setBool(String key, Boolean value) {
		SharedPreferences.Editor edit = this.pref.edit();
		if (!key.equals("") && !value.equals("")) {
			edit.putBoolean(key, value);
			edit.commit();
			return true;
		} else {
			return false;
		}

	}

	public String get(String key) {

		return this.pref.getString(key, null);

	}

	public boolean getBool(String key) {

		return this.pref.getBoolean(key, false);

	}

	public void setInitialPref() {
		SharedPreferences.Editor edit = this.pref.edit();
		edit.putBoolean("isLogin", false);
		edit.putBoolean("isFreshInstall", false);
		edit.putBoolean("analytics_sent", false);
		edit.putString("username", null);
		edit.putString("Authorization", null);
		edit.putString("expiration", null);
		edit.putString("activation", null);
		edit.putBoolean("isPremium", false);
		edit.commit();

	}
	
	public boolean isLoggedIn(){
		
		return this.pref.getBoolean("isLogin",false);
		
	}

}
