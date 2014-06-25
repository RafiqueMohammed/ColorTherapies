package com.colortherapies.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class L {
	public static void l(String msg) {
		Log.d("ARR", msg);
	}

	public static void t(Context c, String msg) {
		Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
	}
}
