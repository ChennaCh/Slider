package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.Network.ConnectivityReceiver;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.UserProfile.SiginInActivity;
import com.fit.bloodmanagment.UserProfile.SignUpActivity;
import com.fit.bloodmanagment.Utils.API;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.fit.bloodmanagment.Network.Conn.displayMobileDataSettingsDialog;
import static com.fit.bloodmanagment.R.id.toolbar;

public class UrgencyActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText  etname,etemail,etphone,etaddress,etpurpose,etcity,etrequiredate;
    Spinner bloodgroupspinner;
    Button sendrequestbtn;
    String rescode;
    Calendar myCalendar = Calendar.getInstance();
    AdView mAdView;

    //DatePickerDialog datePickerDialog=new DatePickerDialog();
    private String nname, nemail, nphone,naddress,npurpose,ncity,nbloodgroup,nrequredate;
    private int day;
    private int month;
    private int year;
    static final int DATE_PICKER_ID = 1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgency);

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.urgency_toolbar);
        setTitle("Emergency Request");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        etname = (EditText) findViewById(R.id.myUusername);
        etemail = (EditText) findViewById(R.id.myUemail);
        etphone = (EditText) findViewById(R.id.myUmobile);
        etaddress = (EditText) findViewById(R.id.myUaddress);
        etpurpose=(EditText)findViewById(R.id.myUpurpose);
        etcity=(EditText)findViewById(R.id.myUCity);
        bloodgroupspinner=(Spinner)findViewById(R.id.myUspinner);
        etrequiredate=(EditText)findViewById(R.id.etrequireddate);
        sendrequestbtn=(Button)findViewById(R.id.myUrequest);
        etrequiredate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                new DatePickerDialog(UrgencyActivity.this,date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                myCalendar = Calendar.getInstance();
                day = myCalendar.get(Calendar.DAY_OF_MONTH);
                month = myCalendar.get(Calendar.MONTH);
                year = myCalendar.get(Calendar.YEAR);
                showDialog(DATE_PICKER_ID);

            }
        });

        sendrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //apicall();
                nname = etname.getText().toString();
                nphone = etphone.getText().toString();
                nemail = etemail.getText().toString();
                nbloodgroup = bloodgroupspinner.getSelectedItem().toString();
                ncity = etcity.getText().toString();
                npurpose=etpurpose.getText().toString();
                naddress=etaddress.getText().toString();
                nrequredate=etrequiredate.getText().toString();
                nbloodgroup = bloodgroupspinner.getSelectedItem().toString();
                String emailPattern1 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                if (nname.equals("")){
                    etname.requestFocus();
                    etname.setError("Please Enter UserName");

                }else if (!nemail.matches(emailPattern1)){
                    etemail.requestFocus();
                    etemail.setError("Please Enter valid MailId");

                }
                else if (nphone.length()< 10){
                    etphone.requestFocus();
                    etphone.setError("Enter 10 digits Number");

                }
                else if (naddress.equals("")){
                    etaddress.requestFocus();
                    etaddress.setError("Please Enter Address");

                }else if(npurpose.equals("")){
                    etpurpose.requestFocus();
                    etpurpose.setError("Please Enter Purpose");

                }else if(ncity.equals("")){
                    etcity.requestFocus();
                    etcity.setError("Please Enter City");
                }else if(nrequredate.equals("")){
                    etrequiredate.requestFocus();
                    etrequiredate.setError("Please Enter Date");
                }

                if (nname.trim().length() > 0 && naddress.trim().length() > 0 && npurpose.trim().length()>0 &&ncity.trim().length()>0){
                    if(ConnectivityReceiver.isConnected()==false){
                        //checkConnection();
                        displayMobileDataSettingsDialog(UrgencyActivity.this);
                    }
                    insertToDatabase(nname, nphone, nemail, naddress,npurpose,ncity,nbloodgroup,nrequredate);
                    //apicall();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_LONG).show();

                }

            }

        });



    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                // create a new DatePickerDialog with values you want to show

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, day);
                myCalendar = Calendar.getInstance();
                myCalendar.add(Calendar.DATE, +1); // Add 0 days to Calendar
                Date newDate = myCalendar.getTime();
                datePickerDialog.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
                return datePickerDialog;
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // the callback received when the user "sets" the Date in the
        // DatePickerDialog
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            etrequiredate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }
    };


    private void insertToDatabase(final String getname,final String getphone,final String getemail,
                                  final String getaddress, final String getpurpose, final String getcity, final String getbloodgroup, final String getrequiredate) {
        class SendPostReqAsyncTask extends AsyncTask<Object, Object, String> {
            @Override
            protected String doInBackground(Object... strings) {
                // name,email,mobile,address,bloodgroup,city,purpose,duedate
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", getname));
               // nameValuePairs.add(new BasicNameValuePair("password", getpass));
                nameValuePairs.add(new BasicNameValuePair("mobile", getphone));
                nameValuePairs.add(new BasicNameValuePair("email", getemail));
                nameValuePairs.add(new BasicNameValuePair("purpose", getpurpose));
                nameValuePairs.add(new BasicNameValuePair("bloodgroup", getbloodgroup));
                nameValuePairs.add(new BasicNameValuePair("city", getcity));
                nameValuePairs.add(new BasicNameValuePair("address", getaddress));
                nameValuePairs.add(new BasicNameValuePair("duedate",getrequiredate));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(API.addbloodneedUrl);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    rescode = EntityUtils.toString(entity);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return rescode;
            }

            @Override
            protected void onPostExecute(String code) {
                super.onPostExecute(code);
                try {
                    JSONObject res=new JSONObject(code);
                    String op=res.getString("result");
                    if(op.equals("success")) {
                        Toast.makeText(UrgencyActivity.this, "Request Send Successfully", Toast.LENGTH_LONG).show();
                        etname.setText("");
                        etemail.setText("");
                        etphone.setText("");
                        etaddress.setText("");
                        etpurpose.setText("");
                        etcity.setText("");
                        etrequiredate.setText("");
                    }
                    else
                    {
                        Toast.makeText(UrgencyActivity.this, "Sorry! Request Not Send successfully", Toast.LENGTH_LONG).show();
                        //etemail.setError("Email already exists");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(getname, getphone, getemail, getaddress,getpurpose,getcity,getbloodgroup,getrequiredate);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
