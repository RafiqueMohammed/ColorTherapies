package com.colortherapies;

import java.util.List;

import org.json.JSONException;

import com.colortherapies.helper.AccountPreference;
import com.colortherapies.helper.ColorView;
import com.colortherapies.helper.Methods;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class Dashboard extends Activity {

	AccountPreference acc_prefs;
	GridView HGrid, RGrid, FGrid;
	List<View> HList, RList, FList;
	ColorView colorView;
	String decode = "{\"health\":[\"#FFCCBB\",\"#99bbcc\",\"#2211FF\"],\"relationship\":[\"#1FFCBB\",\"#F9CCBB\",\"#992BCC\"],\"finance\":[\"#FFCC00\",\"#559922\",\"#929b9C\"]}";
	TextView header;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		HGrid = (GridView) findViewById(R.id.health_grid);
		RGrid = (GridView) findViewById(R.id.relationship_grid);
		FGrid = (GridView) findViewById(R.id.finance_grid);


		header= (TextView) findViewById(R.id.txt_dashboard_header);
header.setTypeface(Typeface.createFromAsset(getAssets(),"font/Helvetica Neue UltraLight.ttf"));
		try {
			colorView = new ColorView(this, decode);
			
			HList = colorView.getViewList(ColorView.TYPE_HEALTH);
			 
			FList = colorView.getViewList(ColorView.TYPE_FINANCE);
			 
			RList = colorView.getViewList(ColorView.TYPE_RELATIONSHIP);
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		HGrid.setAdapter(new Methods.DashboardColorAdapter(this,HList));
		
		FGrid.setAdapter(new Methods.DashboardColorAdapter(this,FList));
		
		RGrid.setAdapter(new Methods.DashboardColorAdapter(this,RList));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.mainactivity_menu) {
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

}
