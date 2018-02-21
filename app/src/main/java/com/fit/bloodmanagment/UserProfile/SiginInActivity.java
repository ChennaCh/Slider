package com.fit.bloodmanagment.UserProfile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Activity.MyProfileActivity;
import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.Network.ConnectivityReceiver;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.API;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;

import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fit.bloodmanagment.Network.Conn.displayMobileDataSettingsDialog;


public class SiginInActivity extends AppCompatActivity{
    Toolbar toolbar;
    EditText edtuser, edtpass,userInput;
    String strusername, strpassword,emailinput;
    TextView signup,setextview,forgetpwd;
    Button login;
    private static final String TAG = SiginInActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private SignInButton btnSignIn;
    //private GoogleApiClient mGoogleApiClient;
    ProgressBar progressbar;
    int flag = 0;
    private Activity activity;

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
       // btnSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        edtuser = (EditText) findViewById(R.id.editusername);
        edtpass = (EditText) findViewById(R.id.editpassword);
        signup=(TextView)findViewById(R.id.signin_signup);
        forgetpwd=(TextView)findViewById(R.id.tvforgetpwd);
        setextview =(TextView)findViewById(R.id.signinsettext);
        strusername=edtuser.getText().toString();
        strpassword=edtpass.getText().toString();
        login=(Button)findViewById(R.id.signin);
        setextview.setText("");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String loginusername=edtuser.getText().toString();
                final String loginpwd=edtpass.getText().toString();
                setextview.setText("");
                if(loginusername.equals(""))
                {
                    //Toast.makeText(getApplicationContext(),"username",Toast.LENGTH_LONG).show();
                    setextview.setText("Please enter UserName");
                    edtuser.setFocusable(true);

                }
                else if(loginpwd.equals(""))
                {
                    // Toast.makeText(getApplicationContext(),"username",Toast.LENGTH_LONG).show();

                    setextview.setText("Please enter Password");
                    edtpass.setFocusable(true);

                }
                else {
                    if(ConnectivityReceiver.isConnected()==false){
                        //checkConnection();
                        displayMobileDataSettingsDialog(SiginInActivity.this);
                    }
                    getCredentials();
                }

            }
        });
        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate alert dialog xml
                 showAlertDialog();
//                LayoutInflater li = LayoutInflater.from(SiginInActivity.this);
//                final View dialogView = li.inflate(R.layout.custom_dialog, null);
//                userInput = (EditText) dialogView.findViewById(R.id.et_input);
//                emailinput=userInput.getText().toString();
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SiginInActivity.this);
//                alertDialogBuilder.setView(dialogView);
//                // set dialog message
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        if (!isValidEmail(emailinput)||userInput.equals("")) {
//                                            userInput.setError("Enter valid email");
//                                            userInput.setFocusable(true);
//                                        }
//
//                                        else {
//                                            sendPasswordToMail();
//                                            Toast.makeText(getApplicationContext(), "Send Password successfully", Toast.LENGTH_LONG).show();
//                                        }
//
//                                    }
//                                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                // show it
//                alertDialog.show();
//
            }
       });
    }

    private void showAlertDialog() {
        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        userInput = (EditText) view.findViewById(R.id.et_input);

        final AlertDialog alertDialog = new AlertDialog.Builder(SiginInActivity.this)
                .setView(view)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        emailinput=userInput.getText().toString();
                        if (emailinput.equals("")) {
                            userInput.setError("Enter valid email");
                            userInput.setFocusable(true);
                            } else {
                                            sendPasswordToMail();
                                            Toast.makeText(getApplicationContext(), "Send Password successfully", Toast.LENGTH_LONG).show();
                                            dialog.cancel();
                                        }
                    }
                    });

                    Button buttonNegative = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
            buttonNegative.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            // Do something when button negative clicked
                            dialog.cancel();
                        }
                    });
                }
            });
        alertDialog.show();
    }

    private void sendPasswordToMail() {
        emailinput=userInput.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String uri = "http://" + address + "/myFolder/page.php?" + value1 + "=" + value2;
        //String uri = String.format("http://somesite.com/some_endpoint.php?param1=%1$s&param2=%2$s", num1, num2);
        String serverURL = String.format("http://www.fratelloinnotech.com/saveworld/sendpassword.php?email="+emailinput);
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("result");

                            for (int i = 0; i < contacts.length(); i++) {
                                //columns:  fullname,email,password,mobile,gender,address,bloodgroup,age,city,status
                                JSONObject c = contacts.getJSONObject(i);
                                String email = c.getString("email");
                                String password = c.getString("password");
                            }
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        queue.add(getRequest);
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    private void getCredentials() {
       // progressbar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.getalldonorsurl;
        final String loginusername=edtuser.getText().toString();
        final String loginpwd=edtpass.getText().toString();

        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String username="Guest";
                        String uname="";
                        try {
                            flag=0;
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("result");

                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                uname = c.getString("email");
                                String pwd = c.getString("password");
                                String mob =  c.getString("mobile");

                              if((loginusername.equals(mob)||loginusername.equals(uname))&& loginpwd.equals(pwd)){
                                 flag=1;
                                  username = c.getString("fullname");
                                  break;
                              }

                            }
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            e.printStackTrace();
                        }
                        if (flag == 1){
                            Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), MainMapActivity.class);
                            //intent.putExtra("user",loginusername);
                            SharedPreferences preferences = getSharedPreferences("userdetails",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                           // String username= edtuser.getText().toString();
                           // String pass = edtpass.getText().toString();
                            editor.putString("username",uname);
                            editor.putString("fullname",username);
                            editor.commit();

                            startActivity(intent);
                        }else {
//                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                            setextview.setText("Invalid Username and Password");
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Toast.makeText(activity, "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        queue.add(getRequest);
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
