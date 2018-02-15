package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    Calendar myCalendar = Calendar.getInstance();
    //DatePickerDialog datePickerDialog=new DatePickerDialog();
    private String nname, nemail, nphone,naddress,npurpose,ncity,nbloodgroup,nrequredate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgency);
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
                new DatePickerDialog(UrgencyActivity.this,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

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
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            view.setMinDate(System.currentTimeMillis() - 10000);
            updateLabel();
        }

    };
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etrequiredate.setText(sdf.format(myCalendar.getTime()));
    }

    private void insertToDatabase(final String getname,final String getphone,final String getemail,
                                  final String getaddress, final String getpurpose, final String getcity, final String getbloodgroup, final String getrequiredate) {
        class SendPostReqAsyncTask extends AsyncTask<Object, Object, Void> {
            @Override
            protected Void doInBackground(Object... strings) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(UrgencyActivity.this, "Request Send Successfully", Toast.LENGTH_LONG).show();
                etname.setText("");
                etemail.setText("");
                etphone.setText("");
                etaddress.setText("");
                etpurpose.setText("");
                etcity.setText("");
                etrequiredate.setText("");
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
