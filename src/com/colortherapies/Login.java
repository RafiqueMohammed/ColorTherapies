package com.colortherapies;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpConnection;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {

	Button login,sign_up,forgot_password;
	EditText email,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		login=(Button) findViewById(R.id.btn_login);
		sign_up=(Button) findViewById(R.id.btn_sign_up);
		forgot_password=(Button) findViewById(R.id.btn_forgot_password);
		
		email=(EditText) findViewById(R.id.txt_login_email);
		password=(EditText) findViewById(R.id.txt_login_password);
		
		login.setOnClickListener(this);
		sign_up.setOnClickListener(this);
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		// HANDLES BUTTON CLICKS OF LOGIN FORM
		
		switch(v.getId()){
		
		case R.id.btn_login:
			checkLogin(email.getText().toString(),password.getText().toString());
		break;
		case R.id.btn_forgot_password: ;
		break;
		case R.id.btn_sign_up: 
			startActivity(new Intent(this,Registration.class));
		break;
		
		}
		
	}
	
	private void checkLogin(String email,String password){
		if(email.isEmpty()||password.isEmpty()){
			Methods.showAlertDialog(this,"Required Field Missing", "Please type both email id and password to login",false);
		}else{
			if(Methods.isInternetConnected(Login.this)){
				new sendLoginDetails().execute(new String[]{"Rafique"});
			}else{
				Methods.showAlertDialog(this, "Internet Connection Unavailable",
						"Try to connect your wifi or mobile internet connection",
						false);
			}
			
		}
	}
	
	private class sendLoginDetails extends AsyncTask<String,Void,JSONObject>{

		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			pd=new ProgressDialog(Login.this);
			pd.setTitle("Please wait..");
			pd.setMessage("Verifying your login..");
			pd.setCancelable(false);
			pd.show();
		}
		
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			URL login_url;
			HttpURLConnection http;
			BufferedInputStream getStream;
			try {
				login_url = new URL("http://192.168.0.200/rest/v1/type/login");
				http=(HttpURLConnection) login_url.openConnection();
				http.setConnectTimeout(30000);
				http.setReadTimeout(30000);
				http.setInstanceFollowRedirects(true);
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
http.setDoOutput(true);
http.setDoInput(true);
/*
List<NameValuePair> post_data = new ArrayList<NameValuePair>();
post_data.add(new BasicNameValuePair("email", email.getText().toString()));
post_data.add(new BasicNameValuePair("password", password.getText().toString()));*/

String urlParameters ="email=" + URLEncoder.encode(email.getText().toString(), "UTF-8") +"&password=" + URLEncoder.encode(password.getText().toString(), "UTF-8");
DataOutputStream wr = new DataOutputStream (
        http.getOutputStream ());
wr.writeBytes(urlParameters);
wr.flush ();
wr.close ();

				if(!login_url.getHost().equals(http.getURL().getHost())){
					Log.d("ARR","Redirection occured");
					return null;
				}else{
					getStream=new BufferedInputStream(http.getInputStream());
					BufferedReader reader=new BufferedReader(new InputStreamReader(getStream));
					String data,output;
					output="";
					while((data=reader.readLine())!=null){
						output+=data;
					}
					
					Log.d("ARR","Output :"+output);
				}
				
				int status=http.getResponseCode();
				Log.d("ARR","Status Code :"+status);
				http.disconnect();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
			}
		
			
			
			
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
		}
		
		
		
	}
}
