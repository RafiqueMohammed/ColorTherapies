package com.colortherapies;

import java.util.List;

import org.json.JSONException;

import com.colortherapies.helper.ColorView;
import com.colortherapies.helper.Methods;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

public class Test extends Activity {

	GridView HGrid, RGrid, FGrid;
	List<View> HList, RList, FList;
	ColorView colorView;
	String decode = "{\"health\":[\"#FFCCBB\",\"#99bbcc\",\"#2211FF\"],\"relationship\":[\"#1FFCBB\",\"#F9CCBB\",\"#992BCC\"],\"finance\":[\"#FFCC00\",\"#559922\",\"#929b9C\"]}";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		HGrid = (GridView) findViewById(R.id.health_grid);
		RGrid = (GridView) findViewById(R.id.relationship_grid);
		FGrid = (GridView) findViewById(R.id.finance_grid);
		
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


}
