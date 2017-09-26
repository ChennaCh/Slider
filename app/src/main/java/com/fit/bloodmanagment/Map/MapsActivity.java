package com.fit.bloodmanagment.Map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fit.bloodmanagment.Activity.BloodBanks;
import com.fit.bloodmanagment.Activity.ContactUsActivity;
import com.fit.bloodmanagment.Activity.DonarActivity;
import com.fit.bloodmanagment.Activity.FeedBackActivity;
import com.fit.bloodmanagment.Activity.PrecautionsActivity;
import com.fit.bloodmanagment.Activity.ReceiverActivity;
import com.fit.bloodmanagment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.fit.bloodmanagment.Activity.AboutUsActivity;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private GPSTracker gps;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    View mapView;
    private Button navbtn;

    private boolean shouldLoadHomeFragOnBackPress = true;

    private static final String TAG_ABOUTUS = "AboutUs";
    private static final String TAG_DONAR = "Donar";
    private static final String TAG_RECEIVER = "Receiver";
    private static final String TAG_BLOODBANKS = "BloodBanks";
    private static final String TAG_PRECAUTIONS = "Precautions";
    private static final String TAG_CONTACTUS = "ContactUs";
    private static final String TAG_FEEDBACK = "Feedback";

    public static String CURRENT_TAG = TAG_ABOUTUS;
    int id;

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


        navbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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

}
