package com.colortherapies;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class Dashboard extends Activity {

	ListView menuListView;
	GridView career_grid,health_grid,finance_grid,relationship_grid;
	ArrayList<View> career_array_view,health_array_view,finance_array_view,relation_array_view;
	BaseAdapter adpt;
	SharedPreferences pref,account;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		account = getSharedPreferences("AccountPreference",Context.MODE_PRIVATE);
		Log.d("ARR","Dash fresh :"+account.getBoolean("isFreshInstall", true)+ " Name : "+account.contains("isFreshInstall"));
		Log.d("ARR","Dash USername :"+account.getString("username","-"));
		
		
		Typeface helvetica=Typeface.createFromAsset(getAssets(), "font/Helvetica Neue UltraLight.ttf");
		
		TextView header_txt=(TextView) findViewById(R.id.txt_dashboard_header);
		header_txt.setTypeface(helvetica);
		
		//menuListView=(ListView) findViewById(R.id.list_dashboard_menu);
		
		career_grid=(GridView) findViewById(R.id.career_grid);
		health_grid=(GridView) findViewById(R.id.health_grid);
		finance_grid=(GridView) findViewById(R.id.finance_grid);
		relationship_grid=(GridView) findViewById(R.id.relationship_grid);
		
		
		career_array_view=new ArrayList<View>();
		health_array_view=new ArrayList<View>();
		finance_array_view=new ArrayList<View>();
		relation_array_view=new ArrayList<View>();
		
	View cv=new View(this);
	cv.setBackgroundColor(Color.CYAN);
	cv.setMinimumWidth(50);
	cv.setMinimumHeight(50);
	cv.setPadding(3,3,3,3);
	career_array_view.add(cv);
	
	View cv1=new View(this);
	cv1.setBackgroundColor(Color.CYAN);
	cv1.setMinimumWidth(50);
	cv1.setMinimumHeight(50);
	cv1.setPadding(3,3,3,3);
	cv1.setBackgroundColor(Color.DKGRAY);
	health_array_view.add(cv1);
	
	View cv2=new View(this);
	cv2.setBackgroundColor(Color.CYAN);
	cv2.setMinimumWidth(50);
	cv2.setMinimumHeight(50);
	cv2.setPadding(3,3,3,3);
	cv2.setBackgroundColor(Color.MAGENTA);
	
	View cv21=new View(this);
	cv21.setBackgroundColor(Color.CYAN);
	cv21.setMinimumWidth(50);
	cv21.setMinimumHeight(50);
	cv21.setPadding(3,3,3,3);
	cv21.setBackgroundColor(Color.RED);
	finance_array_view.add(cv2);
	finance_array_view.add(cv21);
	
	
	View cv3=new View(this);
	cv3.setBackgroundColor(Color.CYAN);
	cv3.setMinimumWidth(50);
	cv3.setMinimumHeight(50);
	cv3.setPadding(3,3,3,3);
	cv3.setBackgroundColor(Color.GREEN);
	relation_array_view.add(cv3);
	
		
		adpt=new mAdapter(this,career_array_view);
		career_grid.setAdapter(adpt);
		adpt=new mAdapter(this,health_array_view);
		health_grid.setAdapter(adpt);
		adpt=new mAdapter(this,finance_array_view);
		finance_grid.setAdapter(adpt);
		adpt=new mAdapter(this,relation_array_view);
		relationship_grid.setAdapter(adpt);
		/*
ArrayList<String> menuArray=new ArrayList<String>();
menuArray.add("Prediction for Next Week");
menuArray.add("Settings");

ArrayAdapter<String> adp=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,menuArray);

menuListView.setAdapter(adp);*/

		

		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inf=getMenuInflater();
		inf.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.mainactivity_menu){
			Intent i=new Intent(getApplicationContext(),MainActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	
	class mAdapter extends BaseAdapter{Context c;
	ArrayList<View> mView;

	mAdapter(Context c,ArrayList<View> mView){
		this.c=c;
		this.mView=mView;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mView.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mView.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
			convertView=mView.get(position);
		
		return convertView;
	}
	}
}
