package com.style;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyText extends TextView {

	public MyText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		createFont();
	}
	public MyText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		createFont();
	}
	
	public MyText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		createFont();
	}
	
	void createFont(){
		Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"font/Helvetica Neue UltraLight.ttf");
		setTypeface(typeface);
	}

}
