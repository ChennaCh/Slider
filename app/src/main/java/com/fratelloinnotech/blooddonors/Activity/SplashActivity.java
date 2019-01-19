package com.fratelloinnotech.blooddonors.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fratelloinnotech.blooddonors.Map.MainMapActivity;
import com.fratelloinnotech.blooddonors.Permissions.AbsRuntimePermission;
import com.fratelloinnotech.blooddonors.R;

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
