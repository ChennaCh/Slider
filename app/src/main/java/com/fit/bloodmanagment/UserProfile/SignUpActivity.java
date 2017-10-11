package com.fit.bloodmanagment.UserProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fit.bloodmanagment.Map.MapsActivity;
import com.fit.bloodmanagment.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity  {
    Toolbar toolbar;
    EditText  etname, etemail,etpassword,etre_password,etphone,etaddress,DOB;
    Button register;
    String name,email,password,repassward,phone,address,dateofbirth;
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
        etre_password = (EditText) findViewById(R.id.editrepwd);
        etphone = (EditText) findViewById(R.id.editphone);
        etaddress = (EditText) findViewById(R.id.editaddress);
        DOB = (EditText) findViewById(R.id.editText);

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
                repassward = etre_password.getText().toString();
                phone = etphone.getText().toString();
                address = etaddress.getText().toString();
                dateofbirth=DOB.getText().toString();
                if (!isValidName(name)) {
                    etname.setError("Enter username");
                }else if (!isValidEmail(email)){
                    etemail.setError("Enter valid email");
                }else if (password.isEmpty()){
                    etpassword.setError("Invalid password");
                }else if (repassward.isEmpty()){
                    etre_password.setError("Invalid reenter password");
                }else if (!isValidMobile(phone)){
                    etphone.setError("Invalid phone number");
                }else if (address.isEmpty()) {
                    etaddress.setError("Invalid address");
                }else if (dateofbirth.isEmpty()) {
                    DOB.setError("Invalid date of birth");
            }else{
                    Intent intent=new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DOB.setText(sdf.format(myCalendar.getTime()));
    }
    private boolean isValidName(String name) {

        name = "^[a-zA-Z\\s]{3,30}$";

        Pattern pattern = Pattern.compile(name);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
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
