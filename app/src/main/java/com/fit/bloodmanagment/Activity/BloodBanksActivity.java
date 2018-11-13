package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Adapter.BloodbankListAdapter;
import com.fit.bloodmanagment.Adapter.DonorListAdapter;
import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.API;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class BloodBanksActivity extends AppCompatActivity implements ObservableScrollViewCallbacks ,SearchView.OnQueryTextListener {

    private com.fit.bloodmanagment.Beans.ObservableRecyclerView bloodbankrecyle;
    private BloodbankListAdapter bloodbankListAdapter;
    ProgressBar bloodbankprogress;
    private Activity activity;
    private String TAG = BloodBanksActivity.class.getSimpleName();
    ArrayList<BloodbankBean> bbdata = new ArrayList<>();
    Toolbar toolbar;
    ObservableScrollView mScrollView;
    TextView errormsg;
    AdView mAdView;

//    Display display = getWindowManager().getDefaultDisplay();
//    Point size = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_banks);

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mScrollView = (ObservableScrollView) findViewById(R.id.scrollable);
        setTitle(getString(R.string.BloodBanks));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bloodbankprogress = (ProgressBar) findViewById(R.id.bloodbank_progress);
        bloodbankprogress.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        bloodbankrecyle = (com.fit.bloodmanagment.Beans.ObservableRecyclerView) findViewById(R.id.bbrecyclerView);
        bloodbankrecyle.setScrollViewCallbacks(this);
        errormsg = (TextView) findViewById(R.id.displayerror);
        errormsg.setText("No Blood Bank found :(");
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
                                String mobile = c.getString("mobile");
                                String landline = c.getString("landline");
                                String email = c.getString("email");
                                String address = c.getString("address");
                                String city = c.getString("city");
//                              validation(name,pass);
                                bbdata.add(new BloodbankBean(id, name, mobile, landline, email, address, city));
                                bloodbankListAdapter = new BloodbankListAdapter(BloodBanksActivity.this, bbdata);
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
                        Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
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

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(BloodBanksActivity.this, MainMapActivity.class);
//        startActivity(intent);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.search_icon, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.WHITE);
        //ab.setHomeAsUpIndicator(drawable);
        EditText editText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        editText.setHintTextColor(getResources().getColor(R.color.white));
        editText.setTextColor(getResources().getColor(R.color.white));
        searchView.setQueryHint("Search by location");
        //searchView.setBackgroundColor(Color.WHITE);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<BloodbankBean> newbbList = new ArrayList<>();
        for (BloodbankBean reqbb : bbdata) {
            String location = reqbb.getBcity().toLowerCase();
            if (location.contains(newText)) {
                newbbList.add(reqbb);
            }
        }
        if (newbbList.isEmpty()) {
            bloodbankrecyle.setVisibility(View.GONE);
            errormsg.setVisibility(View.VISIBLE);
            bloodbankprogress.setVisibility(View.GONE);
        } else {
            errormsg.setVisibility(View.GONE);
            bloodbankrecyle.setVisibility(View.VISIBLE);
            bloodbankListAdapter = new BloodbankListAdapter(BloodBanksActivity.this, newbbList);
            bloodbankListAdapter.setFilter(newbbList);
            bloodbankrecyle.setAdapter(bloodbankListAdapter);
            bloodbankrecyle.setLayoutManager(new LinearLayoutManager(BloodBanksActivity.this));
        }
            return true;
        }
        }
