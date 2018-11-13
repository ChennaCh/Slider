package com.fit.bloodmanagment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.fit.bloodmanagment.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://securegw.paytm.in/link/72036/LL_907699");
    }
}
