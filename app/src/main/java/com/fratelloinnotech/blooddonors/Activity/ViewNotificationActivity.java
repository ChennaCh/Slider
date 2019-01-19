package com.fratelloinnotech.blooddonors.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fratelloinnotech.blooddonors.Beans.Notifications;
import com.fratelloinnotech.blooddonors.R;
import com.fratelloinnotech.blooddonors.Utils.API;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewNotificationActivity extends AppCompatActivity {
RecyclerView recyclerView;
NotifyAdapter notifyAdapter;
    Toolbar toolbar;
    AdView mAdView;

    List<Notifications> data = new ArrayList<>();
    private String TAG = ViewNeedsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);

        MobileAds.initialize(this, "ca-app-pub-4682541119478126~4361374156");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.toolbar_viewneeds);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Notifications");
        recyclerView = (RecyclerView) findViewById(R.id.notifications);
            shownotify();
    }

    private void shownotify() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.shownotifications;
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("notis");
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String ntitle=c.getString("title");
                                String nmessage=c.getString("message");
                                String ndate=c.getString("pdate");
                                Notifications notifications = new Notifications();
                                notifications.setTitle(ntitle);
                                notifications.setMessage(nmessage);
                                notifications.setDate(ndate);
                                data.add(notifications);
                                notifyAdapter = new NotifyAdapter(data, getApplicationContext());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false));
                                recyclerView.setAdapter(notifyAdapter);

                            }
                        } catch (JSONException e) {
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
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        queue.add(getRequest);
    }

    public class NotifyAdapter extends RecyclerView.Adapter<ViewNotificationActivity.NotifyAdapter.MyViewHolder>{
        List<Notifications> data;
        Context viewNotificationActivity;
        public NotifyAdapter(List<Notifications> data, Context viewNotificationActivity) {
            this.data  = data;
            this.viewNotificationActivity = viewNotificationActivity;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent,false);
            return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tit.setText(data.get(position).getTitle());
            holder.msg.setText(data.get(position).getMessage());
            holder.date.setText(data.get(position).getDate());
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tit,msg,date;
            public MyViewHolder(View itemView) {
                super(itemView);
               tit = (TextView) itemView.findViewById(R.id.titles);
                msg = (TextView) itemView.findViewById(R.id.message);
                date = (TextView) itemView.findViewById(R.id.date);

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
