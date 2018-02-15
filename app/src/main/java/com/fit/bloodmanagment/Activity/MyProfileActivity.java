package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Adapter.DonorListAdapter;
import com.fit.bloodmanagment.Beans.DonorBean;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.UserProfile.SignUpActivity;
import com.fit.bloodmanagment.Utils.API;
import com.fit.bloodmanagment.Utils.CustomVolleyRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;


public class MyProfileActivity extends AppCompatActivity {
    //TextViews
    private EditText etViewName,etPhoneno,etAddress,etAge,etcity,etbloodgroup,etgender,etstatus;
    private TextView textViewEmail;
    private NetworkImageView profilePhoto;
    String fullname,email,phone,address,age,city,gender,bloodgroup,status;
     Toolbar toolbar;
    String RESULT_OK="1";
    Button updatebtn;
    //Image Loader
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        toolbar = (Toolbar) findViewById(R.id.myprofile_toolbar);
        setTitle(getString(R.string.Myprofile));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        //Initializing Views
        etViewName = (EditText) findViewById(R.id.myPuname);
        textViewEmail = (TextView) findViewById(R.id.myPmail);
        etPhoneno=(EditText) findViewById(R.id.myPmobileno);
        etAddress=(EditText)findViewById(R.id.myPaddress);
        etAge=(EditText)findViewById(R.id.myPage);
        etcity=(EditText)findViewById(R.id.myPcity);
        etbloodgroup=(EditText)findViewById(R.id.myPbloodgroup);
        etgender=(EditText)findViewById(R.id.myPgender);
        etstatus=(EditText)findViewById(R.id.myPstatus);
        //profilePhoto = (NetworkImageView) findViewById(R.id.myimageview);
         updatebtn=(Button)findViewById(R.id.myPsave);
        textViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Email cann't be edit",Toast.LENGTH_SHORT).show();
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = etViewName.getText().toString();
                email = textViewEmail.getText().toString();
                //password = etpassword.getText().toString();
                phone = etPhoneno.getText().toString();
                address = etAddress.getText().toString();
                age = etAge.getText().toString();
                city=etcity.getText().toString();
                bloodgroup=etbloodgroup.getText().toString();
                gender=etgender.getText().toString();
                status=etstatus.getText().toString();
                //setProgress(fullname, password, phone, email, address, gender, bloodgroup,age,city);
                if (fullname.trim().length() > 0 && email.trim().length() > 0 && gender.trim().length() > 0 && bloodgroup.trim().length() > 0 && phone.trim().length() > 0 && address.trim().length() > 0 && city.trim().length() > 0 && age.length() > 0 && status.length()>0) {

                    updateProfile(fullname, phone, email, address, gender, bloodgroup, age, city,status);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_LONG).show();
                }
            }
        });

        try {
            Bundle bundle = getIntent().getExtras();
            String profilename = bundle.getString("username");
            String profileemail = bundle.getString("email");

        }
        catch (Exception e){
            Log.e("Error","Exception");
        }
          getMyProfile();
    }



    private void getMyProfile() {
        SharedPreferences shre = getSharedPreferences("userdetails",MODE_PRIVATE);
        String username = shre.getString("username",null);
        String urmail=username.toString();
        //String urmail="loginuname";
       // donorprogress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String uri = "http://" + address + "/myFolder/page.php?" + value1 + "=" + value2;
        //String uri = String.format("http://somesite.com/some_endpoint.php?param1=%1$s&param2=%2$s", num1, num2);
        String serverURL = String.format("http://www.fratelloinnotech.com/saveworld/getprofile.php?email="+urmail);
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("result");

                            for (int i = 0; i < contacts.length(); i++) {
                                //columns:  fullname,email,password,mobile,gender,address,bloodgroup,age,city,status
                                JSONObject c = contacts.getJSONObject(i);
                                String fullname = c.getString("fullname");
                                String email = c.getString("email");
                                String password = c.getString("password");
                                String mobile = c.getString("mobile");
                                String gender = c.getString("gender");
                                String address = c.getString("address");
                                String bloodgroup = c.getString("bloodgroup");
                                String age=c.getString("age");
                                String city=c.getString("city");
                                String status=c.getString("status");
                                //String status=c.getString("status");
                                etViewName.setText(fullname);
                                textViewEmail.setText(email);
                                etPhoneno.setText(mobile);
                                etAddress.setText(address);
                                etAge.setText(age);
                                etcity.setText(city);
                                etbloodgroup.setText(bloodgroup);
                                etgender.setText(gender);
                                etstatus.setText(status);
//                        validation(name,pass);
//                                donordata.add(new DonorBean(id,fullname,email,password,mobile,gender,address,bloodgroup,age,city));
//                                donorListAdapter = new DonorListAdapter(DonarActivity.this,donordata);
//                                donorrecycle.setAdapter(donorListAdapter);
//                                donorrecycle.setLayoutManager(new LinearLayoutManager(DonarActivity.this));
//                                donorprogress.setVisibility(View.GONE);
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
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        queue.add(getRequest);
    }


    private void updateProfile(final String getname, final String getphone,
                                 final String getemail, final String getaddress, final String getgender, final String getbloodgroup, final String getage, final String getcity,final String getstatus) {
        class SendPostReqAsyncTask extends AsyncTask<Object, Object, Void> {
            @Override
            protected Void doInBackground(Object... strings) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("fullname", getname));
                //nameValuePairs.add(new BasicNameValuePair("password", getpassword));
                nameValuePairs.add(new BasicNameValuePair("mobile", getphone));
                nameValuePairs.add(new BasicNameValuePair("email", getemail));
                nameValuePairs.add(new BasicNameValuePair("address",getaddress));
                nameValuePairs.add(new BasicNameValuePair("gender", getgender));
                nameValuePairs.add(new BasicNameValuePair("bloodgroup", getbloodgroup));
                nameValuePairs.add(new BasicNameValuePair("age", getage));
                nameValuePairs.add(new BasicNameValuePair("city", getcity));
                nameValuePairs.add(new BasicNameValuePair("status",getstatus));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(API.updateProfile);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MyProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MyProfileActivity.class));
                finish();


            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(getname,getage, getphone, getemail, getaddress, getbloodgroup, getgender, getcity,getstatus);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
