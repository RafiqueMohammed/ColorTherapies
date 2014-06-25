package com.colortherapies.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class ColorView {
	Context c;
	View mView;
	JSONObject JObj;
	JSONArray JHealth, JRelationship, JFinance;
	public static final int TYPE_HEALTH = 1;
	public static final int TYPE_RELATIONSHIP = 2;
	public static final int TYPE_FINANCE = 3;

	public ColorView(Context c, String json) throws JSONException {
		JObj = new JSONObject(json);
		initJArrays();
		this.c = c;
	}

	private void initJArrays() throws JSONException {
		if (checkHRFExist()) {
			JHealth = JObj.getJSONArray("health");
			JRelationship = JObj.getJSONArray("relationship");
			JFinance = JObj.getJSONArray("finance");
		} else {
			JHealth = null;
			JRelationship = null;
			JFinance = null;
		}

	}

	private boolean checkHRFExist() {
		// TODO Auto-generated method stub
		if(JObj.has("health")&&JObj.has("relationship")&&JObj.has("finance")){
			return true;
		}
		return false;
	}

	private View getView(String color) {
		View cv = new View(c);

		if (isHex(color)) {
			cv.setBackgroundColor(Color.parseColor(color));
		} else {
			cv.setBackgroundColor(Color.TRANSPARENT);
		}
		cv.setMinimumWidth(30);
		cv.setMinimumHeight(50);
		cv.setPadding(3, 3, 3, 3);
		
		return cv;

	}

	private boolean isHex(String color) {

		if (color.contains("#")) {
			if (color.substring(1).matches("\\p{XDigit}+")
					&& color.length() == 7) {
				return true;
			}
		}
		return false;
	}

	public List<View> getViewList(int type) throws JSONException {

		List<View> mListView = new ArrayList<View>();

		int i = 0;

		switch (type) {

		case ColorView.TYPE_HEALTH:
			if (JHealth != null) {
				for (i = 0; i < JHealth.length(); i++) {
					mListView.add(getView(JHealth.getString(i)));
				}
			} else {
				mListView.add(getView("#FFFFFF"));
			}
			;
			break;

		case ColorView.TYPE_RELATIONSHIP:

			if (JRelationship != null) {
				for (i = 0; i < JRelationship.length(); i++) {
					mListView.add(getView(JRelationship.getString(i)));
				}
			} else {
				mListView.add(getView("#FFFFFF"));
			}
			;
			break;

		case ColorView.TYPE_FINANCE:

			if (JFinance != null) {
				for (i = 0; i < JFinance.length(); i++) {
					mListView.add(getView(JFinance.getString(i)));
				}
			} else {
				mListView.add(getView("#FFFFFF"));
			}
			;
			break;
		}

		return mListView;
	}
}
