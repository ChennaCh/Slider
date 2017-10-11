package com.fit.bloodmanagment.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fit.bloodmanagment.Map.MapsActivity;
import com.fit.bloodmanagment.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SiginInActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtuser, edtpass;
    String username, password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_in);
        toolbar = (Toolbar) findViewById(R.id.signin_toolbar);
        setTitle(getString(R.string.Signin));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        edtuser = (EditText) findViewById(R.id.editusername);
        edtpass = (EditText) findViewById(R.id.editpassword);
        login=(Button)findViewById(R.id.btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edtuser.getText().toString();
                password = edtpass.getText().toString();
                if (username.equals("")) {
//                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    edtuser.setError("Invalid Name");
                } else if (!isValidPassword(password)){
                    edtpass.setError("Invalid password");}
                else{
                    Intent it=new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(it);
                }
            }
        });

    }
    private boolean isValidPassword(String Password) {
        String Password_pattren = "^+[0-9]{6}$";
        Pattern pattern = Pattern.compile(Password_pattren);
        Matcher matcher = pattern.matcher(Password);
        return matcher.matches();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
