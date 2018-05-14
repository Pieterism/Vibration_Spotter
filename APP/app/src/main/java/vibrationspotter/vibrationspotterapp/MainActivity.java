package vibrationspotter.vibrationspotterapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vibrationspotter.Models.Project;

/*-----
De activity die als eerste wordt opgestart
-----*/

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static String TAG = "MainActivity";
    private static final int QR = 555;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    ConstraintLayout clHomePage;
    LinearLayout llprojecten;
    ImageView imageView;
    TextView textName, aantalprojecten, aantalgebruikers, textgebruikers, textprojecten;
    SharedPreferences settings;
    Map<String, ?> sharedPreferences;
    View nav_header_main;
    FloatingActionButton fab;

    Gson gson;

    List<Project> projecten;

    /*
     * TODO: Mapfragment initMap()
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        clHomePage = findViewById(R.id.clhome_page);
        llprojecten = findViewById(R.id.llprojecten);

        imageView = findViewById(R.id.vbAfbeelding);

        nav_header_main = navigationView.getHeaderView(0);
        textName = nav_header_main.findViewById(R.id.textName);

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = settings.getAll();

        projecten = new ArrayList<>();
        gson = new Gson();

        projecten.clear();

        /*-----
        Controleren of er vorige keer al ingelogd was
        -----*/
        String naam;
        if (sharedPreferences.containsKey("email"))
            naam = sharedPreferences.get("email").toString();
        else naam = "";
        textName.setText(naam);

        fab = findViewById(R.id.fab_naarQRlezer);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent naarQR = new Intent(getApplicationContext(), QR_hub.class);
                startActivityForResult(naarQR, QR);
            }
        });

        aantalgebruikers = findViewById(R.id.UsersSizeText);
        aantalgebruikers.setText("Offline");
        aantalprojecten = findViewById(R.id.ProjectenSizeText);
        aantalprojecten.setText("Offline");
        textgebruikers = findViewById(R.id.text_gebruikers);
        textgebruikers.setVisibility(View.INVISIBLE);
        textprojecten = findViewById(R.id.text_projecten);
        textprojecten.setVisibility(View.INVISIBLE);

        JsonArrayRequest alleprojecten = new JsonArrayRequest(getString(R.string.url) + "Projecten/alleProjecten",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());

                        Type type = new TypeToken<List<Project>>() {
                        }.getType();
                        projecten = gson.fromJson(response.toString(), type);

                        aantalprojecten.setText(String.valueOf(projecten.size()));
                        textprojecten.setVisibility(View.VISIBLE);

                        getLocationPermission();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }
        );
        VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(alleprojecten, "alleProjecten");
        JsonArrayRequest getAantalGebruikers = new JsonArrayRequest(
                getString(R.string.url) + "Persoon/aantal",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Map<String, String>>>() {
                        }.getType();

                        List<Map<String, String>> gegevens = gson.fromJson(response.toString(), type);

                        aantalgebruikers.setText(gegevens.get(0).get("size"));
                        textgebruikers.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(getAantalGebruikers, "getaantalGebruikers");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_testMultimeting) {

            Intent stertTest = new Intent(getApplicationContext(), MetingSpotter.class);
            startActivity(stertTest);

            return true;
        } else if (id == R.id.action_about) {
            Intent naarAbout = new Intent(getApplicationContext(), About.class);
            startActivity(naarAbout);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Toast.makeText(getApplicationContext(), "HomePage", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_projecten) {

            if (sharedPreferences.containsKey("email")) {

                Intent activities = new Intent(getApplicationContext(), ProjectKeuzeActivity.class);
                startActivity(activities);

            } else
                Toast.makeText(getApplicationContext(), "Log in om je projecten te bekijken", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_meter) {
            Intent naar_meter = new Intent(MainActivity.this, Meter.class);
            startActivity(naar_meter);

        } else if (id == R.id.nav_login) {
            Intent naar_login = new Intent(MainActivity.this, Splash.class);
            startActivity((naar_login));

        } else if (id == R.id.nav_logout) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();

            editor.clear();
            editor.apply();

            recreate();

        } else if (id == R.id.nav_map) {
            Intent naar_map = new Intent(MainActivity.this, MapActivity.class);
            startActivity((naar_map));
        } else if (id == R.id.nav_newproject) {
            Intent naar_newproject = new Intent(MainActivity.this, NewProject.class);
            startActivity((naar_newproject));
        }

        return true;
    }

    /*-----
    Verijdelde switch die bepaalt of de vorige activity al dan niet geslaagd is en bepaalt
    welke die vorige activity was
    -----*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (requestCode == QR) {
                    String qrcode = data.getStringExtra("QR_code");
                    Toast.makeText(this, qrcode + " Main", Toast.LENGTH_SHORT).show();

                    Map<String, String> QRgegevens = new HashMap<>();
                    QRgegevens.put("QR", qrcode);

                    final JSONObject jsonObject = new JSONObject(QRgegevens);
                    JSONArray jArray = new JSONArray();
                    jArray.put(jsonObject);

                    JsonArrayRequest QRprojectrequest = new JsonArrayRequest(Request.Method.POST,
                            getString(R.string.url) + "Projecten/ProjectViaQR",
                            jArray,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("ProjectViaQR", response.toString());

                                    ArrayList<Project> projecten;

                                    Type type = new TypeToken<List<Project>>(){}.getType();
                                    projecten = gson.fromJson(response.toString(), type);

                                    String project = gson.toJson(projecten.get(0));
                                    System.out.println(project);
                                    Intent naar_project = new Intent(getApplicationContext(), ProjectActivity.class);
                                    naar_project.putExtra("project", project);
                                    naar_project.putExtra("authorised", false);
                                    startActivity(naar_project);
                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("ProjectViaQR", "Error: " + error.toString() + ", " + error.getMessage());
                                }
                            }
                    );
                    VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(QRprojectrequest, "QR_Request");
                }
            }
        } else {
            System.out.println("data null");
        }
    }

    //Controleert of de Google-services in orde zijn
    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog d = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            d.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //MAPFRAGMENT VIEW
    //----------------------------------------------------------------------------------------------
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 10f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mfusedLocationProviderclient;
    private GoogleMap mMap;

    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(this, "Map ready", Toast.LENGTH_LONG).show();
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
            mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            init();

        }
    }

    //Laatst gekende locatie van gsm opvragen
    public void getDeviceLocation() {
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
                            Toast.makeText(MainActivity.this, "lat: " + currentLocation.getLatitude() + ",long: " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {

        }
    }

    //TODO: NAVIGEREN NAAR CORRECTE METING
    public void init() {

        MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());
        mMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Project p = (Project)marker.getTag();

                String project = gson.toJson(p);
                Intent naar_project = new Intent(getApplicationContext(), ProjectActivity.class);
                naar_project.putExtra("project", project);
                naar_project.putExtra("viewer", true);
                startActivity(naar_project);
            }
        });


        for (final Project p : projecten) {
            LatLng coord = new LatLng(p.getLatitude(), p.getLongtitude());

            if (p.getType().equalsIgnoreCase("STEM")) {
                MarkerOptions options = new MarkerOptions().position(coord).title(p.getTitel()).snippet(String.valueOf(p.getIdProject()));
                mMap.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_mapmarker))).setTag(new Project(p));
            } else {
                MarkerOptions options = new MarkerOptions().position(coord).title(p.getTitel()).snippet(String.valueOf(p.getIdProject()));
                mMap.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_mapmarker_red))).setTag(new Project(p));
            }
        }
    }

    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MainActivity.this);
    }

    //permissies aanvragen
    public void getLocationPermission() {
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

    //kaart verplaatsen naar andere locatie + pin erop plaatsen
    public void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
    }


}

