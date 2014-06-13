package com.colortherapies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Registration extends Activity implements OnClickListener{
	TextView txt_header;
	Button login,add_acc;
	EditText fname,email,pass,re_pass,phone,dob;
	RadioButton male,female;
	RadioGroup gender;
	CheckBox terms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
init();
	}
	
	
	
	void init(){
		Typeface header = Typeface.createFromAsset(getAssets(),"font/HelveticaNeue-UltraLigCond.otf");
		txt_header = (TextView) findViewById(R.id.txt_create_account);
		txt_header.setTypeface(header);
		login=(Button) findViewById(R.id.register_already_login);
		add_acc=(Button) findViewById(R.id.register_create_account);
		
		fname=(EditText) findViewById(R.id.register_fullname);
		email=(EditText) findViewById(R.id.register_email);
		pass=(EditText) findViewById(R.id.register_password);
		re_pass=(EditText) findViewById(R.id.register_retype_password);
		phone=(EditText) findViewById(R.id.register_phone);
		dob=(EditText) findViewById(R.id.register_dob);
		
		gender=(RadioGroup) findViewById(R.id.register_gender);
		male=(RadioButton) findViewById(R.id.register_male);
		female=(RadioButton) findViewById(R.id.register_female);
		
		terms=(CheckBox) findViewById(R.id.register_agree_terms);
		
		login.setOnClickListener(this);
		add_acc.setOnClickListener(this);
		
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.register_already_login:
			startActivity(new Intent(this,Login.class));
			; break;
		case R.id.register_create_account:
			sendRegistration();
			; break;
		}
	}
	
	void sendRegistration(){
		String txt_fname,txt_email,txt_pass,txt_re_pass,txt_phone,txt_dob,txt_gender;
		
		int selectedGenderID=gender.getCheckedRadioButtonId();
		if(selectedGenderID==R.id.register_male){
			txt_gender=male.getText().toString();
		}else if(selectedGenderID==R.id.register_female){
			txt_gender=female.getText().toString();
		}else{
			txt_gender="";
		}
		
		/*Log.d("ARR","Fname : "+fname.getText()+"email : "+email.getText()+"pass : "+pass.getText());
		Log.d("ARR","re_pass : "+re_pass.getText()+"phone : "+phone.getText()+"dob : "+dob.getText());*/

		txt_fname=fname.getText().toString();
		txt_email=email.getText().toString();
		txt_pass=pass.getText().toString();
		txt_re_pass=re_pass.getText().toString();
		txt_phone=phone.getText().toString();
		txt_dob=dob.getText().toString();
		

		
		 if(txt_fname.equals("")){
			Methods.showAlertDialog(this,"Required","Enter Your Full Name", false);
			return;
		}else if(txt_email.equals("")){
			Methods.showAlertDialog(this,"Required","Enter Your Valid Email ID", false);
			return;
		}else if(txt_pass.equals("")){
			Methods.showAlertDialog(this,"Required","Enter Your Password", false);
			return;
		}else if(txt_re_pass.equals("")){
			Methods.showAlertDialog(this,"Required","Retype Your Password", false);
			return;
		}else if(txt_gender.equals("")){
			Methods.showAlertDialog(this,"Required","Please Select Your Gender", false);
			return;
		}else if(txt_dob.equals("")){
			Methods.showAlertDialog(this,"Required","Please Select Your Date Of Birth", false);
			return;
		}else if(txt_phone.equals("")){
			Methods.showAlertDialog(this,"Required","Enter Valid Mobile Number", false);
			return;
		}else if(!txt_pass.equals(txt_re_pass)){
			Methods.showAlertDialog(this,"Password Not Match","Your given password doesn't match. Please retype your password.", false);
			return;
		}else if(!terms.isChecked()){
			Methods.showAlertDialog(this,"Terms and Conditions","You must agree to our terms and conditions", false);
			return;
		}
		 
		 Methods.showAlertDialog(this,"Success","All Required Data are valid",true);
		 
		 
		 String[] info={txt_fname,txt_email,txt_pass,txt_phone,txt_dob,txt_gender};
		 new sendToCloud().execute(info);
	}
	
	private class sendToCloud extends AsyncTask<String, Void, String>{

		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd=new ProgressDialog(Registration.this);
			pd.setTitle("Please Wait..");
			pd.setMessage("Registering your new account..");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String txt_fname,txt_email,txt_pass,txt_phone,txt_dob,txt_gender;
			txt_fname=params[0];
			txt_email=params[1];
			txt_pass=params[2];
			txt_phone=params[3];
			txt_dob=params[4];
			txt_gender=params[5];
			
			HttpClient http;
			HttpPost post;
			
			
			HttpParams p=new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(p,20000);
			HttpConnectionParams.setSoTimeout(p,20000);
			
			http=new DefaultHttpClient(p);
			post=new HttpPost("http://192.168.0.200/rest/v1/register/new");
			
			List<NameValuePair> param=new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("fullname",txt_fname));
			param.add(new BasicNameValuePair("email",txt_email));
			param.add(new BasicNameValuePair("password",txt_pass));
			param.add(new BasicNameValuePair("phone",txt_phone));
			param.add(new BasicNameValuePair("dob",txt_dob));
			param.add(new BasicNameValuePair("gender",txt_gender));
			
			try {
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(param);
				post.setEntity(entity);
				post.setHeader("Token","My HASH TOKEN OF AUTHENTICATION");
				HttpResponse response=http.execute(post);
				int status=response.getStatusLine().getStatusCode();
				Log.d("ARR","Status :"+status);
				if(status==200){
					BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String data="",tmp;
					while((tmp=br.readLine())!=null){
						data+=tmp;
					}
					
					Log.d("ARR","Response :"+data.toString());
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
		pd.dismiss();
			super.onPostExecute(result);
		}
		
	}
}
