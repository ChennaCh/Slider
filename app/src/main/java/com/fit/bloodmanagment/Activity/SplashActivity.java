package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fit.bloodmanagment.Map.MapsActivity;
import com.fit.bloodmanagment.R;

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

//                Intent intent = new Intent(SplashActivity.this,MapsActivity.class);
//                startActivity(intent);
                startActivity(new Intent(SplashActivity.this, MapsActivity.class));
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
