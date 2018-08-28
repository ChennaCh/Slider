package com.fit.bloodmanagment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.fit.bloodmanagment.Network.ConnectivityReceiver;
import com.fit.bloodmanagment.Network.VolleySingleton;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.UserProfile.SignUpActivity;
import com.fit.bloodmanagment.Utils.API;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fit.bloodmanagment.Network.Conn.displayMobileDataSettingsDialog;

public class AddBloodBanksActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText name,mob,landnum,email,addre,city;
    Button addbbank;
    TextView err;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_banks);
        toolbar = (Toolbar) findViewById(R.id.signup_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.AddbBank));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        name = (EditText) findViewById(R.id.editbname);
        mob = (EditText) findViewById(R.id.editbmobile);
        landnum = (EditText) findViewById(R.id.editbland);
        email = (EditText) findViewById(R.id.editbemail);
        addre = (EditText) findViewById(R.id.editbaddress);
        city = (EditText) findViewById(R.id.editblocation);
        addbbank = (Button) findViewById(R.id.regbbtn);
        err = (TextView) findViewById(R.id.regberror);
        addbbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                err.setText("");
                String blname = name.getText().toString();
                String blmobile = mob.getText().toString();
                String blland = landnum.getText().toString();
                String blemail = email.getText().toString();
                String bladdres=addre.getText().toString();
                String blcity = city.getText().toString();
                if (blname.equals("")) {
                    err.setText("Please Enter Blood Bank Name");
                } else if (!isValidEmail(blemail)) {
                    // etemail.requestFocus();
                    err.setText("Please Enter valid email");
                } else if (!isValidMobile(blmobile)) {
                    // etphone.requestFocus();
                    err.setText("Please Enter Valid Phone Number");
//                } else if (!isNumberValid(blland)) {
//                    // etemail.requestFocus();
//                    err.setText("Please Enter Valid Land Number");
                }else if (bladdres.equals("")) {
                    //etaddress.requestFocus();
                    err.setText("Please Enter Address");
                }  else if (blcity.equals("")) {
                    // etcity.requestFocus();
                    err.setText("Please Enter Location or city");
                } else if (blname.trim().length() > 0 && blmobile.trim().length() > 0 && blland.trim().length() > 0 && blemail.trim().length() > 0 && bladdres.trim().length() > 0 && blcity.trim().length() > 0) {
                    if(ConnectivityReceiver.isConnected()==false){
                        //checkConnection();
                        displayMobileDataSettingsDialog(AddBloodBanksActivity.this);
                    }
                    addbloodbank();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error in Adding Blood Bank",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addbloodbank() {
        //calling url
        String serverurl = API.addbloodbankurl;
        //sending request to url for response Or Request Constructer with 4 parameters
        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");//result should be matched with url link response ie,{"result":"success"}
                    if (res.equals("success")) //array key
                    {
                        Toast.makeText(getApplicationContext(), "Blood Bank added successfully", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        mob.setText("");
                        landnum.setText("");
                        email.setText("");
                        addre.setText("");
                        city.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry!.. error in adding, we apologize", Toast.LENGTH_SHORT).show();
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
                String blname = name.getText().toString();
                String blmobile = mob.getText().toString();
                String blemail = email.getText().toString();
                String bladdres=addre.getText().toString();
                String blcity = city.getText().toString();
                String blland = landnum.getText().toString();

                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put("name", blname);
                data.put("mobile", blmobile);
                data.put("landline", blland);
                data.put("email", blemail);
                data.put("address", bladdres);
                data.put("city", blcity);
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
//    private boolean isNumberValid(String number){
//        return Pattern.matches("/^[0-9]\\d{2,4}-\\d{6,8}$/\n", number);
//    }

}
