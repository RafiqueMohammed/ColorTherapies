package com.colortherapies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.colortherapies.helper.AccountPreference;
import com.colortherapies.helper.Default;
import com.colortherapies.helper.L;
import com.colortherapies.helper.MCrypt;
import com.colortherapies.helper.Methods;

import android.app.Activity;
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

	Button login, sign_up, forgot_password;
	EditText email, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		login = (Button) findViewById(R.id.btn_login);
		sign_up = (Button) findViewById(R.id.btn_sign_up);
		forgot_password = (Button) findViewById(R.id.btn_forgot_password);

		email = (EditText) findViewById(R.id.txt_login_email);
		password = (EditText) findViewById(R.id.txt_login_password);

		login.setOnClickListener(this);
		sign_up.setOnClickListener(this);

		AccountPreference mypref = new AccountPreference(this);
		if (mypref.isLoggedIn()) {
			startActivity(new Intent(this, Dashboard.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_SINGLE_TOP));

			finish();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		AccountPreference mypref = new AccountPreference(this);
		if (mypref.isLoggedIn()) {
			startActivity(new Intent(this, Dashboard.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_SINGLE_TOP));

			finish();
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// HANDLES BUTTON CLICKS OF LOGIN FORM

		switch (v.getId()) {

		case R.id.btn_login:
			checkLogin(email.getText().toString(), password.getText()
					.toString());
			break;
		case R.id.btn_forgot_password:
			;
			break;
		case R.id.btn_sign_up:
			startActivity(new Intent(this, Registration.class));
			break;

		}

	}

	private void checkLogin(String email, String password) {
		if (email.isEmpty() || password.isEmpty()) {
			Methods.showAlert(this, "Required Field Missing",
					"Please type both email id and password to login", false);
		} else {
			if (Methods.isInternetConnected(Login.this)) {
				new sendLoginDetails().execute(new String[] { "Rafique" });
			} else {
				Methods.showAlert(
						this,
						"Internet Connection Unavailable",
						"Try to connect your wifi or mobile internet connection",
						false);
			}

		}
	}

	private class sendLoginDetails extends AsyncTask<String, Void, JSONObject> {

		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			pd = new ProgressDialog(Login.this);
			pd.setTitle("Please wait..");
			pd.setMessage("Verifying your login..");
			pd.setCancelable(false);
			pd.show();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub

			BufferedReader br;
			String captcha_token;
			JSONObject json_output = null;

			String output;
			try {
				HttpClient http;
				HttpPost post;

				HttpParams p = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(p, 20000);
				HttpConnectionParams.setSoTimeout(p, 20000);

				http = new DefaultHttpClient(p);
				post = new HttpPost(Default.LOGIN_URL);

				List<NameValuePair> post_data = new ArrayList<NameValuePair>();
				post_data.add(new BasicNameValuePair("username", email
						.getText().toString()));
				post_data.add(new BasicNameValuePair("password", password
						.getText().toString()));

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						post_data);
				post.setEntity(entity);

				captcha_token = new MCrypt().getEncrypt(email.getText()
						.toString() + password.getText().toString());
				post.addHeader("Authorization", captcha_token);

				HttpResponse response = http.execute(post);

				int status = response.getStatusLine().getStatusCode();

				Log.d("ARR", "Status :" + status);

				if (status == 200) {
					br = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
					String data = "", tmp = "";
					while ((tmp = br.readLine()) != null) {
						data += tmp;
					}

					json_output = new JSONObject(data.toString());
					if (json_output.getString("status").equals("ok")) {
						json_output.put("Authorization", response
								.getFirstHeader("Authorization").getValue());
					}

				} else if (status == 400) {
					output = "{\"status\":\"no\",\"result\":\"Invalid Type Request\"}";
					json_output = new JSONObject(output);
				} else if (status == 404) {
					output = "{\"status\":\"no\",\"result\":\"No such request found\"}";
					json_output = new JSONObject(output);
				} else if (status == 500) {
					output = "{\"status\":\"no\",\"result\":\"Server is temporarily down. Please try again later\"}";
					json_output = new JSONObject(output);
				} else if (status == 503) {
					output = "{\"status\":\"no\",\"result\":\"Server is temporarily down. Please try again later\"}";
					json_output = new JSONObject(output);
				}

			} catch (UnsupportedEncodingException e) {
				L.l("UnsupportedEncodingException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				L.l("JSONException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				L.l("IllegalStateException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				L.l("IOException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				L.l("Encryption Exception");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return json_output;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if (result != null) {
				try {
					if (result.getString("status").equals("ok")) {
						HashMap<String, String> hash = new HashMap<String, String>();
						hash.put("Authorization",
								result.getString("Authorization"));
						hash.put("activation", result.getString("activation"));
						hash.put("expiration", result.getString("expiration"));
						hash.put("username", result.getString("email"));
						hash.put("isPremium", result.getString("isPremium"));

						AccountPreference mypref = new AccountPreference(
								Login.this);
						mypref.addAccountPreference(hash);

						Methods.AlertWithRedirect(
								Login.this,
								"Success",
								"Your account will be expire on "
										+ result.getString("expiration"),
								new Dashboard(), true);

					} else if (result.getString("status").equals("no")) {
						Methods.showAlert(Login.this, "Failed",
								result.getString("result"), false);
					} else {
						L.t(Login.this,
								"Error #A5250. No Response From The Server");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					L.t(Login.this,
							"Error #A1250. Unable to parse response from the server.");
				}
			} else {
				L.t(Login.this, "Error #A1150. No Response From The Server");
			}
		}

	}
}
