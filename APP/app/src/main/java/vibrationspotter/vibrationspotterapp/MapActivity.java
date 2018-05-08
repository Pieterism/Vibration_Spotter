package vibrationspotter.vibrationspotterapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vibrationspotter.Models.Project;

import static com.google.android.gms.location.places.Places.getGeoDataClient;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mfusedLocationProviderclient;
    private GoogleMap mMap;
    private AutoCompleteTextView mSearchtext;
    private ImageView mGps;
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;

    ArrayList<Project> projecten;
    Gson gson;
    SharedPreferences settings;
    Map<String, ?> sharedPreferences;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map ready", Toast.LENGTH_LONG).show();
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSearchtext = (AutoCompleteTextView) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_gps);

        addAllMetingenMarkers();
        getLocationPermission();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void init() {

        mGeoDataClient = getGeoDataClient(this, null);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, LAT_LNG_BOUNDS, null);

        mSearchtext.setAdapter(mPlaceAutocompleteAdapter);

        mSearchtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionid, KeyEvent keyEvent) {
                if (actionid == EditorInfo.IME_ACTION_SEARCH || actionid == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == keyEvent.ACTION_DOWN || keyEvent.getAction() == keyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override/*mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(this, this)
                    .build();

            mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);*/
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        hideSoftKeyboard();
    }

    //locatie zoeken op kaart
    private void geoLocate() {
        String searchString = mSearchtext.getText().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            Address address = list.get(0);

            Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
            init();

        }
    }

    //Markeer locaties van projecten huidige gebruiker op kaart
    private void addAllMetingenMarkersGebruiker() {
        //HIER MOET IK EEN PROJECT KRIJGEN WAARVAN IK LAT EN LONG NADIEN KAN OPVRAGEN
        gson = new Gson();

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = settings.getAll();

        String email = settings.getString("email", null);
        if (email != null) {
            Map<String, String> gegevens = new HashMap<>();
            gegevens.put("email", email);
            final JSONObject jsonObject = new JSONObject(gegevens);
            final JSONArray jArray = new JSONArray();
            jArray.put(jsonObject);

            JsonArrayRequest projectRequest = new JsonArrayRequest(Request.Method.POST,
                    getString(R.string.url) + "Projecten",
                    jArray,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Projecten", "GELUKT!");

                            Type type = new TypeToken<List<Project>>() {
                            }.getType();
                            projecten = gson.fromJson(response.toString(), type);

                            for (final Project p : projecten) {
                                LatLng coord = new LatLng(p.getLatitude(), p.getLongtitude());
                                MarkerOptions options = new MarkerOptions().position(coord).title(p.getTitel());
                                mMap.addMarker(options);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Projecten", "Error: " + error.toString() + ", " + error.getMessage());
                        }
                    }
            );
            VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(projectRequest, "Inloggen");
        }
    }

    //TODO
    //Markeer locaties van alle projecten op kaart
    private void addAllMetingenMarkers() {
        //HIER MOET IK EEN PROJECT KRIJGEN WAARVAN IK LAT EN LONG NADIEN KAN OPVRAGEN
        gson = new Gson();

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = settings.getAll();

        String email = settings.getString("email", null);
        if (email != null) {
            Map<String, String> gegevens = new HashMap<>();
            gegevens.put("email", email);
            final JSONObject jsonObject = new JSONObject(gegevens);
            final JSONArray jArray = new JSONArray();
            jArray.put(jsonObject);

            JsonArrayRequest projectRequest = new JsonArrayRequest(Request.Method.POST,
                    getString(R.string.url) + "Projecten",
                    jArray,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Projecten", "GELUKT!");

                            Type type = new TypeToken<List<Project>>() {
                            }.getType();
                            projecten = gson.fromJson(response.toString(), type);

                            for (final Project p : projecten) {
                                LatLng coord = new LatLng(p.getLatitude(), p.getLongtitude());
                                MarkerOptions options = new MarkerOptions().position(coord).title(p.getTitel());
                                mMap.addMarker(options);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Projecten", "Error: " + error.toString() + ", " + error.getMessage());
                        }
                    }
            );
            VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(projectRequest, "Inloggen");
        }
    }

    //Laatst gekende locatie van gsm opvragen
    private void getDeviceLocation() {
        mfusedLocationProviderclient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                final Task location = mfusedLocationProviderclient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "");
                            Toast.makeText(MapActivity.this, "lat: " + currentLocation.getLatitude() + ",long: " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MapActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {

        }
    }

    //kaart verplaatsen naar andere locatie + pin erop plaatsen
    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();
    }

    //permissies aanvragen
    private void getLocationPermission() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);

        }
    }

    //Checken of permissies toegestaan zijn
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    //verbergt android keyboard
    private void hideSoftKeyboard() {
        View view =this.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
