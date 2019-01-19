package com.fratelloinnotech.blooddonors.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fratelloinnotech.blooddonors.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ResultActivity extends AppCompatActivity {
    int flag = 0;
    View root;
    TextView resultt, data;
   // Button learn, sched;
   AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_);
        setSupportActionBar(toolbar);
        setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        resultt = (TextView) findViewById(R.id.textRes);
        data = (TextView) findViewById(R.id.data);

        Bundle bundle = getIntent().getExtras();

        int q1 = bundle.getInt("q1");
        int q2 = bundle.getInt("q2");
        int q3 = bundle.getInt("q3");
        int q4 = bundle.getInt("q4");
        int q5 = bundle.getInt("q5");
        int q6 = bundle.getInt("q6");
        int q7 = bundle.getInt("q7");
        int q8 = bundle.getInt("q8");
        int q9 = bundle.getInt("q9");

        if (q1 == 0 || q2 == 0 || q3 == 0 || q4 == 0 || q5 == 0 || q6 == 0|| q7 == 0 || q8 == 0||q9 == 0) {


           // sched.setVisibility(View.INVISIBLE);

            resultt.setText("Sorry, You did not pass the test");

            data.setText("\nYou seem to not meet one of the criteria of being a blood donor," +
                    "this means that your blood isn't safe for transfusion or that blood donation may affect your health or live." +
                    "\n\nYou can check the 'Learn' Section to know what you are missing.");

        } else {

            //learn.setVisibility(View.INVISIBLE);

            resultt.setText("Congrats, You passed the test!");

            data.setText("\nYou met the criteria, and now you can donate your blood to any one who need need it, this is your first step towards change. \n\n" +
                    "You can go to the nearest hospital and donate your blood, make your own blood donation schedule " +
                    "or register in the blood donatiob service to make people know that another generous donor is available.");

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
