package cs4720.cs4720finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import cs4720.cs4720finalproject.Model.EasyTreasureChest;
import cs4720.cs4720finalproject.Model.Excalibur;
import cs4720.cs4720finalproject.Model.HardTreasureChest;
import cs4720.cs4720finalproject.Model.IronHelmet;
import cs4720.cs4720finalproject.Model.IronManHelmet;
import cs4720.cs4720finalproject.Model.Item;
import cs4720.cs4720finalproject.Model.MediumTreasureChest;
import cs4720.cs4720finalproject.Model.PlainStone;
import cs4720.cs4720finalproject.Model.TreasureChest;
import cs4720.cs4720finalproject.Model.TriviaQuiz;
import cs4720.cs4720finalproject.Rest.ApiClient;
import cs4720.cs4720finalproject.Rest.ApiInterface;
import cs4720.cs4720finalproject.Model.QuizQuestion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private ArrayList<TreasureChest> chestList = new ArrayList<TreasureChest>();
    private ArrayList<GroundOverlay> overlays = new ArrayList<GroundOverlay>();
    ArrayList<TreasureChest> closeChests = new ArrayList<TreasureChest>();
    private ArrayList<String> possibleEasyItems = new ArrayList<String>();
    private ArrayList<String> possibleMediumItems = new ArrayList<String>();
    private ArrayList<String> possibleHardItems = new ArrayList<String>();
    private static final int QUIZ_REQUEST = 1;
    private static final int PROFILE_REQUEST = 0;
    private static boolean first_launch = true;
    private Button acceptChallenge;


    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    Circle detectionRadius;
    private Boolean mRequestingLocationUpdates = false;
    private boolean firstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        acceptChallenge = (Button) findViewById(R.id.button);
        acceptChallenge.setVisibility(View.INVISIBLE);

        //Add all hard items to possible hard items here
        possibleHardItems.add("excalibur_item");
        possibleHardItems.add("iron_man_helmet_item");
        //Excalibur exc = new Excalibur();
        //possibleHardItems.add(exc);
        //IronManHelmet irmh = new IronManHelmet();
        //possibleHardItems.add(irmh);

        //Add all easy items to possible easy items here
        possibleEasyItems.add("plain_stone_item");
        //PlainStone pls = new PlainStone();
        //possibleEasyItems.add(pls);

        //Add all medium items to possible medium items here
        possibleMediumItems.add("iron_helmet_item");
        // IronHelmet irh = new IronHelmet();
        //possibleMediumItems.add(irh);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // Trying to check if this is the first launch of the activity. May not work
        if (first_launch == true) {
            mapFragment.getMapAsync(this);
            first_launch = false;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        mMap.moveCamera(zoom);
        mMap.setMinZoomPreference(14);

        //Test chest
        LatLng testLocation = new LatLng(38.031611, -78.510728);
        GroundOverlayOptions testChest = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.easy_treasure_chest)).position(testLocation, 50, 50);
        GroundOverlay testOverlay = googleMap.addGroundOverlay(testChest);
        overlays.add(testOverlay);
        EasyTreasureChest testChest1 = new EasyTreasureChest(testLocation);
        //int testItemIndex1 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
        //int testItemIndex2 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
        //int testItemIndex3 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
        testChest1.addItem(possibleEasyItems.get(0));
        testChest1.addItem(possibleEasyItems.get(0));
        testChest1.addItem(possibleEasyItems.get(0));
        chestList.add(testChest1);

        LatLng testLocation2 = new LatLng(38.029055, -78.509931);
        GroundOverlayOptions testChest2 = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.medium_treasure_chest)).position(testLocation2, 50, 50);
        GroundOverlay testOverlay2 = googleMap.addGroundOverlay(testChest2);
        overlays.add(testOverlay2);
        MediumTreasureChest testChest3 = new MediumTreasureChest(testLocation2);
        //int testItemIndex1 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
        //int testItemIndex2 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
        //int testItemIndex3 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
        testChest3.addItem(possibleMediumItems.get(0));
        testChest3.addItem(possibleMediumItems.get(0));
        testChest3.addItem(possibleMediumItems.get(0));
        chestList.add(testChest3);

        double maxNorth = 38.070591;
        double maxWest = -78.523636;
        double maxSouth = 38.009584;
        double maxEast = -78.446311;
        // This line randomizes the number of chests that spawn to be between 10 and 25 (up for change)
        int numberOfChests = ThreadLocalRandom.current().nextInt(50, 101);
        for (int i = 0; i < numberOfChests; i++) {
            Random r = new Random();
            Random r2 = new Random();
            double lat = maxSouth + (maxNorth - maxSouth) * r.nextDouble();
            double lon = maxEast + (maxWest - maxEast) * r.nextDouble();
            LatLng location = new LatLng(lat, lon);
            int chestType = ThreadLocalRandom.current().nextInt(1, 11);
            Log.d("Number", "" + chestType);
            if (chestType >= 1 && chestType < 7) {
                GroundOverlayOptions easyChest = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.easy_treasure_chest)).position(location, 50, 50);
                GroundOverlay overlay = googleMap.addGroundOverlay(easyChest);
                overlays.add(overlay);
                EasyTreasureChest chest1 = new EasyTreasureChest(location);
                //int itemIndex1 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
                //int itemIndex2 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
                //int itemIndex3 = ThreadLocalRandom.current().nextInt(0, possibleEasyItems.size() - 1);
                //chest1.addItem(possibleEasyItems.get(itemIndex1));
                //chest1.addItem(possibleEasyItems.get(itemIndex2));
                //chest1.addItem(possibleEasyItems.get(itemIndex3));
                chestList.add(chest1);
            } else if (chestType >= 7 && chestType < 10) {
                GroundOverlayOptions mediumChest = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.medium_treasure_chest)).position(location, 50, 50);
                GroundOverlay overlay = googleMap.addGroundOverlay(mediumChest);
                overlays.add(overlay);
                MediumTreasureChest chest2 = new MediumTreasureChest(location);
//                int itemIndex1 = ThreadLocalRandom.current().nextInt(0, possibleMediumItems.size() - 1);
//                int itemIndex2 = ThreadLocalRandom.current().nextInt(0, possibleMediumItems.size() - 1);
//                int itemIndex3 = ThreadLocalRandom.current().nextInt(0, possibleMediumItems.size() - 1);
//                chest2.addItem(possibleMediumItems.get(itemIndex1));
//                chest2.addItem(possibleMediumItems.get(itemIndex2));
//                chest2.addItem(possibleMediumItems.get(itemIndex3));
                chestList.add(chest2);
            } else if (chestType == 10) {
                GroundOverlayOptions hardChest = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.hard_treasure_chest)).position(location, 50, 50);
                GroundOverlay overlay = googleMap.addGroundOverlay(hardChest);
                overlays.add(overlay);
                HardTreasureChest chest3 = new HardTreasureChest(location);
//                int itemIndex1 = ThreadLocalRandom.current().nextInt(0, possibleHardItems.size() - 1);
//                int itemIndex2 = ThreadLocalRandom.current().nextInt(0, possibleHardItems.size() - 1);
//                int itemIndex3 = ThreadLocalRandom.current().nextInt(0, possibleHardItems.size() - 1);
//                chest3.addItem(possibleHardItems.get(itemIndex1));
//                chest3.addItem(possibleHardItems.get(itemIndex2));
//                chest3.addItem(possibleHardItems.get(itemIndex3));
                chestList.add(chest3);
            }


        }
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
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

        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mRequestingLocationUpdates = true;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        if (detectionRadius != null) {
            detectionRadius.remove();
        }
        CircleOptions options = new CircleOptions();
        LatLng ltLn = new LatLng(location.getLatitude(), location.getLongitude());
        options.center(ltLn);
        options.radius(75);
        options.fillColor(R.color.wallet_holo_blue_light);
        detectionRadius = mMap.addCircle(options);

        for (int i = 0; i < chestList.size() - 1; i++) {
            double tempDist = calculate_distance(detectionRadius.getCenter(), chestList.get(i).getLatLng());
            // May need to be changed
            if (tempDist <= 6 && !closeChests.contains(chestList.get(i))) {
                closeChests.add(chestList.get(i));
                chestList.remove(i);
            }
        }

        for(int i = 0; i < closeChests.size() - 1; i++) {
            if(calculate_distance(detectionRadius.getCenter(), closeChests.get(i).getLatLng()) > 6) {
                closeChests.remove(i);
            }
        }
        // Make accept challenge button visible when chests are nearby
        if ((closeChests.size()) > 0) {
            if (closeChests.get(0) instanceof EasyTreasureChest) {
                acceptChallenge.setText("Accept Easy Challenge");
            } else if (closeChests.get(0) instanceof MediumTreasureChest) {
                acceptChallenge.setText("Accept Medium Challenge");
            } else if (closeChests.get(0) instanceof HardTreasureChest) {
                acceptChallenge.setText("Accept Hard Challenge");
            }
            acceptChallenge.setVisibility(View.VISIBLE);
        } else if (closeChests.size() <= 0) {
            acceptChallenge.setVisibility(View.INVISIBLE);
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        if(firstLoad) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            firstLoad = false;
        }
    }

    public double calculate_distance(LatLng location, LatLng latLng) {
        double R = 6373;
        double lat1 = location.latitude;
        double lon1 = location.longitude;
        double lat2 = latLng.latitude;
        double lon2 = latLng.longitude;
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
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
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    public void startQuizActivity(View v) {
        Intent intent = new Intent(MapsActivity.this, TriviaQuizActivity.class);
        Bundle bundle = new Bundle();
        //Currently just for testing purposes. Will change to pass in the difficulty of the nearest treasure chest
        if (closeChests.get(0) instanceof EasyTreasureChest) {
            intent.putExtra("Quiz Difficulty", "Easy");
            bundle.putSerializable("chest", closeChests.get(0));
            intent.putExtra("sent chest", bundle);
        } else if (closeChests.get(0) instanceof MediumTreasureChest) {
            intent.putExtra("Quiz Difficulty", "Medium");
            bundle.putSerializable("chest", closeChests.get(0));
            intent.putExtra("sent chest", bundle);
        } else if (closeChests.get(0) instanceof HardTreasureChest) {
            intent.putExtra("Quiz Difficulty", "Medium");
            bundle.putSerializable("chest", closeChests.get(0));
            intent.putExtra("sent chest", bundle);
        }
        startActivityForResult(intent, QUIZ_REQUEST); //Might need to be startActivityForResult(intent, QUIZ_REQUEST)
    }

    public void openProfile(View v) {
        Intent intent = new Intent(MapsActivity.this, HomePageActivity.class);
        startActivityForResult(intent, PROFILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                for (int i = 0; i < overlays.size() - 1; i++) {
                    if (overlays.get(i).getPosition().equals(closeChests.get(0).getLatLng())) {
                        overlays.get(i).remove();
                        overlays.remove(i);
                    }
                }
                for (int i = 0; i < chestList.size() - 1; i++) {
                    if (chestList.get(i).getLatLng().equals(closeChests.get(0).getLatLng())) {
                        Log.d("Chest", "Chest removed");
                    }
                }
                closeChests.remove(0);
                acceptChallenge.setVisibility(View.INVISIBLE);
            }
        }
        else if(requestCode == PROFILE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("Profile Closed", "Success");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRequestingLocationUpdates) {
            stopLocationUpdates();
            mRequestingLocationUpdates = false;
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
                mRequestingLocationUpdates = true;
            }
            }
        }

    @Override
    public void onBackPressed() {
    }
    }
