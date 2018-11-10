package com.fit.bloodmanagment.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Adapter.BloodbankListAdapter;
import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.Beans.ObservableRecyclerView;
import com.fit.bloodmanagment.Beans.ViewBloodCamp;
import com.fit.bloodmanagment.Map.MainMapActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.API;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewBloodActivity extends AppCompatActivity implements ObservableScrollViewCallbacks,SearchView.OnQueryTextListener {
    Toolbar toolbar;
    ObservableRecyclerView recyclerView;
    ViewBloodAdapter viewBloodAdapter;
    List<ViewBloodCamp> data = new ArrayList<>();
    private String TAG = ViewBloodActivity.class.getSimpleName();
    ProgressBar progressBar;
    TextView errormsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood);
        toolbar = (Toolbar) findViewById(R.id.viewcamp_toolbar);
        setTitle("Blood Donation Camps");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        recyclerView = (ObservableRecyclerView) findViewById(R.id.viewcamps);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setEmptyView(errormsg);
        errormsg = (TextView) findViewById(R.id.displayerror);
        errormsg.setText("No Location found :(");

        getshops();
    }

    private void getshops() {
        data.clear();
        //request for getting data
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverurl = API.viewbloodcampurl;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("camps");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String sorgname = jsonObject1.getString("orgname");
                        String svenname = jsonObject1.getString("venue");
                        String svaddress = jsonObject1.getString("address");
                        String svcity = jsonObject1.getString("city");
                        String svmobile = jsonObject1.getString("mobile");
                        String svemail = jsonObject1.getString("email");
                        String svdescription = jsonObject1.getString("description");
                        String svdate = jsonObject1.getString("cdate");
                        String svdtime = jsonObject1.getString("ctime");


                        ViewBloodCamp viewcamp = new ViewBloodCamp();
                        viewcamp.setOrganisername(sorgname);
                        viewcamp.setVenuename(svenname);
                        viewcamp.setAddress(svaddress);
                        viewcamp.setCity(svcity);
                        viewcamp.setMobile(svmobile);
                        viewcamp.setEmail(svemail);
                        viewcamp.setDescript(svdescription);
                        viewcamp.setDate(svdate);
                        viewcamp.setTime(svdtime);

                        data.add(viewcamp);
                    }
                    viewBloodAdapter = new ViewBloodAdapter(data, getApplicationContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(viewBloodAdapter);
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_LONG).show();
                VolleyLog.d("Main", "Error: " + error.getMessage());
                Log.d("Main", "" + error.getMessage() + "," + error.toString());

            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                return null;
            }

        };
        progressBar.setVisibility(View.VISIBLE);
        queue.add(stringRequest);
    }
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(ViewBloodActivity.this, MainMapActivity.class);
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
        String text = newText.toLowerCase();
        if(text.length()==0){
            errormsg.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getshops();
        }
        else{
            viewBloodAdapter.filter(text);
        }
        return true;
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


    public class ViewBloodAdapter extends RecyclerView.Adapter<ViewBloodActivity.ViewBloodAdapter.MyViewHolder> {
        List<ViewBloodCamp> data;
        Context context;


        public ViewBloodAdapter(List<ViewBloodCamp> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @NonNull
        @Override

        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campviewlayout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            holder.orga.setText(data.get(position).getOrganisername());
            holder.vename.setText(data.get(position).getVenuename());
            holder.veaddress.setText(data.get(position).getAddress());
            holder.vecity.setText(data.get(position).getCity());
            holder.vedes.setText(data.get(position).getDescript());
            holder.vedate.setText(data.get(position).getDate());
            holder.vetime.setText(data.get(position).getTime());
            holder.vemobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mobnum = data.get(position).getMobile();
                    Toast.makeText(getApplicationContext(),mobnum,Toast.LENGTH_SHORT).show();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+mobnum));
                    if (ActivityCompat.checkSelfPermission(ViewBloodActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                }
            });
            holder.veemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String mails =  data.get(position).getEmail();
                     //   Toast.makeText(context, "mail."+mails, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Intent.ACTION_SENDTO);
                        i.setType("message/rfc822");
                        i.setData(Uri.parse("mailto:"+ mails));
                        // i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Urgent Requirement of Blood");
                        i.putExtra(Intent.EXTRA_TEXT   , "hi, Please send the complete details about blood avaible status");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void filter(String text) {
            List<ViewBloodCamp> filterdNames = new ArrayList<>();
            //looping through existing elements
            for (ViewBloodCamp s: data ) {
                //if the existing elements contains the search input
                if (s.getCity().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }
            if(filterdNames.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                errormsg.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }else {
                errormsg.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                //calling a method of the adapter class and passing the filtered list
                viewBloodAdapter.filterList(filterdNames);
                viewBloodAdapter = new ViewBloodAdapter(data, getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(viewBloodAdapter);
            }
        }
        //This method will filter the list
        //here we are passing the filtered data
        //and assigning it to the list with notifydatasetchanged method
        public void filterList(List<ViewBloodCamp> filterdNames) {
            this.data = filterdNames;
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView orga, vename, veaddress, vecity, vedate, vetime,vedes;
            ImageView vemobile, veemail;

            MyViewHolder(View view) {
                super(view);
                orga = (TextView) view.findViewById(R.id.oname);
                vename = (TextView) view.findViewById(R.id.vname);
                veaddress = (TextView) view.findViewById(R.id.vaddress);
                vecity = (TextView) view.findViewById(R.id.vcity);
                vedate = (TextView) view.findViewById(R.id.vdate);
                vetime = (TextView) view.findViewById(R.id.vtime);
                vemobile = (ImageView) view.findViewById(R.id.vcall);
                veemail = (ImageView) view.findViewById(R.id.vemail);
                vedes = (TextView) view.findViewById(R.id.vdescr);

            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
