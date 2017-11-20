package com.fit.bloodmanagment.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.Permissions.AbsRuntimePermission;
import com.fit.bloodmanagment.R;

public class SplashActivity extends AbsRuntimePermission {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);;

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainMapActivity.class));
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}
