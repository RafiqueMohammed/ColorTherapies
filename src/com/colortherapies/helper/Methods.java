package com.colortherapies.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.colortherapies.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
	
	public static HttpClient getHttp() {
		HttpParams p = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(p, 20000);
		HttpConnectionParams.setSoTimeout(p, 20000);
		return new DefaultHttpClient(p);
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
	
	
public static class CheckAuthentication extends AsyncTask<String, Void, JSONObject> {

		
		@Override
		public JSONObject doInBackground(String... url) {
			// TODO Auto-generated method stub
			String URL= url[0];
			HttpClient http=Methods.getHttp();
			HttpPost post=new HttpPost(URL);
			
			List<NameValuePair> pair=new ArrayList<NameValuePair>();
			pair.add(new BasicNameValuePair("AuthToken",""));
			pair.add(new BasicNameValuePair("username",""));
			String output = "";
			JSONObject json_output=null;
			try {
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(pair);
				post.setEntity(entity);
				HttpResponse response= http.execute(post);
				
				int status=response.getStatusLine().getStatusCode();
				
				if(status==200){
					
					BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					
					String tmp="",read="";
					
					while((tmp=br.readLine())!=null){
						read+=tmp;
					}
					if(!read.equals("")){
						json_output=new JSONObject(read);
					}else{
						output = "{\"status\":\"no\",\"result\":\"No data recieved\"}";
						json_output=new JSONObject(output);
					}
				}else if (status == 400) {
					output = "{\"status\":\"no\",\"result\":\"Invalid Type Request\"}";
					json_output = new JSONObject(output);
				} else if (status == 404) {
					output = "{\"status\":\"no\",\"result\":\"No such request found\"}";
					json_output = new JSONObject(output);
				} else if (status == 500) {
					output = "{\"status\":\"no\",\"result\":\"Server is temporarily down. Please try again later\"}";
					json_output = new JSONObject(output);
				} 
				
				return json_output;
			} catch (UnsupportedEncodingException e) {
				L.l("UnsupportedEncodingException "+e.toString());
			} catch (ClientProtocolException e) {
				L.l("ClientProtocolException "+e.toString());			
				} catch (IOException e) {
					L.l("IOException "+e.toString());			
					} catch (JSONException e) {
						L.l("JSONException "+e.toString());
						}
			return json_output;
		}


	}
	
	}




