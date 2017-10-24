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
import com.fit.bloodmanagment.R;
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

import static com.fit.bloodmanagment.R.id.toolbar;

public class UrgencyActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText  etname,etemail,etphone,etaddress,etpurpose,etcity,etrequiredate;
    Spinner bloodgroupspinner;
    Button sendrequestbtn;
    Calendar myCalendar = Calendar.getInstance();
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

                }else if (nphone.length()< 10){
                    etphone.requestFocus();
                    etphone.setError("Enter 10 digits Number");

                }
                else if (!nemail.matches(emailPattern1)){
                    etemail.requestFocus();
                    etemail.setError("Please Enter valid MailId");

                }else if (naddress.equals("")){
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
                    //insertToDatabase(nname, nphone, nemail, naddress,npurpose,ncity,nbloodgroup,nrequredate);
                    apicall();
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
            updateLabel();
        }

    };
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etrequiredate.setText(sdf.format(myCalendar.getTime()));
    }

//    private void insertToDatabase(final String getname, final String getaddress, final String getphone,
//                                  final String getemail, final String getbloodgroup, final String getpurpose, final String getcity, final String getrequredate) {
//        class SendPostReqAsyncTask extends AsyncTask<Object, Object, Void> {
//            @Override
//            protected Void doInBackground(Object... strings) {
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                nameValuePairs.add(new BasicNameValuePair("name", getname));
//               // nameValuePairs.add(new BasicNameValuePair("password", getpass));
//                nameValuePairs.add(new BasicNameValuePair("mobile", getphone));
//                nameValuePairs.add(new BasicNameValuePair("email", getemail));
//                nameValuePairs.add(new BasicNameValuePair("purpose", getpurpose));
//                nameValuePairs.add(new BasicNameValuePair("bloodgroup", getbloodgroup));
//                nameValuePairs.add(new BasicNameValuePair("city", getcity));
//                nameValuePairs.add(new BasicNameValuePair("addresss", getaddress));
//                nameValuePairs.add(new BasicNameValuePair("date",getrequredate));
//                try {
//                    HttpClient httpClient = new DefaultHttpClient();
//                    HttpPost httpPost = new HttpPost(API.addbloodneedUrl);
//                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                    HttpResponse response = httpClient.execute(httpPost);
//                    HttpEntity entity = response.getEntity();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                Toast.makeText(UrgencyActivity.this, "Request Send Successfully", Toast.LENGTH_LONG).show();
//                etname.setText("");
//                etemail.setText("");
//                etphone.setText("");
//                etaddress.setText("");
//                etpurpose.setText("");
//                etcity.setText("");
//                etrequiredate.setText("");
//            }
//        }
//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//        sendPostReqAsyncTask.execute(getname, getphone, getemail, getaddress, getbloodgroup, getpurpose,getcity);
//    }

    private  void apicall(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.addbloodneedUrl;
        final StringRequest getRequest = new StringRequest(Request.Method.POST, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("officerResponse", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String str_status = jsonObject.getString("result");
                            //String str_msg = jsonObject.getString("message");



                            if (str_status.equals("success")) {
                                JSONObject Jsonobject = jsonObject.getJSONObject("data");
//                                String  shop = Jasonobject.getString("shop_name");
//                                String  owner = Jasonobject.getString("owner_name");
                               String name=Jsonobject.getString("myname");
                                String mobile=Jsonobject.getString("mymoblle");
                                String email=Jsonobject.getString("myemail");
                                String purpose=Jsonobject.getString("mypurpose");
                                String bloodgroup=Jsonobject.getString("mybloodgroup");
                                String city=Jsonobject.getString("mycity");
                                String addresss=Jsonobject.getString("myaddresss");
                                String date=Jsonobject.getString("myduedate");

                                //Navigating to the another activity
                                Intent intent = new Intent(getApplicationContext(), MainMapActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"Sent Request Successfully", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                        Log.d("UrgenncyActivity", String.valueOf(error));

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //  params.put("imei", device_imei_no);
                params.put("myname", nname);
                params.put("mymobile", nphone);
                params.put("myemail", nemail);
                params.put("mypurpose", npurpose);
                params.put("mybloodgroup", nbloodgroup);
                params.put("mycity", ncity);
                params.put("myaddress", naddress);
                params.put("myduedate", nrequredate);
//                params.put("working_fulltime",  radio_value);
//                params.put("city_id", str_IssueTypeId_post);
//                params.put("working_days", MAIN);
//                params.put("start_time", strtime);
//                params.put("end_time", endti);



                Log.d("tag",params.toString());
                return params;
            }
        };
        queue.add(getRequest);

    }







    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
