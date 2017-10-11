package com.fit.bloodmanagment.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Adapter.BloodbankListAdapter;
import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.Map.MapsActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.API;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class BloodBanksActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private ObservableRecyclerView bloodbankrecyle;
    private BloodbankListAdapter bloodbankListAdapter;
    ProgressBar bloodbankprogress;
    private Activity activity;
    private String TAG = BloodBanksActivity.class.getSimpleName();
    ArrayList<BloodbankBean> bbdata=new ArrayList<>();
    private static String bburl = "http://www.fratelloinnotech.com/smec/getfaculty.php";
    private String fbranch;
    private String getbranch;
    Toolbar toolbar;
    ObservableScrollView mScrollView;
//    Display display = getWindowManager().getDefaultDisplay();
//    Point size = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_banks);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mScrollView = (ObservableScrollView) findViewById(R.id.scrollable);
        setTitle(getString(R.string.BloodBanks));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bloodbankprogress = (ProgressBar) findViewById(R.id.bloodbank_progress);
        bloodbankprogress.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        bloodbankrecyle = (ObservableRecyclerView) findViewById(R.id.bbrecyclerView);
        bloodbankrecyle.setScrollViewCallbacks(this);
//        new BloodbankAsyncFetch().execute();
        listapi();
    }

    private void listapi() {
        bloodbankprogress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.bburl;
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("result");

                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String id = c.getString("id");
                                String name = c.getString("name");
                                String pass = c.getString("password");
                                String phone = c.getString("mobile");
                                String email = c.getString("email");
                                String branch = c.getString("branch");
                                String gender = c.getString("gender");
                                String qualification = c.getString("qualification");
                                String about = c.getString("about");

//                        validation(name,pass);
                                bbdata.add(new BloodbankBean(id,name,pass,phone,email,branch,gender,qualification,about));

                                bloodbankListAdapter = new BloodbankListAdapter(BloodBanksActivity.this,bbdata);
                                bloodbankrecyle.setAdapter(bloodbankListAdapter);
                                bloodbankrecyle.setLayoutManager(new LinearLayoutManager(BloodBanksActivity.this));
                                bloodbankprogress.setVisibility(View.GONE);
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
                        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
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


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BloodBanksActivity.this, MapsActivity.class);
        startActivity(intent);
    }




}
