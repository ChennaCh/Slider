package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.Utils.CustomVolleyRequest;


public class MyProfileActivity extends Activity {
    //TextViews
    private TextView textViewName;
    private TextView textViewEmail;
    private NetworkImageView profilePhoto;
    String RESULT_OK="1";

    //Image Loader
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        //Initializing Views
        textViewName = (TextView) findViewById(R.id.myusername);
        textViewEmail = (TextView) findViewById(R.id.myemail);
        profilePhoto = (NetworkImageView) findViewById(R.id.myimageview);
       // Intent intent = getIntent();
        Bundle bundle=getIntent().getExtras();
        String profilename=bundle.getString("username");
        String profileemail=bundle.getString("email");
        String profileimage=bundle.getString("photo");


        textViewName.setText(profilename);
        textViewEmail.setText(profileemail);

        //Initializing image loader
            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();

            imageLoader.get(profileimage,
                    ImageLoader.getImageListener(profilePhoto,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));

//            //Loading image
            profilePhoto.setImageUrl(profileimage, imageLoader);

        // setResult(Integer.parseInt("RESULT_OK"),intent);

    }
}
