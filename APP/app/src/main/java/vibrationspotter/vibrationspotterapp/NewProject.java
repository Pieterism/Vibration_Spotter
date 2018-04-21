package vibrationspotter.vibrationspotterapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewProject extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);
        final EditText etProjectTitel = findViewById(R.id.etProjectTitel);
        final EditText etProjectDescription = findViewById(R.id.etProjectDescription);
        Button bAddProject = findViewById(R.id.bAddProject);

        bAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();

                String titel = etProjectTitel.getText().toString();
                String beschrijving = etProjectDescription.getText().toString();

                double longitude = 0;
                double latitude = 0;

                Map<String, String> projectgegevens = new HashMap<>();
                projectgegevens.put("titel", titel);
                projectgegevens.put("beschrijving", beschrijving);

                LocationManager lm = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
                int rc = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                if (rc == PackageManager.PERMISSION_GRANTED) {
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                     longitude = location.getLongitude();
                     latitude = location.getLatitude();
                } else {
                    requestGPSPermission();
                }

                projectgegevens.put("longtitude", String.valueOf(longitude));
                projectgegevens.put("latitude", String.valueOf(latitude));
                projectgegevens.put("email", settings.getString("email",null));

                final JSONObject jsonObject = new JSONObject(projectgegevens);
                JSONArray jArray = new JSONArray();
                jArray.put(jsonObject);

                JsonArrayRequest inloggenRequest = new JsonArrayRequest(Request.Method.POST,
                        getString(R.string.url) + "ToevoegenProjecten",
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

             //   LocationManager lm = (LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE);


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


}
