package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fit.bloodmanagment.R;


public class BloodBanks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_banks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_blodbanks);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.BloodBanks));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
