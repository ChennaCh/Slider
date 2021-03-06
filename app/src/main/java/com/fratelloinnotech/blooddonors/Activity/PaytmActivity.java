package com.fratelloinnotech.blooddonors.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fratelloinnotech.blooddonors.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class PaytmActivity extends AppCompatActivity {
Button payment;
AdView mAdView;
TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aboutus);
        setSupportActionBar(toolbar);
        setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        payment = (Button) findViewById(R.id.payment_paytm);

        textView = (TextView) findViewById(R.id.paytm_text);
        String redString = getResources().getString(R.string.payment_txt);
        textView.setText(Html.fromHtml(redString));

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),WebViewActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
