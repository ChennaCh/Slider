package com.fit.bloodmanagment.UserProfile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.fit.bloodmanagment.Activity.DonarActivity;
import com.fit.bloodmanagment.Activity.MyProfileActivity;
import com.fit.bloodmanagment.Adapter.DonorListAdapter;
import com.fit.bloodmanagment.Beans.DonorBean;
import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.API;
import com.fit.bloodmanagment.Utils.HttpHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SiginInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Toolbar toolbar;
    EditText edtuser, edtpass;
    String strusername, strpassword;
    TextView signup,setextview;
    Button login;
    private static final String TAG = SiginInActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private SignInButton btnSignIn;
    private GoogleApiClient mGoogleApiClient;
    private ProgressBar mProgressDialog;
    int flag = 0;
    private Activity activity;
    //http://www.fratelloinnotech.com/saveworld/getdonors.php
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
        btnSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        edtuser = (EditText) findViewById(R.id.editusername);
        edtpass = (EditText) findViewById(R.id.editpassword);
        signup=(TextView)findViewById(R.id.signin_signup);
        setextview =(TextView)findViewById(R.id.signinsettext);
        login=(Button)findViewById(R.id.signin);
        strusername = edtuser.getText().toString();
        strpassword = edtpass.getText().toString();
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
                if (strusername.equals("")) {
//                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
//                    edtuser.setFocusable(true);
//                    edtuser.setError("Invalid Name");
                    setextview.setText("Invalid Username and password");

                } else if (!isValidPassword(strpassword)) {
//                    edtpass.setFocusable(true);
//                    edtpass.setError("Invalid password");}
                    setextview.setText("Invalid Username and password");
                }
//                else{
//                    Intent it=new Intent(getApplicationContext(), MainMapActivity.class);
//                    it.putExtra("email",username);
//                    startActivity(it);
//                }

                //new GetContacts1().execute();
               // listapi();


            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);
            Intent myIntent = new Intent(SiginInActivity.this, MyProfileActivity.class);
            myIntent.putExtra("username", personName);
            myIntent.putExtra("email",email);
            myIntent.putExtra("photo",personPhotoUrl);
            startActivity(myIntent);

//            txtName.setText(personName);
//            txtEmail.setText(email);
//            Glide.with(getApplicationContext()).load(personPhotoUrl)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfilePic);
//
//            updateUI(true);
//        } else {
//            // Signed out, show unauthenticated UI.
//            updateUI(false);
//        }
        }
    }

    private boolean isValidPassword(String Password) {
        String Password_pattren = "^+[0-9]{6}$";
        Pattern pattern = Pattern.compile(Password_pattren);
        Matcher matcher = pattern.matcher(Password);
        return matcher.matches();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
           // showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                   // hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }



//    private void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.hide();
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
