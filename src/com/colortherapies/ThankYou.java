package com.colortherapies;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.colortherapies.helper.L;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rafique on 7/7/2014.
 */
public class ThankYou extends Activity {
    Button dash;
    TextView expiry_txt, thanks, welcome;
    String default_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        dash = (Button) findViewById(R.id.register_thankyou_btn);
        expiry_txt = (TextView) findViewById(R.id.register_thankyou_expire_txt);
        thanks = (TextView) findViewById(R.id.welcome_premium_thanks_txt);
        welcome = (TextView) findViewById(R.id.welcome_register_success);

        thanks.setTypeface(Typeface.createFromAsset(getAssets(), "font/Helvetica Neue UltraLight.ttf"));
        welcome.setTypeface(Typeface.createFromAsset(getAssets(), "font/Helvetica Neue UltraLight.ttf"));
        expiry_txt.setTypeface(Typeface.createFromAsset(getAssets(), "font/Helvetica Neue UltraLight.ttf"));
        long theFuture = System.currentTimeMillis() + (86400 * 7 * 1000);
        Date nextWeek = new Date(theFuture);
        default_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(nextWeek);

        expiry_txt.setText(default_date);
        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThankYou.this, Dashboard.class));
                finish();
            }
        });

        Intent get=this.getIntent();
        String intent_txt=get.getStringExtra("expiry_date");
        if(intent_txt!=null&&!intent_txt.equals("")){
            expiry_txt.setText(intent_txt);
            L.t(this,"Got the intent as :"+intent_txt);
        }else{
            L.t(this,"Not recieved any intent");
        }
    }
}
