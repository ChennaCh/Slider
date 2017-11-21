package com.fit.bloodmanagment.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.fit.bloodmanagment.Activity.BloodBanksActivity;
import com.fit.bloodmanagment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Scanner;

import static com.fit.bloodmanagment.Map.MainMapActivity.MYLOCATIONPREF;
import static com.fit.bloodmanagment.Map.MainMapActivity.mylatlongkey;

public class BloodbanksMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences shre;
    Double clat,clang,alat,alang;
    Marker mCurrLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbanks_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bloodbanksmap);
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
        mMap = googleMap;
        shre = getSharedPreferences(MYLOCATIONPREF, Context.MODE_PRIVATE);
        String currentloc=shre.getString(mylatlongkey,null);
        //double currentlatlong = Double.parseDouble(currentloc);

        //double addresslatlong = Double.parseDouble(address);
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
            } else {
                scanner.next();
            }
        }
        LatLng latLng = new LatLng(clat,clang);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        shre=getSharedPreferences("ADDRESSLOCATION", Context.MODE_PRIVATE);
        String address=shre.getString("address",null);
        Scanner addressscanner = new Scanner(address);
        addressscanner.useDelimiter("[^\\d,]");
        while (addressscanner.hasNextDouble() || scanner.hasNext()) {
            if (addressscanner.hasNextDouble()) {
                if (alat == null) {
                    alat = addressscanner.nextDouble(); // First setting the latitude
                } else {
                    alang = addressscanner.nextDouble(); // Then longitude and stopping
                    break;
                }
            } else {
                addressscanner.next();
            }
        }

        LatLng addresslatlang=new LatLng(alat,alang);
        MarkerOptions addressmarkerOptions = new MarkerOptions();
        addressmarkerOptions.position(addresslatlang);
        addressmarkerOptions.title("Destination");
        addressmarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(addressmarkerOptions);



  //      GeocodingLocation locationAddress = new GeocodingLocation();
//        locationAddress.getAddressFromLocation(address,getApplicationContext(), new GeocoderHandler());
//        //LatLng path = new LatLng(locationAddress.);
//        Marker marker = mMap.addMarker(new MarkerOptions()
//                .position(path)
//                .draggable(true));
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            //latLongTV.setText(locationAddress);
        }
    }
    @Override
    public void onBackPressed() {
        Intent listintent = new Intent(BloodbanksMapActivity.this, BloodBanksActivity.class);
        startActivity(listintent);
    }

}
