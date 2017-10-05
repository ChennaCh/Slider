package com.fit.bloodmanagment.Map;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fit.bloodmanagment.Activity.BloodBanks;
import com.fit.bloodmanagment.Activity.ContactUsActivity;
import com.fit.bloodmanagment.Activity.DonarActivity;
import com.fit.bloodmanagment.Activity.FeedBackActivity;
import com.fit.bloodmanagment.Activity.PrecautionsActivity;
import com.fit.bloodmanagment.Activity.ReceiverActivity;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.UserProfile.SiginInActivity;
import com.fit.bloodmanagment.UserProfile.SignUpActivity;
import com.fit.bloodmanagment.Utils.CameraUtility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.fit.bloodmanagment.Activity.AboutUsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private GPSTracker gps;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    View mapView;
    private Button navbtn;
    private Button loginbtn,signupbtn;
    private ImageView profilepic;
    int id;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        navbtn = (Button) findViewById(R.id.navigation_button);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header  = navigationView.getHeaderView(0);

        loginbtn = (Button) header.findViewById(R.id.signin_btn);
        signupbtn = (Button) header.findViewById(R.id.signup_btn);
        profilepic = (ImageView)header.findViewById(R.id.imageView);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,SiginInActivity.class));
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,SignUpActivity.class));
            }
        });


        navbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showPictureDialog();
                selectImage();
            }

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CameraUtility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= CameraUtility.checkPermission(MapsActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profilepic.setImageBitmap(bm);
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profilepic.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(MapsActivity.this, AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_donar) {
            Intent intent = new Intent(MapsActivity.this, DonarActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_receiver) {
            Intent intent = new Intent(MapsActivity.this, ReceiverActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bloodbanks) {
            Intent intent = new Intent(MapsActivity.this, BloodBanks.class);
            startActivity(intent);

        } else if (id == R.id.nav_precautions) {
            Intent intent = new Intent(MapsActivity.this, PrecautionsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(MapsActivity.this, ContactUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(MapsActivity.this, FeedBackActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gps = new GPSTracker(MapsActivity.this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        LatLng mylocaiton = new LatLng(latitude,longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocaiton,15));
        mMap.setOnMyLocationChangeListener(null);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]  {
                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION );
            }
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(17).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        //currentlocation Pin Position Change by below code
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1200);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            layoutParams.setMargins(0, 1200, 30, 30);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
