package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fit.bloodmanagment.R;


public class ContactUsActivity extends AppCompatActivity {
  Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initToolbar();

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_conatactus);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.Contactus));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
