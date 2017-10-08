package com.fit.bloodmanagment.UserProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.fit.bloodmanagment.Map.MapsActivity;
import com.fit.bloodmanagment.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;


public class SiginInActivity extends AppCompatActivity  {

    //implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener

    Toolbar toolbar;
    private SignInButton googles_btn;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_in);
        toolbar = (Toolbar) findViewById(R.id.loginpage_toolbar);
        googles_btn = (SignInButton) findViewById(R.id.sign_in_button);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Google Button onClick
//        googles_btn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();



            //Customizing googleplus button
//        googles_btn.setSize(SignInButton.SIZE_STANDARD);
//        googles_btn.setScopes(gso.getScopeArray());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//
//        switch (id){
//            case R.id.sign_in_button:
//                signIn();
//                break;
//        }
//
//    }

//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent,RC_SIGN_IN);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handlesignInResult(result);
//        }
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//
//            GoogleSignInResult result = opr.get();
//        }else {
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
//                    handlesignInResult(googleSignInResult);
//                }
//            });
//        }
//    }

//    private void handlesignInResult(GoogleSignInResult googleSignInResult) {
//
//        if (googleSignInResult.isSuccess()){
//
//            GoogleSignInAccount accnt = googleSignInResult.getSignInAccount();
//            String name = accnt.getDisplayName();
//            String mail = accnt.getEmail();
//            String profilephoto = accnt.getPhotoUrl().toString();
//            String googlename = "google";
//
//            Intent mapactivity = new Intent(this, MapsActivity.class);
//            mapactivity.putExtra("social",googlename);
//            mapactivity.putExtra("username",name);
//            mapactivity.putExtra("usermail",mail);
//            mapactivity.putExtra("prolepic",profilephoto);
//            startActivity(mapactivity);
//
//        }
//    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}
