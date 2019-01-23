package com.fratelloinnotech.blooddonors.UserProfile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fratelloinnotech.blooddonors.Map.MainMapActivity;
import com.fratelloinnotech.blooddonors.Network.ConnectivityReceiver;
import com.fratelloinnotech.blooddonors.R;
import com.fratelloinnotech.blooddonors.Utils.API;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.SignInButton;


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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fratelloinnotech.blooddonors.Network.Conn.displayMobileDataSettingsDialog;

public class SignUpActivity extends AppCompatActivity  {
    Toolbar toolbar;
    EditText  etname,etemail,etpassword,etphone,etaddress,etage,etcity;
    RadioButton radiogender;
    Button register;
    String rescode;
    Spinner spinnerbloodgroup;
    String fullname,email,password,phone,address,age,city,gender,bloodgroup;
    TextView regerror;
    AdView mAdView;

    //Signin button
    private SignInButton signInButton;
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

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
        regerror = (TextView) findViewById(R.id.regerror);
        regerror.setText("");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regerror.setText("");
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
                    regerror.setText("Please Enter UserName");
                } else if (!isValidEmail(email)) {
                   // etemail.requestFocus();
                    regerror.setText("Please Enter valid email");
                } else if (password.isEmpty()) {
                   // etpassword.requestFocus();
                    regerror.setText("Please Enter password");
                } else if (!isValidMobile(phone)) {
                   // etphone.requestFocus();
                    regerror.setText("Please Enter Valid Phone Number");
                } else if (address.equals("")) {
                    //etaddress.requestFocus();
                    regerror.setText("Please Enter Address");
                } else if (age.equals("")) {
                   // etage.requestFocus();
                    regerror.setText("Please Enter Age");
                }
                else if (Integer.parseInt(age)<18 ) {
                    // etage.requestFocus();
                    regerror.setText("Your Age must be more than 18 Yrs");
                }
                else if (Integer.parseInt(age)>=60 ) {
                    // etage.requestFocus();
                    regerror.setText("Your Age must be less than 60 Yrs");
                }
                else if (city.equals("")) {
                   // etcity.requestFocus();
                    regerror.setText("Please Enter Location");
                }
                else if (fullname.trim().length() > 0 && email.trim().length() > 0 && password.trim().length() > 0 && phone.trim().length() > 0 && address.trim().length() > 0 && city.trim().length() > 0 && age.length() > 0) {
                    if(ConnectivityReceiver.isConnected()==false){
                        //checkConnection();
                        displayMobileDataSettingsDialog(SignUpActivity.this);
                    }
                    registerapicall(fullname, password, phone, email, address, gender, bloodgroup,age,city);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error in Registration",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


            private void registerapicall(final String getname, final String getpassword, final String getphone,
                                         final String getemail, final String getaddress, final String getgender, final String getbloodgroup, final String getage, final String getcity) {
                class SendPostReqAsyncTask extends AsyncTask<Object, Object, String> {
                    @Override
                    protected String doInBackground(Object... strings) {
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
                            rescode = EntityUtils.toString(entity);
                           // int rescode1 =  response.getStatusLine().getStatusCode();
                            //rescode = res;


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
                                Toast.makeText(SignUpActivity.this, "For your generosity, I thank you", Toast.LENGTH_LONG).show();
                                etname.setText("");
                                etemail.setText("");
                                etphone.setText("");
                                etaddress.setText("");
                                etcity.setText("");
                                etage.setText("");
                                etpassword.setText("");
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this, "Sorry! Email already exists, Registration not success", Toast.LENGTH_LONG).show();
                                //etemail.setError("Email already exists");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
                sendPostReqAsyncTask.execute(getname, getpassword, getage, getphone, getemail, getaddress, getbloodgroup, getgender, getcity);
            }

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




