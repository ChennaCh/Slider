package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fit.bloodmanagment.Adapter.BloodbankListAdapter;
import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.Map.BloodbanksMapActivity;
import com.fit.bloodmanagment.Map.MapsActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.HttpHandler;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BloodBanks extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private ObservableRecyclerView bloodbankrecyle;
    private BloodbankListAdapter bloodbankListAdapter;
    ProgressBar bloodbankprogress;
    private Activity activity;
    private String TAG = BloodBanks.class.getSimpleName();
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
        new BloodbankAsyncFetch().execute();
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


    private class BloodbankAsyncFetch extends AsyncTask<Void,Void,ArrayList<BloodbankBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bloodbankprogress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<BloodbankBean> doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(bburl);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
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
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Couldn't get json from server", Toast.LENGTH_SHORT).show();
            }

            return bbdata;
        }

        @Override
        protected void onPostExecute(final ArrayList<BloodbankBean> getBloodbankBean) {
            super.onPostExecute(getBloodbankBean);
            bloodbankprogress.setVisibility(View.GONE);


            if (getBloodbankBean != null){
                 bloodbankListAdapter = new BloodbankListAdapter(BloodBanks.this,getBloodbankBean);
                bloodbankrecyle.setAdapter(bloodbankListAdapter);
                bloodbankrecyle.setLayoutManager(new LinearLayoutManager(BloodBanks.this));
//                bloodbankrecyle.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        Intent intent=new Intent(BloodBanks.this, BloodbanksMapActivity.class);
//                        startActivity(intent);
//                        return true;
//                    }
//                });
//                facultyfeedBackAdapter = new FeedBackAdapter(FacultyViewFeedback.this,facgetFeedbackBeen);
//                facultyrecyle.setAdapter(facultyfeedBackAdapter);
//                facultyrecyle.setLayoutManager(new LinearLayoutManager(FacultyViewFeedback.this));
//                bloodbankrecyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
//                        Toast.makeText(ViewFacultyActivity.this, "Toast "+position, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ViewFacultyActivity.this,FacultyDetail.class);
//                        GetFacultyBean  b = getFacultyBeen.get(position);
//                        ArrayList<String> values = new ArrayList<String>();
//
//                        values.add(b.getGname());
//                        values.add(b.getGmobile());
//                        values.add(b.getGbranch());
//                        values.add(b.getGqualification());
//                        values.add(b.getGabout());
//                        values.add(b.getGemail());
//                        values.add(b.getId());
//                        intent.putStringArrayListExtra("values",values);
//                        startActivity(intent);
//                    }
//                });
//            } else {
//
//                Toast.makeText(activity, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
//
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
        Intent intent = new Intent(BloodBanks.this, MapsActivity.class);
        startActivity(intent);
    }
}
