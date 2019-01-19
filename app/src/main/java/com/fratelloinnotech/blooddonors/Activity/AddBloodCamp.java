package com.fratelloinnotech.blooddonors.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.fratelloinnotech.blooddonors.Network.ConnectivityReceiver;
import com.fratelloinnotech.blooddonors.Network.VolleySingleton;
import com.fratelloinnotech.blooddonors.R;
import com.fratelloinnotech.blooddonors.Utils.API;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.fratelloinnotech.blooddonors.Network.Conn.displayMobileDataSettingsDialog;

public class AddBloodCamp extends AppCompatActivity {
    Toolbar toolbar;
    EditText orgname, venuename, venuaddress, email, mobile, location, descr;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    protected static TextView displayCurrentDate;
    protected static TextView displayCurrentTime;
    Button done;
    TextView adderrors;
    ImageView sdate, stime;
    StringBuffer Date;
    int day, month, year;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_camp);

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.addcamp_toolbar);
        setTitle("Create Blood Donation Camp");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        done = (Button) findViewById(R.id.subbtn);
        displayCurrentDate = (TextView) findViewById(R.id.date);
        displayCurrentTime = (TextView) findViewById(R.id.time);
        sdate = (ImageView) findViewById(R.id.imgdate);
        stime = (ImageView) findViewById(R.id.imgtime);
        orgname = (EditText) findViewById(R.id.organizer);
        venuename = (EditText) findViewById(R.id.venue);
        venuaddress = (EditText) findViewById(R.id.campaddress);
        email = (EditText) findViewById(R.id.campemail);
        mobile = (EditText) findViewById(R.id.campmobile);
        location = (EditText) findViewById(R.id.city);
        descr = (EditText) findViewById(R.id.descript);
        adderrors = (TextView)findViewById(R.id.adderror);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adderrors.setText("");
                String organiser = orgname.getText().toString();
                String venuname = venuename.getText().toString();
                String venaddress = venuaddress.getText().toString();
                String venuemail = email.getText().toString();
                String vmobile = mobile.getText().toString();
                String locat = location.getText().toString();
                String des = descr.getText().toString();
                String dat = displayCurrentDate.getText().toString();
                String tim = displayCurrentTime.getText().toString();
                if(organiser.equals("")){
                   adderrors.setText("Please Enter Organiser Name");
                }else if(venuname.equals("")){
                    adderrors.setText("Please Enter Venue Name");
                }else if(venaddress.equals("")) {
                    adderrors.setText("Please Enter Venue Address");
                }
                else if(venuemail.equals("")) {
                    adderrors.setText("Please Enter Email Id");
                }else if(vmobile.equals("")) {
                    adderrors.setText("Please Enter Mobile Number");
                }else if(locat.equals("")) {
                    adderrors.setText("Please Enter City/Location");
                }
                else if(des.equals("")) {
                    adderrors.setText("Please Add Description about Event");
                }
                else if(dat.equals("Date")) {
                    adderrors.setText("Please Select Date");
                }
                else if(tim.equals("Time")) {
                    adderrors.setText("Please Select Time");
                }

                else if (organiser.trim().length() > 0 && venuname.trim().length() > 0 && venaddress.trim().length() > 0 && venuemail.trim().length() > 0 && vmobile.trim().length() > 0 && locat.trim().length() > 0 && des.trim().length() > 0&&dat.length()>0&&tim.length()>0) {
                    if(ConnectivityReceiver.isConnected()==false){
                        //checkConnection();
                        displayMobileDataSettingsDialog(AddBloodCamp.this);
                    }
                    addcamp();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error in Registration",Toast.LENGTH_LONG).show();
                }
            }
        });

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

        day = c.get(Calendar.DATE);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        Date = new StringBuffer();
        Date.append(year).append("-").append(month + 1).append("-").append(day);
      //  Toast.makeText(getApplicationContext(),Date,Toast.LENGTH_SHORT).show();

        sdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DatePickerFragment mDatePicker = new DatePickerFragment();
                mDatePicker.show(getFragmentManager(), "Select date");

            }
        });
        stime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker mTimePicker = new TimePicker();
                mTimePicker.show(getFragmentManager(), "Select time");
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private StringBuffer Date;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            displayCurrentDate.setText(String.valueOf(year) + " - " + String.valueOf(month+1) + " - " + String.valueOf(day));
        }

    }

    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            displayCurrentTime.setText(String.valueOf(hourOfDay) + " : " + String.valueOf(minute));
        }
    }

    private void addcamp() {
        //calling url
        String serverurl = API.addbloddcampurl;
        //sending request to url for response Or Request Constructer with 4 parameters
        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");//result should be matched with url link response ie,{"result":"success"}
                    if (res.equals("success")) //array key
                    {
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("ERROR", "Exception");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Main", "Error: " + error.getMessage());
                Log.d("Main", "" + error.getMessage() + "," + error.toString());

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                //To Read data from Edit fields and convert to string
                String organiser = orgname.getText().toString();
                String venuname = venuename.getText().toString();
                String venaddress = venuaddress.getText().toString();
                String venuemail = email.getText().toString();
                String vmobile = mobile.getText().toString();
                String locat = location.getText().toString();
                String des = descr.getText().toString();
                String dat = displayCurrentDate.getText().toString();
                String tim = displayCurrentTime.getText().toString();

                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put("orgname", organiser);
                data.put("venue", venuname);
                data.put("address", venaddress);
                data.put("email", venuemail);
                data.put("mobile", vmobile);
                data.put("city", locat);
                data.put("description", des);
                data.put("cdate", dat);
                data.put("ctime", tim);

                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
