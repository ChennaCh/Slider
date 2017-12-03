package com.fit.bloodmanagment.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.fit.bloodmanagment.Activity.BloodBanksActivity;
import com.fit.bloodmanagment.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Scanner;
import static com.fit.bloodmanagment.Map.MainMapActivity.MYLOCATIONPREF;
import static com.fit.bloodmanagment.Map.MainMapActivity.mylatlongkey;

public class BloodbanksMapActivity extends FragmentActivity implements OnMapReadyCallback{

   // private GoogleMap mMap;
    SharedPreferences shre;
    Double clat,clang,alat,alang;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    GoogleMap gmap;
    View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbanks_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bloodbanksmap);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // mMap.setMapType(GoogleMap.);
        shre = getSharedPreferences(MYLOCATIONPREF, Context.MODE_PRIVATE);
        String currentloc=shre.getString(mylatlongkey,null);
        Scanner scanner = new Scanner(currentloc);
        scanner.useDelimiter("[^\\d,]");
        while (scanner.hasNextDouble() || scanner.hasNext()) {
            if (scanner.hasNextDouble()) {
                if (clat == null) {
                    clat = scanner.nextDouble(); // First setting the latitude
                } else {
                    clang = scanner.nextDouble(); // Then longitude and stopping
                    break;
                }
                LatLng latLng = new LatLng(clat,clang);
//            googleMap.addMarker(new MarkerOptions().position(latLng)
//                .title("Current Position"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            } else {
                scanner.next();
            }
        }


//        shre=getSharedPreferences("ADDRESSLOCATION", Context.MODE_PRIVATE);
//        String address=shre.getString("address",null);
//        Scanner addressscanner = new Scanner(address);
//        addressscanner.useDelimiter("[^\\d,]");
//        while (addressscanner.hasNextDouble() || scanner.hasNext()) {
//            if (addressscanner.hasNextDouble()) {
//                if (alat == null) {
//                    alat = addressscanner.nextDouble(); // First setting the latitude
//                } else {
//                    alang = addressscanner.nextDouble(); // Then longitude and stopping
//                    break;
//                }
//            } else {
//                addressscanner.next();
//            }
//            LatLng addresslatlang=new LatLng(alat,alang);
//            MarkerOptions addressmarkerOptions = new MarkerOptions();
//            addressmarkerOptions.position(addresslatlang);
//            addressmarkerOptions.title("Destination");
//            addressmarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//            mCurrLocationMarker = googleMap.addMarker(addressmarkerOptions);
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(addresslatlang));
//            googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
//        }



    }
    public void setPadding(View mapView){
        gmap.setPadding(0,300,0,0);
        //left,top,right,bottom
    }
    @Override
    public void onBackPressed() {
        Intent listintent = new Intent(BloodbanksMapActivity.this, BloodBanksActivity.class);
        startActivity(listintent);
    }

}
