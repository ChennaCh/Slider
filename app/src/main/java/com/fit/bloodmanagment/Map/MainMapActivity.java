package com.fit.bloodmanagment.Map;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fit.bloodmanagment.Activity.AboutUsActivity;
import com.fit.bloodmanagment.Activity.AddBloodCamp;
import com.fit.bloodmanagment.Activity.BloodBanksActivity;
import com.fit.bloodmanagment.Activity.DonarActivity;
import com.fit.bloodmanagment.Activity.FaqsActivity;
import com.fit.bloodmanagment.Activity.MyProfileActivity;
import com.fit.bloodmanagment.Activity.PrecautionsActivity;
import com.fit.bloodmanagment.Activity.PrivacyPolicyActivity;
import com.fit.bloodmanagment.Activity.UrgencyActivity;
import com.fit.bloodmanagment.Activity.ViewBloodActivity;
import com.fit.bloodmanagment.Activity.ViewNeedsActivity;
import com.fit.bloodmanagment.Adapter.DonorListAdapter;
import com.fit.bloodmanagment.Beans.DonorBean;
import com.fit.bloodmanagment.Beans.ImageHelperBean;
import com.fit.bloodmanagment.BuildConfig;
import com.fit.bloodmanagment.GooglePlaces.GetNearByDonors;
import com.fit.bloodmanagment.GooglePlaces.GetNearbyPlacesData;
import com.fit.bloodmanagment.Network.Conn;
import com.fit.bloodmanagment.Network.ConnectivityReceiver;
import com.fit.bloodmanagment.Network.MyApplication;
import com.fit.bloodmanagment.R;
import com.fit.bloodmanagment.SQliteDatabase.ImageDatabaseHelper;
import com.fit.bloodmanagment.UserProfile.SiginInActivity;
import com.fit.bloodmanagment.UserProfile.SignUpActivity;
import com.fit.bloodmanagment.Utils.API;
import com.fit.bloodmanagment.Utils.CameraUtility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.R.id.edit;
import static com.fit.bloodmanagment.Network.Conn.displayMobileDataSettingsDialog;

public class MainMapActivity extends FragmentActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener{
    private Boolean isFabOpen = false;
    TextView fab1,fab2,fab3,fab4,fab5,fab6,fab7,fab8;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private GoogleMap mMap;
    View mapView;
    private Button navbtn,loginbtn,signupbtn;
    public static final String IMAGE_ID = "IMG_ID";
    private final String TAG = "MainMapActivity";
    private Activity activity;
    ArrayList<DonorBean> donordata=new ArrayList<>();
    private ImageDatabaseHelper databaseHelper;
    int id;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    double latitude,longitude;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    EditText mainsearch;
    ImageView pharmacyimage,hospitalimage,fab,fableft,shareimage,rating,profilepic,searchimage,donormain,dadd,notificatio_bar;
    ProgressBar progressBar;
    ProgressDialog progressdialog;
    LinearLayout donorll,hospitalll,pharmacyll,fableftll;
    public  NavigationView navigationView;
    LatLng latLng;
    SharedPreferences shre;
    Bitmap thumbnail;
    MarkerOptions markerOptions;
    public static final String MyPREFERENCES = "MyPre" ;//file name
    public static final String MYLOCATIONPREF="MyLocationPrefereces";
    public static final String mylatlongkey="mylatlong";
    public static final String  key = "nameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        navbtn = (Button) findViewById(R.id.navigation_button);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header  = navigationView.getHeaderView(0);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainmap);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
//        mapprogressbar = (ProgressBar) findViewById(R.id.mapprogressbar);
//
//   mapprogressbar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        fableftll=(LinearLayout)findViewById(R.id.fableftll);
        pharmacyll=(LinearLayout)findViewById(R.id.pharmacyll);
        hospitalll=(LinearLayout)findViewById(R.id.hospitalll);
        donorll=(LinearLayout)findViewById(R.id.donorll);
        pharmacyimage=(ImageView) findViewById(R.id.pharmacyimage);
        hospitalimage=(ImageView) findViewById(R.id.hospitalsimage);
        searchimage = (ImageView) findViewById(R.id.searchimage);
        dadd = (ImageView) findViewById(R.id.dadd);
        notificatio_bar = (ImageView) findViewById(R.id.notificatio_bar);
        shareimage=(ImageView)findViewById(R.id.share_app);
        donormain = (ImageView)findViewById(R.id.donormain);
        loginbtn = (Button) header.findViewById(R.id.signin_btn);
        signupbtn = (Button) header.findViewById(R.id.signup_btn);
        profilepic = (ImageView)header.findViewById(R.id.navimageview);
        mainsearch = (EditText) findViewById(R.id.mainsearch);
        rating=(ImageView) findViewById(R.id.rating);
        fableft=(ImageView)findViewById(R.id.fableft);
        fab = (ImageView) findViewById(R.id.fabright);
        fab1 = (TextView) findViewById(R.id.fab1);
        fab2 = (TextView) findViewById(R.id.fab2);
        fab3 = (TextView) findViewById(R.id.fab3);
        fab4 = (TextView) findViewById(R.id.fab4);
        fab5 = (TextView) findViewById(R.id.fab5);
        fab6 = (TextView) findViewById(R.id.fab6);
        fab7 = (TextView) findViewById(R.id.fab7);
        fab8 = (TextView) findViewById(R.id.fab8);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);
        fab6.setOnClickListener(this);
        fab7.setOnClickListener(this);
        fab8.setOnClickListener(this);
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        checkConnection();
        if(ConnectivityReceiver.isConnected()==false){
            displayMobileDataSettingsDialog(this);
        }
        shre =  getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (shre.contains(key))
        {//save required image
            String u=shre.getString(key, "");
            thumbnail=decodeBase64(u);
            profilepic.setImageBitmap(thumbnail);
        }
        shre = getSharedPreferences("userdetails",MODE_PRIVATE);
        String loginuname = shre.getString("fullname",null);
        if(loginuname=="" || loginuname==null){
           // Toast.makeText(getApplicationContext(),"Login again",Toast.LENGTH_LONG).show();
            LinearLayout ll =(LinearLayout)header.findViewById(R.id.loginorsignup);
            ll.setVisibility(View.VISIBLE);
            LinearLayout ll2=(LinearLayout)header.findViewById(R.id.myloginusername);
            ll2.setVisibility(View.GONE);
            Menu menuNav=navigationView.getMenu();
            MenuItem nav_myprofile = menuNav.findItem(R.id.nav_myprofile);
            nav_myprofile.setVisible(false);
            MenuItem nav_logout = menuNav.findItem(R.id.nav_logout);
            nav_logout.setVisible(false);
            MenuItem nav_beadonor = menuNav.findItem(R.id.nav_donoradd);
            nav_beadonor.setVisible(true);

            //nav_donoradd
        }else{
            LinearLayout ll =(LinearLayout)header.findViewById(R.id.loginorsignup);
            ll.setVisibility(View.GONE);
            LinearLayout ll2=(LinearLayout)header.findViewById(R.id.myloginusername);
            ll2.setVisibility(View.VISIBLE);
            TextView username=(TextView)header.findViewById(R.id.username);
            username.setText(loginuname);
            Menu menuNav=navigationView.getMenu();
            MenuItem nav_myprofile = menuNav.findItem(R.id.nav_myprofile);
            nav_myprofile.setVisible(true);
            MenuItem nav_logout = menuNav.findItem(R.id.nav_logout);
            nav_logout.setVisible(true);
            MenuItem nav_beadonor = menuNav.findItem(R.id.nav_donoradd);
            nav_beadonor.setVisible(false);

        }
        shre =  getSharedPreferences(MYLOCATIONPREF, Context.MODE_PRIVATE);
        String location=shre.getString(mylatlongkey,String.valueOf(latLng));

        donormain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdonorsonmap();
            }
        });

        dadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

        notificatio_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UrgencyActivity.class);
                startActivity(i);
            }
        });

        mainsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String name=mainsearch.getText().toString().trim();
                    if(name.equals(""))
                    {
                        Toast.makeText(MainMapActivity.this, "Searchbox can't be empty", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                    Toast.makeText(MainMapActivity.this, "Results for "+name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),DonarActivity.class);
                    intent.putExtra("sq",name);
                    startActivity(intent);
                    return true;
                }
                }
                return false;
            }
        });

        searchimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=mainsearch.getText().toString().trim();
                if(name.equals(""))
                {
                    Toast.makeText(MainMapActivity.this, "Searchbox can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainMapActivity.this, "Results for "+name, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),DonarActivity.class);
                    i.putExtra("sq",name);
                    startActivity(i);
                }
            }
        });

        shareimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareapp = "\nLet me recommend you this application\n\n";
                    shareapp = shareapp + "https://play.google.com/store/apps/details?id=com.fit.bloodmanagement";
                    i.putExtra(Intent.EXTRA_TEXT, shareapp);
                    startActivity(Intent.createChooser(i, "Choose One"));
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(myAppLinkToMarket);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext()," unable to find application in playstore", Toast.LENGTH_LONG).show();
                    }
                }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMapActivity.this,SiginInActivity.class));
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMapActivity.this,SignUpActivity.class));
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

        fableftll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMapActivity.this,UrgencyActivity.class));
            }
        });

    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    //check google play services
    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
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
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        setPadding(mapView);
                        mMap.setMyLocationEnabled(true);


                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this application might request.
            // You can add here other case statements according to your requirement.
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainMapActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= CameraUtility.checkPermission(MainMapActivity.this);

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
            if (requestCode == SELECT_FILE){
                //onSelectFromGalleryResult(data);
                Bitmap bm=null;
                if (data != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    storeImage(bm);
                    profilepic.setImageBitmap(bm);

                }
            }

            else if (requestCode == REQUEST_CAMERA) {
                //onCaptureImageResult(data);
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                storeImage(bm);
                profilepic.setImageBitmap(bm);

            }
        }
    }
    private void storeImage(Bitmap thumbnail) {
        // Removing image saved earlier in shared prefernces
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
        createFolder();
        // this code is use to generate random number and add to file
        // name so that each file should be different
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";

        // set the file path
        // sdcard/PictureFolder/ is the folder created in create folder method
        String filePath = "/sdcard/PictureFolder/"+fname;
        // the rest of the code is for saving the file to filepath mentioned above
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

        //choose another format if PNG doesn't suit you
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bos);
        shre =  getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shre.edit();
        editor.putString(key,encodeTobase64(thumbnail));
        editor.commit();
        try {
            bos.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void createFolder()
    {
        // here PictureFolder is the folder name you can change it offcourse
        String RootDir = Environment.getExternalStorageDirectory()
                + File.separator + "PictureFolder";
        File RootFile = new File(RootDir);
        RootFile.mkdir();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(MainMapActivity.this, AboutUsActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_donoradd) {
            Intent intent = new Intent(MainMapActivity.this, SignUpActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_donor) {
            Intent intent = new Intent(MainMapActivity.this, DonarActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_bloodbanks) {
            Intent intent = new Intent(MainMapActivity.this, BloodBanksActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_precautions) {
            Intent intent = new Intent(MainMapActivity.this, PrecautionsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_myprofile) {
            Intent intent = new Intent(MainMapActivity.this, MyProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_request) {
            Intent intent = new Intent(MainMapActivity.this, UrgencyActivity.class);
            startActivity(intent);
        }else if(id ==R.id.nav_viewneeds){
            Intent intent = new Intent(MainMapActivity.this, ViewNeedsActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_faqs){
            Intent intent = new Intent(MainMapActivity.this, FaqsActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_privacypolicy){
            Intent intent = new Intent(MainMapActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_logout){
            Intent intent = new Intent(getApplicationContext(), MainMapActivity.class);
            getApplicationContext().getSharedPreferences("userdetails", 0).edit().clear().commit();
            startActivity(intent);
        }else if(id==R.id.nav_addbloodcamp){
            Intent intent = new Intent(MainMapActivity.this, AddBloodCamp.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_viewbloodcamp){
            Intent intent = new Intent(MainMapActivity.this, ViewBloodActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // mMap.setMapType(GoogleMap.);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                setPadding(mapView);
                mMap.setMyLocationEnabled(true);
                mMap.setTrafficEnabled(true);
                //mMap.setPadding(0, 0, 30, );
                //mMap.setPadding(left, top, right, bottom);
            }
        }
        else {
            buildGoogleApiClient();
            setPadding(mapView);
            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(true);
            // mMap.setPadding(0, 0, 30, 105);
        }


        pharmacyll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                if(ConnectivityReceiver.isConnected()==false){
                    checkConnection();
                    //displayMobileDataSettingsDialog(MainMapActivity.this);
                }
                   // progressdialog = new ProgressDialog(MainMapActivity.this,R.style.MyAlertDialogStyle);
                    String Pharmacy = "pharmacy";

                    // Log.d("onClick", "Button is Clicked");
                    mMap.clear();
                    if (mCurrLocationMarker != null) {
                        //  mCurrLocationMarker.remove();
//                        progressdialog.setMessage("loading");
//                        progressdialog.show();
                        String url = getUrl(latitude, longitude, Pharmacy);
                        Object[] DataTransfer = new Object[2];
                        DataTransfer[0] = mMap;
                        DataTransfer[1] = url;
                        Log.d("onClick", url);
                        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                        getNearbyPlacesData.execute(DataTransfer);
                       // progressdialog.dismiss();
                        Toast.makeText(MainMapActivity.this, "Nearby Pharmacies", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Log.e("ERROR","EXCEPTION");
                }


            }
        });
        hospitalll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()==false){
                    checkConnection();
                    //displayMobileDataSettingsDialog(MainMapActivity.this);
                }
                setProgress(v.VISIBLE);
                //ProgressDialog loading;
                //loading = ProgressDialog.show(MainMapActivity.this, "Uploading...", null,true,true);
                String Hospital="hospital";

                //Log.d("onClick", "Button is Clicked");
                mMap.clear();
                //mCurrLocationMarker = mMap.addMarker(markerOptions);;
                if (mCurrLocationMarker != null) {
//                    mCurrLocationMarker.remove();
                    String url = getUrl(latitude, longitude, Hospital);
                    Object[] DataTransfer = new Object[2];
                    DataTransfer[0] = mMap;
                    DataTransfer[1] = url;
                    Log.d("onClick", url);
                    GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                    getNearbyPlacesData.execute(DataTransfer);
                    //loading.dismiss();
                    setProgress(v.GONE);
                    Toast.makeText(MainMapActivity.this,"Nearby Hospitals", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public void setPadding(View mapView){
        mMap.setPadding(0,300,0,0);
        //left,top,right,bottom
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
         latLng = new LatLng(location.getLatitude(), location.getLongitude());
         markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
       // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        shre =  getSharedPreferences(MYLOCATIONPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shre.edit();
        editor.putString(mylatlongkey,String.valueOf(latLng));
        editor.commit();
        //Toast.makeText(MainMapActivity.this,"Your Current Location", Toast.LENGTH_LONG).show();
        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitude,longitude));
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        String bg="";
       // Toast.makeText(getApplicationContext(), "id"+id, Toast.LENGTH_SHORT).show();
        switch (id){
            case R.id.fabright:
                animateFAB();
                break;
            case R.id.fab1:
                bg="A+";
                break;
            case R.id.fab2:
                bg="A-";
                break;
            case R.id.fab3:
                bg="B+";
                break;
            case R.id.fab4:
                bg="B-";
                break;
            case R.id.fab5:
                bg="AB+";
                break;
            case R.id.fab6:
                bg="AB-";
                break;
            case R.id.fab7:
                bg="O+";
                break;
            case R.id.fab8:
                bg="O-";
                break;
        }
        if(id!=R.id.fabright) {
            Toast.makeText(MainMapActivity.this, "Results for " + bg, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), DonarActivity.class);
            i.putExtra("sq", bg);
            startActivity(i);
        }
    }

    public void animateFAB(){

        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab5.startAnimation(fab_close);
            fab6.startAnimation(fab_close);
            fab7.startAnimation(fab_close);
            fab8.startAnimation(fab_close);

            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            fab5.setClickable(false);
            fab6.setClickable(false);
            fab7.setClickable(false);
            fab8.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);

            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            fab5.setVisibility(View.VISIBLE);
            fab6.setVisibility(View.VISIBLE);
            fab7.setVisibility(View.VISIBLE);
            fab8.setVisibility(View.VISIBLE);


            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab5.startAnimation(fab_open);
            fab6.startAnimation(fab_open);
            fab7.startAnimation(fab_open);
            fab8.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            fab5.setClickable(true);
            fab6.setClickable(true);
            fab7.setClickable(true);
            fab8.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void showdonorsonmap()
    {
        String Pharmacy = "pharmacy";
        mMap.clear();
            listapi();
    }
    private void listapi() {
       // Toast.makeText(getApplicationContext(),"first",Toast.LENGTH_SHORT).show();

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

                                MarkerOptions markerOptions = new MarkerOptions();
                                Geocoder coder = new Geocoder(getApplicationContext());
                                List<Address> address1 = null;
                                Address location = null;
                                try {
                                    address1 = coder.getFromLocationName(address, 1);

                                    location=address1.get(0);

                                }
                                catch(Exception e)
                                {
                                    //e.printStackTrace();
                                }

                                if(location!=null) {
                                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                                    markerOptions.position(latLng);
                                    markerOptions.title(fullname + " : " + bloodgroup + " : " + mobile + " : " + address);
                                   // Toast.makeText(getApplicationContext(), "hi" + fullname + " : " + bloodgroup + " : " + mobile + " : " + address, Toast.LENGTH_SHORT).show();

                                    mMap.addMarker(markerOptions);
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                    //move map camera
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
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
                        //Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "NO Network Connection", Toast.LENGTH_SHORT).show();
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
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),message , Snackbar.LENGTH_LONG);
        // snackbar.show();
//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
