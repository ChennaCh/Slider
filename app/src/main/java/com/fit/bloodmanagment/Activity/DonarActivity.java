package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.fit.bloodmanagment.Adapter.DonorListAdapter;
import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.Beans.DonorBean;
import com.fit.bloodmanagment.Map.MainMapActivity;
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


public class DonarActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private ObservableRecyclerView donorrecycle;
    private DonorListAdapter donorListAdapter;
    ProgressBar donorprogress;
    private Activity activity;
    private String TAG = DonarActivity.class.getSimpleName();
    ArrayList<DonorBean> donordata=new ArrayList<>();
    Toolbar toolbar;
    ObservableScrollView mScrollView;
//    Display display = getWindowManager().getDefaultDisplay();
//    Point size = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_donor);
        setSupportActionBar(toolbar);
        mScrollView = (ObservableScrollView) findViewById(R.id.scrollable);
        setTitle(getString(R.string.Donors));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        donorprogress = (ProgressBar) findViewById(R.id.donor_progress);
        donorprogress.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        donorrecycle = (ObservableRecyclerView) findViewById(R.id.donorrecyclerView);
        donorrecycle.setScrollViewCallbacks(this);
//        new BloodbankAsyncFetch().execute();
        listapi();
    }

    private void listapi() {
        donorprogress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.getalldonorsurl;
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("result");

                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String id=c.getString("id");
                                String fullname = c.getString("fullname");
                                String email = c.getString("email");
                                String password = c.getString("password");
                                String mobile = c.getString("mobile");
                                String gender = c.getString("gender");
                                String address = c.getString("address");
                                String bloodgroup = c.getString("bloodgroup");
                                String age=c.getString("age");
                                String city=c.getString("city");
//                        validation(name,pass);
                                donordata.add(new DonorBean(id,fullname,email,password,mobile,gender,address,bloodgroup,age,city));
                                donorListAdapter = new DonorListAdapter(DonarActivity.this,donordata);
                                donorrecycle.setAdapter(donorListAdapter);
                                donorrecycle.setLayoutManager(new LinearLayoutManager(DonarActivity.this));
                                donorprogress.setVisibility(View.GONE);
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
        Intent intent = new Intent(DonarActivity.this, MainMapActivity.class);
        startActivity(intent);
    }
}
