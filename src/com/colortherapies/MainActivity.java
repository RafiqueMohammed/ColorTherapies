package com.colortherapies;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends Activity{
	Calendar cal=Calendar.getInstance(Locale.getDefault());
	int day=cal.get(Calendar.DAY_OF_MONTH);
	int month=cal.get(Calendar.MONTH);
	int year=cal.get(Calendar.YEAR);
	TextView date_txt;
	Button open;
DatePickerDialog.OnDateSetListener listener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("ARR","Day "+day+" Month "+month+" YR:"+year);
		date_txt=(TextView) findViewById(R.id.txt_date);
		open=(Button) findViewById(R.id.btn_openDate);
		
		open.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dateDialog().show();
			}
		});
		
		date_txt.append("\n Old Date - "+day+" / "+month+" / "+year);
		listener=new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int month,
					int day) {
				// TODO Auto-generated method stub
				Log.d("ARR","New Day "+day+" Month "+month+" YR:"+year);
				date_txt.append("\n New Date - "+day+" / "+month+" / "+year);
			}
		};
		
		dateDialog().show();
	}
	
	public Dialog dateDialog(){

		return new DatePickerDialog(this,listener, year, month, day);
		
	}
}
