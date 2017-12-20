package com.fit.bloodmanagment.UserProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Activity.UrgencyActivity;
import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.API;

import com.google.android.gms.common.SignInButton;


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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity  {
    Toolbar toolbar;
    EditText  etname,etemail,etpassword,etphone,etaddress,etage,etcity;
    RadioButton radiogender;
    Button register;
    Spinner spinnerbloodgroup;
    String fullname,email,password,phone,address,age,city,gender,bloodgroup;
    //Signin button
    private SignInButton signInButton;
    //Signing Options
    //private GoogleSignInOptions gso;
    //google api client
    //private GoogleApiClient mGoogleApiClient;
    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;
    //TextViews
    private TextView textViewName;
    private TextView textViewEmail;
    private NetworkImageView profilePhoto;
    //Image Loader
    private ImageLoader imageLoader;
    //Calendar myCalendar = Calendar.getInstance();
    private String TAG = "RegisterActivity";
    public RadioGroup rgender;
     MainMapActivity mainmapsactivity=new MainMapActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar = (Toolbar) findViewById(R.id.signup_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.Signup));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        etname = (EditText) findViewById(R.id.editname);
        etemail = (EditText) findViewById(R.id.editemail);
        etpassword = (EditText) findViewById(R.id.editpwd);
        etphone = (EditText) findViewById(R.id.editphone);
        etaddress =(EditText)findViewById(R.id.signupaddress);
        etage = (EditText) findViewById(R.id.etage);
        spinnerbloodgroup = (Spinner) findViewById(R.id.signupspinner);
        etcity = (EditText) findViewById(R.id.etcity);
        rgender =  (RadioGroup) findViewById(R.id.radioSex);
        register = (Button) findViewById(R.id.regbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = etname.getText().toString();
                email = etemail.getText().toString();
                password = etpassword.getText().toString();
                phone = etphone.getText().toString();
                address = etaddress.getText().toString();
                age = etage.getText().toString();
                city=etcity.getText().toString();
                 //get selected radio button from radioGroup
                int selectedId = rgender .getCheckedRadioButtonId();

                // find the radio button by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                gender=radioButton.getText().toString();
                bloodgroup = spinnerbloodgroup.getSelectedItem().toString();
               // bloodgroup=((Spinner)findViewById(spinnerbloodgroup.getId())).toString();
               // gender = ((RadioButton)findViewById(spinnerbloodgroup.getId())).getText().toString();
                if (fullname.equals("")) {
                   // etname.requestFocus();
                    etname.setError("Enter username");
                } else if (!isValidEmail(email)) {
                   // etemail.requestFocus();
                    etemail.setError("Enter valid email");
                } else if (password.isEmpty()) {
                   // etpassword.requestFocus();
                    etpassword.setError("Invalid password");
                } else if (!isValidMobile(phone)) {
                   // etphone.requestFocus();
                    etphone.setError("Invalid phone number");
                } else if (address.equals("")) {
                    //etaddress.requestFocus();
                    etaddress.setError("Invalid address");
                } else if (age.equals("")) {
                   // etage.requestFocus();
                    etage.setError("Enter valid age");
                } else if (city.equals("")) {
                   // etcity.requestFocus();
                    etcity.setError("Enter City");
                }
                else if (fullname.trim().length() > 0 && email.trim().length() > 0 && password.trim().length() > 0 && phone.trim().length() > 0 && address.trim().length() > 0 && city.trim().length() > 0 && age.length() > 0) {
                    registerapicall(fullname, password, phone, email, address, gender, bloodgroup,age,city);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error in Registration",Toast.LENGTH_LONG).show();
                }
                // registerapicall();
//                mainmapsactivity.navigationView=(NavigationView) findViewById(R.id.nav_view);
//                //mainmapsactivity.navigationView.setNavigationItemSelectedListener(this);
//                View header  =  mainmapsactivity.navigationView.getHeaderView(0);
////                loginbtn = (Button) header.findViewById(R.id.signin_btn);
////                signupbtn = (Button) header.findViewById(R.id.signup_btn);
//                header.findViewById(R.id.signin_btn).setVisibility(View.INVISIBLE);
//                header.findViewById(R.id.signup_btn).setVisibility(View.INVISIBLE);
            }
        });
    }


            private void registerapicall(final String getname, final String getpassword, final String getphone,
                                         final String getemail, final String getaddress, final String getgender, final String getbloodgroup, final String getage, final String getcity) {
                class SendPostReqAsyncTask extends AsyncTask<Object, Object, Void> {
                    @Override
                    protected Void doInBackground(Object... strings) {
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("fullname", getname));
                        nameValuePairs.add(new BasicNameValuePair("password", getpassword));
                        nameValuePairs.add(new BasicNameValuePair("mobile", getphone));
                        nameValuePairs.add(new BasicNameValuePair("email", getemail));
                        nameValuePairs.add(new BasicNameValuePair("address",getaddress));
                        nameValuePairs.add(new BasicNameValuePair("gender", getgender));
                        nameValuePairs.add(new BasicNameValuePair("bloodgroup", getbloodgroup));
                        nameValuePairs.add(new BasicNameValuePair("age", getage));
                        nameValuePairs.add(new BasicNameValuePair("city", getcity));
                        try {
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost(API.registerUrl);
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
                        Toast.makeText(SignUpActivity.this, "Request Send Successfully", Toast.LENGTH_LONG).show();
                        etname.setText("");
                        etemail.setText("");
                        etphone.setText("");
                        etaddress.setText("");
                        etcity.setText("");
                        etage.setText("");
                        etpassword.setText("");
                    }
                }
                SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
                sendPostReqAsyncTask.execute(getname, getpassword, getage, getphone, getemail, getaddress, getbloodgroup, getgender, getcity);
            }


            //    private void updateLabel() {
//        String myFormat = "MM/dd/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        DOB.setText(sdf.format(myCalendar.getTime()));
//    }
//    private boolean isValidName(String name) {
//
//        name = "^[a-zA-Z\\s]{3,30}$";
//
//        Pattern pattern = Pattern.compile(name);
//        Matcher matcher = pattern.matcher(name);
//        return matcher.matches();
//    }
            private boolean isValidEmail(String email) {
                String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(EMAIL_PATTERN);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();

            }

            private boolean isValidMobile(String mobile) {
                String Mobile_PATTERN = "^+[0-9]{10}$";
                Pattern pattern = Pattern.compile(Mobile_PATTERN);
                Matcher matcher = pattern.matcher(mobile);
                return matcher.matches();

            }

            @Override
            public boolean onSupportNavigateUp() {
                onBackPressed();
                return true;
            }
}




