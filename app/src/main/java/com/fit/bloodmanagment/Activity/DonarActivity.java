package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fit.bloodmanagment.Beans.DonorBean;
import com.fit.bloodmanagment.Map.BloodbanksMapActivity;
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


public class DonarActivity extends AppCompatActivity implements ObservableScrollViewCallbacks,SearchView.OnQueryTextListener {

    private ObservableRecyclerView donorrecycle;
    private DonorListAdapter donorListAdapter;
    ProgressBar donorprogress;
    private Activity activity;
    private String TAG = DonarActivity.class.getSimpleName();
    ArrayList<DonorBean> donordata=new ArrayList<>();
    Toolbar toolbar;
    String sq=null;
    ObservableScrollView mScrollView;

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
        sq = getIntent().getStringExtra("sq");
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
                               if(sq == null){
                                donorListAdapter = new DonorListAdapter(DonarActivity.this,donordata);
                                donorrecycle.setAdapter(donorListAdapter);
                                donorrecycle.setLayoutManager(new LinearLayoutManager(DonarActivity.this));
                                donorprogress.setVisibility(View.GONE);
                               }
                                else
                               {
                                   showdata(sq);
                               }
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DonarActivity.this, MainMapActivity.class);
        startActivity(intent);
    }

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
        searchView.setQueryHint("Search by location or BloodGroup");
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
        ArrayList<DonorBean> newList=new ArrayList<>();
        for(DonorBean reqdonors : donordata){
            String location= reqdonors.getDcity().toLowerCase();
            String bgroup = reqdonors.getDbloodgroup().toLowerCase();
            if(location.contains(newText.toLowerCase()) || bgroup.equalsIgnoreCase(newText.toLowerCase())){
              newList.add(reqdonors);
            }
        }
        donorListAdapter = new DonorListAdapter(DonarActivity.this,newList);
        donorListAdapter.setFilter(newList);
        donorrecycle.setAdapter(donorListAdapter);
        donorrecycle.setLayoutManager(new LinearLayoutManager(DonarActivity.this));
        return true;
    }

    public void showdata(String sq1)
    {
        sq1=sq1.toLowerCase();
        ArrayList<DonorBean> newList=new ArrayList<>();
        for(DonorBean reqdonors : donordata){
            String location= reqdonors.getDcity().toLowerCase();
            String bgroup = reqdonors.getDbloodgroup().toLowerCase();
            if(location.contains(sq1.toLowerCase()) || bgroup.equalsIgnoreCase(sq1.toLowerCase())){
                newList.add(reqdonors);
            }
        }
        donorListAdapter = new DonorListAdapter(DonarActivity.this,newList);
        donorListAdapter.setFilter(newList);
        donorrecycle.setAdapter(donorListAdapter);
        donorrecycle.setLayoutManager(new LinearLayoutManager(DonarActivity.this));
        donorprogress.setVisibility(View.GONE);
    }
}
