package com.colortherapies;

import com.style.CustomFont;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WelcomePremium extends Activity {
	TextView welcome,welcome1,date_txt;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_welcome_premium);
	welcome=(TextView) findViewById(R.id.welcome_premium_thanks_txt);
	welcome1=(TextView) findViewById(R.id.welcome_premium_thanks_1_txt);
	date_txt=(TextView) findViewById(R.id.welcome_premium_expire_txt);
	
	welcome.setTypeface(new CustomFont(this).getTypeface());
	welcome1.setTypeface(new CustomFont(this).getTypeface());
	Intent i=this.getIntent();
	String date="00-00-0000";
	date=i.getStringExtra(date);
	Log.d("ARR","Date :"+date);
	if(date!=null){
		date_txt.setText(date);
	}else{
		date_txt.setText("00-00-0000");
	}
	
}
}
