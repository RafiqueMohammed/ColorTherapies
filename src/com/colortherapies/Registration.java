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
import org.apache.http.client.ClientProtocolException;
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

import com.colortherapies.helper.Default;
import com.colortherapies.helper.L;
import com.colortherapies.helper.MCrypt;
import com.colortherapies.helper.Methods;
import com.colortherapies.helper.AccountPreference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Registration extends Activity implements OnClickListener {
	TextView txt_header;
	Button login, add_acc;
	EditText fname, email, pass, re_pass, phone, city, country;
	RadioButton male, female;
	RadioGroup gender;
	CheckBox terms;

	DatePicker dob;
	String captcha_ref_key, captcha_token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		init();
		captcha_ref_key = "";
		captcha_token = "";

	}

	void init() {
		Typeface header = Typeface.createFromAsset(getAssets(),
				"font/HelveticaNeue-UltraLigCond.otf");
		txt_header = (TextView) findViewById(R.id.txt_create_account);
		txt_header.setTypeface(header);
		login = (Button) findViewById(R.id.register_already_login);
		add_acc = (Button) findViewById(R.id.register_create_account);

		fname = (EditText) findViewById(R.id.register_fullname);
		email = (EditText) findViewById(R.id.register_email);
		pass = (EditText) findViewById(R.id.register_password);
		re_pass = (EditText) findViewById(R.id.register_retype_password);
		phone = (EditText) findViewById(R.id.register_phone);
		dob = (DatePicker) findViewById(R.id.register_dob);
		city = (EditText) findViewById(R.id.register_city);
		country = (EditText) findViewById(R.id.register_country);

		gender = (RadioGroup) findViewById(R.id.register_gender);
		male = (RadioButton) findViewById(R.id.register_male);
		female = (RadioButton) findViewById(R.id.register_female);

		terms = (CheckBox) findViewById(R.id.register_agree_terms);

		login.setOnClickListener(this);
		add_acc.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_already_login:
			startActivity(new Intent(this, Login.class));
			;
			break;
		case R.id.register_create_account:
			sendRegistration();
			;
			break;
		}
	}

	void sendRegistration() {
		String txt_fname, txt_email, txt_pass, txt_re_pass, txt_phone, txt_dob, txt_gender, txt_city, txt_country;

		int selectedGenderID = gender.getCheckedRadioButtonId();
		if (selectedGenderID == R.id.register_male) {
			txt_gender = male.getText().toString();
		} else if (selectedGenderID == R.id.register_female) {
			txt_gender = female.getText().toString();
		} else {
			txt_gender = "";
		}


		int   day  = dob.getDayOfMonth();
        int   month= dob.getMonth() + 1;
        int   year = dob.getYear();
		
		txt_fname = fname.getText().toString();
		txt_email = email.getText().toString();
		txt_pass = pass.getText().toString();
		txt_re_pass = re_pass.getText().toString();
		txt_phone = phone.getText().toString();
		txt_dob = year+"-"+month+"-"+day;
		L.t(Registration.this,"Your datepicker "+txt_dob);
		txt_city = city.getText().toString();
		txt_country = country.getText().toString();

		if (txt_fname.equals("")) {
			Methods.showAlert(this, "Required", "Enter Your Full Name", false);
			return;
		} else if (txt_email.equals("")) {
			Methods.showAlert(this, "Required", "Enter Your Valid Email ID",
					false);
			return;
		} else if (txt_pass.equals("")) {
			Methods.showAlert(this, "Required", "Enter Your Password", false);
			return;
		} else if (txt_re_pass.equals("")) {
			Methods.showAlert(this, "Required", "Retype Your Password", false);
			return;
		} else if (txt_gender.equals("")) {
			Methods.showAlert(this, "Required", "Please Select Your Gender",
					false);
			return;
		} else if (txt_dob.equals("--")) {
			Methods.showAlert(this, "Required",
					"Please Select Your Date Of Birth", false);
			return;
		} else if (txt_phone.equals("")) {
			Methods.showAlert(this, "Required", "Enter Valid Mobile Number",
					false);
			return;
		} else if (txt_city.equals("")) {
			Methods.showAlert(this, "Required", "Enter Your City Name", false);
			return;
		} else if (txt_country.equals("")) {
			Methods.showAlert(this, "Required", "Enter Your Country Name",
					false);
			return;
		} else if (!txt_pass.equals(txt_re_pass)) {
			Methods.showAlert(
					this,
					"Password Not Match",
					"Your given password doesn't match. Please retype your password.",
					false);
			return;
		} else if (!terms.isChecked()) {
			Methods.showAlert(this, "Terms and Conditions",
					"You must agree to our terms and conditions", false);
			return;
		}

		captcha_ref_key = txt_fname + txt_email;

		try {

			captcha_token = new MCrypt().getEncrypt(captcha_ref_key);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Methods.t(Registration.this, "Unfortunately Error #2500 Occured "
					+ e.toString()); // Error while creating MCrypt ALgorithm
			return;
		}

		String[] info = { txt_fname, txt_email, txt_pass, txt_phone, txt_dob,
				txt_gender, txt_city, txt_country, captcha_token };

		new sendToCloud().execute(info);

	}

	private class sendToCloud extends AsyncTask<String, Void, JSONObject> {

		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd = new ProgressDialog(Registration.this);
			pd.setTitle("Please Wait..");
			pd.setMessage("Registering your new account..");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			String txt_fname, txt_email, txt_pass, txt_phone, txt_dob, txt_gender, city, country, captcha_token;
			txt_fname = params[0];
			txt_email = params[1];
			txt_pass = params[2];
			txt_phone = params[3];
			txt_dob = params[4];
			txt_gender = params[5];
			city = params[6];
			country = params[7];
			captcha_token = params[8];

			HttpClient http;
			HttpPost post;

			HttpParams p = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(p, 20000);
			HttpConnectionParams.setSoTimeout(p, 20000);

			http = new DefaultHttpClient(p);
			post = new HttpPost(Default.REGISTRATION_URL);

			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("fullname", txt_fname));
			param.add(new BasicNameValuePair("email", txt_email));
			param.add(new BasicNameValuePair("password", txt_pass));
			param.add(new BasicNameValuePair("phone", txt_phone));
			param.add(new BasicNameValuePair("dob", txt_dob));
			param.add(new BasicNameValuePair("gender", txt_gender));
			param.add(new BasicNameValuePair("city", city));
			param.add(new BasicNameValuePair("country", country));
			String output = "";
			JSONObject json_output = null;
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param);
				post.setEntity(entity);

				post.addHeader("Authorization", captcha_token);

				HttpResponse response = http.execute(post);
				int status = response.getStatusLine().getStatusCode();
				Log.d("ARR", "Status :" + status);
				BufferedReader br;

				if (status == 201) {
					br = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
					String data = "", tmp = "";
					while ((tmp = br.readLine()) != null) {
						data += tmp;
					}

					json_output = new JSONObject(data.toString());
					json_output
							.put("Authorization",
									response.getFirstHeader("Authorization")
											.getValue());

				} else if (status == 200 || status == 503) {
					br = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
					String data = "", tmp = "";
					while ((tmp = br.readLine()) != null) {
						data += tmp;
					}
					output = data.toString();

					json_output = new JSONObject(output);
				} else if (status == 400) {
					output = "{\"status\":\"no\",\"result\":\"Invalid Type Request\"}";
					json_output = new JSONObject(output);
				} else if (status == 404) {
					output = "{\"status\":\"no\",\"result\":\"No such request found\"}";
					json_output = new JSONObject(output);
				} else if (status == 500) {
					output = "{\"status\":\"no\",\"result\":\"Server is temporarily down. Please try again later\"}";
					json_output = new JSONObject(output);
				} 

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				output = "{\"status\":\"no\",\"result\":\"UnsupportedEncodingException Occured. Please Report Us\"}";
				try {
					json_output = new JSONObject(output);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				output = "{\"status\":\"no\",\"result\":\"ClientProtocolException Occured. Please Report Us\"}";
				try {
					json_output = new JSONObject(output);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				output = "{\"status\":\"no\",\"result\":\"IOException Occured. Please Report Us\"}";
				try {
					json_output = new JSONObject(output);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return json_output;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			try {

				if (result.getString("status").equals("no")) {
					L.t(Registration.this, result.getString("result"));

				} else if (result.getString("status").equals("ok")) {

					HashMap<String, String> hash = new HashMap<>();
					hash.put("Authorization", result.getString("Authorization"));
					hash.put("activation", result.getString("activation"));
					hash.put("expiration", result.getString("expiration"));
					hash.put("username", result.getString("email"));
					hash.put("isPremium", result.getString("isPremium"));

					AccountPreference mypref = new AccountPreference(
							Registration.this);
					mypref.addAccountPreference(hash);
					mypref.printAccountPreference();
					Methods.AlertWithRedirect(Registration.this, "Thank You!",
							"You account has been successfully registered",
							new Dashboard(), true);

				} else {
					Log.d("ARR", "Else my result will be " + result.toString());
					Log.d("ARR",
							"Seperating status " + result.getString("status"));
					Log.d("ARR",
							"Seperating email " + result.getString("email"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				L.t(Registration.this,
						"Unable to parse output. Error #3505 occured. Please Report Us");

			}

			super.onPostExecute(result);
		}

	}
}
