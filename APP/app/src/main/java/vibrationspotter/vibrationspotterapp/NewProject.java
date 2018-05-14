package vibrationspotter.vibrationspotterapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NewProject extends AppCompatActivity {

    private FusedLocationProviderClient mfusedLocationProviderclient;
    private boolean mLocationPermissionGranted = true;
    Location currentLocation;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDeviceLocation();

        setContentView(R.layout.activity_newproject);
        final EditText etProjectTitel = findViewById(R.id.etProjectTitel);
        final EditText etProjectDescription = findViewById(R.id.etProjectDescription);
        Button bAddProject = findViewById(R.id.bAddProject);

        bAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etProjectTitel.getText().toString().equals("")) {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();

                    String titel = etProjectTitel.getText().toString();
                    String beschrijving = etProjectDescription.getText().toString();

                    Map<String, String> projectgegevens = new HashMap<>();
                    projectgegevens.put("titel", titel);
                    projectgegevens.put("beschrijving", beschrijving);


                    int rc = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

                    if (rc == PackageManager.PERMISSION_GRANTED) {
                        LocationManager lm = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

                        LocationListener locationListener = new LocationListener() {

                            public void onLocationChanged(Location location) {
                                // Called when a new location is found by the network location provider.
                                Toast.makeText(getBaseContext(), "location is:" + location, Toast.LENGTH_LONG).show();
                            }

                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }

                            public void onProviderEnabled(String provider) {
                            }

                            public void onProviderDisabled(String provider) {
                            }
                        };

                        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        //currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        //Location location = getDeviceLocation();
                        Toast.makeText(getBaseContext(), "location is:" + currentLocation, Toast.LENGTH_LONG).show();
                        longitude = currentLocation.getLongitude();
                        latitude = currentLocation.getLatitude();
                    } else {
                        requestGPSPermission();
                    }

                    projectgegevens.put("longtitude", String.valueOf(longitude));
                    projectgegevens.put("latitude", String.valueOf(latitude));
                    projectgegevens.put("email", settings.getString("email", null));

                    final JSONObject jsonObject = new JSONObject(projectgegevens);
                    JSONArray jArray = new JSONArray();
                    jArray.put(jsonObject);

                    JsonArrayRequest inloggenRequest = new JsonArrayRequest(Request.Method.POST,
                            getString(R.string.url) + "Projecten/ToevoegenProjecten",
                            jArray,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("ToevoegenProjecten", response.toString());
                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("ToevoegenProjecten", "Error: " + error.toString() + ", " + error.getMessage());
                                }
                            }
                    );
                    VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(inloggenRequest, "Inloggen");

                } else Toast.makeText(getApplicationContext(), "Gelieve een Titel op te geven", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void requestGPSPermission() {
        Log.w("GPS Locatie", "GPS permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, permissions, 3);
        }
    }

    public Location getDeviceLocation() {
        mfusedLocationProviderclient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                final Task location = mfusedLocationProviderclient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            currentLocation = (Location) task.getResult();
                            Toast.makeText(NewProject.this, "lat: " + currentLocation.getLatitude() + ",long: " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(NewProject.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {

        }

        return currentLocation;
    }

/*    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getBaseContext(), "location is:" + location, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }*/
}
