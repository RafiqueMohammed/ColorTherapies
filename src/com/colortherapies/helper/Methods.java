package com.colortherapies.helper;

import java.util.List;

import com.colortherapies.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class Methods {

	public static boolean isInternetConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public static void t(Context c, String msg) {
		Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
	}

	public static void showAlert(Context context, String title, String message,
			Boolean status) {

		AlertDialog alertDialog = new AlertDialog.Builder(
				new ContextThemeWrapper(context,
						android.R.style.Theme_Holo_Light_Dialog_NoActionBar))
				.create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.success : R.drawable.error);

		// Setting OK Button
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	public static void AlertWithRedirect(final Context context, String title,
			String message, final Activity page, Boolean status) {

		AlertDialog alertDialog = new AlertDialog.Builder(
				new ContextThemeWrapper(context,
						android.R.style.Theme_Holo_Light_Dialog_NoActionBar))
				.create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.success : R.drawable.error);

		// Setting OK Button
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						context.startActivity(new Intent(context, page
								.getClass())
								.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
										| Intent.FLAG_ACTIVITY_SINGLE_TOP));
						((Activity) context).finish();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	public static class DashboardColorAdapter extends BaseAdapter {
		Context c;
		List<View> mView;

		public DashboardColorAdapter(Context c, List<View> mView) {
			this.c = c;
			this.mView = mView;

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
			convertView = mView.get(position);

			return convertView;
		}
	}
	}




