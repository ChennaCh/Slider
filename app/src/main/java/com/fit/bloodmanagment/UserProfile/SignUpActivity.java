package com.fit.bloodmanagment.UserProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.API;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity  {
    Toolbar toolbar;
    EditText  etname,etemail,etpassword,etphone,etaddress,DOB;
    Button register;
    Spinner bloodgroup;
    String name,email,password,phone,address,dateofbirth;
    //Signin button
    private SignInButton signInButton;
    //Signing Options
    private GoogleSignInOptions gso;
    //google api client
    private GoogleApiClient mGoogleApiClient;
    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;
    //TextViews
    private TextView textViewName;
    private TextView textViewEmail;
    private NetworkImageView profilePhoto;
    //Image Loader
    private ImageLoader imageLoader;
    Calendar myCalendar = Calendar.getInstance();
    private String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar =(Toolbar) findViewById(R.id.signup_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.Signup));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        etname = (EditText) findViewById(R.id.editname);
        etemail = (EditText) findViewById(R.id.editemail);
        etpassword = (EditText) findViewById(R.id.editpwd);
        etphone = (EditText) findViewById(R.id.editphone);
        etaddress = (EditText) findViewById(R.id.editaddress);
        DOB = (EditText) findViewById(R.id.editText);
        bloodgroup=(Spinner)findViewById(R.id.signupspinner);
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
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpActivity.this,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        register = (Button) findViewById(R.id.regbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etname.getText().toString();
                email = etemail.getText().toString();
                password = etpassword.getText().toString();
                phone = etphone.getText().toString();
                address = etaddress.getText().toString();
                dateofbirth=DOB.getText().toString();
                if (name.equals("")) {
                    etname.requestFocus();
                    etname.setError("Enter username");
                }else if (!isValidEmail(email)){
                    etemail.requestFocus();
                    etemail.setError("Enter valid email");
                }else if (password.isEmpty()){
                    etpassword.requestFocus();
                    etpassword.setError("Invalid password");
                }else if (!isValidMobile(phone)){
                    etphone.requestFocus();
                    etphone.setError("Invalid phone number");
                }else if (address.isEmpty()) {
                    etaddress.requestFocus();
                    etaddress.setError("Invalid address");
                }else if (dateofbirth.isEmpty()) {
                    DOB.requestFocus();
                    DOB.setError("Invalid date of birth");
                }else{
                    Intent intent=new Intent(getApplicationContext(), MainMapActivity.class);
                    startActivity(intent);
                }
//                if (name.trim().length() > 0 && email.trim().length() > 0 && password.trim().length() > 0 && phone.trim().length()>0 && address.length() >0  ){
//                    registerapicall(name, password, phone, email, address, bloodgroup,dateofbirth);
//                }
               // registerapicall();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DOB.setText(sdf.format(myCalendar.getTime()));
    }
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

//    private  void registerapicall() {
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String serverURL = API.registerUrl;
//        final StringRequest getRequest = new StringRequest(Request.Method.POST, serverURL,
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("Response", response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String str_status = jsonObject.getString("result");
//                            //String str_msg = jsonObject.getString("message");
//
//
//                            if (str_status.equals("fail")) {
//                                JSONObject Jsonobject = jsonObject.getJSONObject("data");
//                                String rname = Jsonobject.getString("register_name");
//                                String remail = Jsonobject.getString("register_email");
//                                String rpassword = Jsonobject.getString("register_password");
//                                String rmobile = Jsonobject.getString("register_mobile");
//                                String raddress = Jsonobject.getString("register_address");
//                                String rgroup = Jsonobject.getString("register_bg");
//                                String rdob = Jsonobject.getString("register_dob");
////                                //Navigating to the another activity
//                                Intent intent = new Intent(getApplicationContext(), MainMapActivity.class);
//
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(getApplicationContext(),"Error while Sign In", Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
//                        Log.d(TAG, String.valueOf(error));
//
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                //  params.put("imei", device_imei_no);
//                params.put("regname", name);
//                params.put("ownername", email);
//                params.put("phone", phone);
//                params.put("address", address);
//                params.put("location", password);
//                params.put("lat", dateofbirth);
////                params.put("lng",bloodgroup);
////                params.put("landmark", landmark);
////                params.put("working_fulltime",  radio_value);
////                params.put("city_id", str_IssueTypeId_post);
////                params.put("working_days", MAIN);
////                params.put("start_time", strtime);
////                params.put("end_time", endti);
//
//
//
//                Log.d("tag",params.toString());
//                return params;
//            }
//        };
//        queue.add(getRequest);
//
//    }
    }

