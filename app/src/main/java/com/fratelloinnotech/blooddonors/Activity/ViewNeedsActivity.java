package com.fratelloinnotech.blooddonors.Activity;

import android.app.Activity;
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

import com.fratelloinnotech.blooddonors.Adapter.ViewNeedsListAdapter;

import com.fratelloinnotech.blooddonors.Beans.ViewNeedsBean;
import com.fratelloinnotech.blooddonors.R;
import com.fratelloinnotech.blooddonors.Utils.API;
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


public class ViewNeedsActivity  extends AppCompatActivity implements ObservableScrollViewCallbacks,SearchView.OnQueryTextListener {

    private com.fratelloinnotech.blooddonors.Beans.ObservableRecyclerView vneedrecycle;
    private ViewNeedsListAdapter vneedListAdapter;
    ProgressBar vneedprogress;
    private Activity activity;
    private String TAG = ViewNeedsActivity.class.getSimpleName();
    ArrayList<ViewNeedsBean> vneedsdata=new ArrayList<>();
    Toolbar toolbar;
    ObservableScrollView mScrollView;
    TextView errormsg;
    AdView mAdView;

//    Display display = getWindowManager().getDefaultDisplay();
//    Point size = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_needs);

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.toolbar_viewneeds);
        setSupportActionBar(toolbar);
        mScrollView = (ObservableScrollView) findViewById(R.id.scrollable);
        setTitle(getString(R.string.Viewneeds));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        vneedprogress = (ProgressBar) findViewById(R.id.viewneeds_progress);
        vneedprogress.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        vneedrecycle = (com.fratelloinnotech.blooddonors.Beans.ObservableRecyclerView) findViewById(R.id.viewneedsrecyclerView);
        vneedrecycle.setScrollViewCallbacks(this);
        errormsg = (TextView) findViewById(R.id.displayerror);
        errormsg.setText("No Needy found :(");

//        new BloodbankAsyncFetch().execute();
        listapi();
    }

    private void listapi() {
        vneedprogress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.getbloodneedsurl;
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("result");
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String vid=c.getString("id");
                                String vname = c.getString("name");
                                String vemail = c.getString("email");
                                String vmobile = c.getString("mobile");
                                String vaddress = c.getString("address");
                                String vbloodgroup = c.getString("bloodgroup");
                                String vcity=c.getString("city");
                                String vpurpose=c.getString("purpose");
                                String vduedate=c.getString("duedate");
                                // validation(name,pass);
                                vneedsdata.add(new ViewNeedsBean(vid,vname,vemail,vmobile,vaddress,vbloodgroup,vpurpose,vduedate,vcity));
                                vneedListAdapter = new ViewNeedsListAdapter(ViewNeedsActivity.this,vneedsdata);
                                vneedrecycle.setAdapter(vneedListAdapter);
                                vneedrecycle.setLayoutManager(new LinearLayoutManager(ViewNeedsActivity.this));
                                vneedprogress.setVisibility(View.GONE);
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
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(ViewNeedsActivity.this, MainMapActivity.class);
//        startActivity(intent);
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.search_icon, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.WHITE);
        //ab.setHomeAsUpIndicator(drawable);
        EditText editText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        editText.setHintTextColor(getResources().getColor(R.color.white));
        editText.setTextColor(getResources().getColor(R.color.white));
        searchView.setQueryHint("Search by blood group");
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
        newText=newText.toLowerCase();
        ArrayList<ViewNeedsBean> newbbList=new ArrayList<>();
        for(ViewNeedsBean reqbb : vneedsdata){
            String bg= reqbb.getNbloodgroup().toLowerCase();
            if(bg.contains(newText)){
                newbbList.add(reqbb);
            }
        }
        if (newbbList.isEmpty()) {
            vneedrecycle.setVisibility(View.GONE);
            errormsg.setVisibility(View.VISIBLE);
            vneedprogress.setVisibility(View.GONE);
        } else {
            errormsg.setVisibility(View.GONE);
            vneedrecycle.setVisibility(View.VISIBLE);
            vneedListAdapter = new ViewNeedsListAdapter(ViewNeedsActivity.this, newbbList);
            vneedListAdapter.setFilter(newbbList);
            vneedrecycle.setAdapter(vneedListAdapter);
            vneedrecycle.setLayoutManager(new LinearLayoutManager(ViewNeedsActivity.this));
        }
        return true;
    }
}
