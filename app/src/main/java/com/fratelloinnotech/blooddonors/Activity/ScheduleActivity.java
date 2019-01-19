package com.fratelloinnotech.blooddonors.Activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.fratelloinnotech.blooddonors.Beans.AlarmReciever;
import com.fratelloinnotech.blooddonors.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    int mYear, mMonth, mDay;
    TextView last, red, platelets, plasma, whole, times;
    Button setDate, minus, plus, share;
    Date date;
    Calendar c;
    SharedPreferences.Editor edit;
    SharedPreferences spf;
    String dateStr;
    DatePickerDialog dialog;
    int i = 0;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_);
        setSupportActionBar(toolbar);
        setTitle("Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        setDate = (Button) findViewById(R.id.setDate);
//        plus = (Button) findViewById(R.id.plus);
//        share = (Button) findViewById(R.id.share);
//        minus = (Button) findViewById(R.id.minus);

        last = (TextView) findViewById(R.id.last);
        red = (TextView) findViewById(R.id.red);
        platelets = (TextView) findViewById(R.id.platelets);
        plasma = (TextView) findViewById(R.id.plasma);
        whole = (TextView) findViewById(R.id.whole);
       // times = (TextView) findViewById(R.id.times);

        spf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        last.setText(spf.getString("last", "not set yet"));
        red.setText(spf.getString("red", "not set yet"));
        plasma.setText(spf.getString("plasma", "not set yet"));
        platelets.setText(spf.getString("platelets", "not set yet"));
        whole.setText(spf.getString("whole", "not set yet"));
        //times.setText(spf.getString("times", "0") + " times");

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(ScheduleActivity.this, new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();

            }
        });

    }

  public class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            ///////////////////////////////////

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            date = c.getTime();
            dateStr = dateFormat.format(date);

            last.setText(dateStr);

            save("last", dateStr);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 7);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            platelets.setText(dateStr);

            save("platelets", dateStr);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 56);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            whole.setText(dateStr);

            save("whole", dateStr);

            setAlarm(c);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 112);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            red.setText(dateStr);

            save("red", dateStr);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 3);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            plasma.setText(dateStr);

            save("plasma", dateStr);

            ///////////////////////////////////


        }
    }
    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();
    }
    private void setAlarm(Calendar targetCal) {

        Intent intent = new Intent(getApplicationContext(), AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

        Toast.makeText(getApplicationContext() , "Date Set!\nYou'll be notified upon the next donation time." , Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
