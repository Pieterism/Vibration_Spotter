package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vibrationspotter.Custom_views.ProjectView;
import vibrationspotter.Models.Project;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ScrollView svProjectview;
    ConstraintLayout clHomePage;
    LinearLayout llprojecten;
    ImageView imageView;
    TextView textName;

    int width;
    int height;

    Bitmap bitmap;

    SharedPreferences settings;
    View nav_header_main;

    Gson gson;

    List<Project> projecten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qr_intent = new Intent(MainActivity.this, QR_hub.class);
                startActivity(qr_intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        svProjectview = findViewById(R.id.svprojectview);
        svProjectview.setVisibility(View.INVISIBLE);

        clHomePage = findViewById(R.id.clhome_page);
        llprojecten = findViewById(R.id.llprojecten);

        imageView = findViewById(R.id.vbAfbeelding);

        nav_header_main = navigationView.getHeaderView(0);
        textName = nav_header_main.findViewById(R.id.textName);

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Map<String,?> sharedPreferences = settings.getAll();

        projecten = new ArrayList<>();
        gson = new Gson();


        projecten.clear();

        String naam;
        if(sharedPreferences.containsKey("email")) naam = sharedPreferences.get("email").toString();
        else naam = "nope";
        textName.setText(naam);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_settings) {

            AlertDialog.Builder settings = new AlertDialog.Builder(this);
            settings.setMessage("Naar Settings")
                    .setNegativeButton("close",null)
                    .create()
                    .show();

            return true;
        } else if (id == R.id.action_testMuyltimeting) {

            Intent stertTest = new Intent(getApplicationContext(), MetingSpotter.class);
            startActivity(stertTest);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            svProjectview.setVisibility(View.INVISIBLE);
            clHomePage.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_projecten) {

            final LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            llprojecten.removeAllViews();

            clHomePage.setVisibility(View.INVISIBLE);
            svProjectview.setVisibility(View.VISIBLE);

            //Deel PJ:
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();

            String email = settings.getString("email",null);
            if(email!=null){
                Map<String,String> gegevens = new HashMap<>();
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

                                Type type = new TypeToken<List<Project>>(){}.getType();
                                projecten = gson.fromJson(response.toString(), type);

                                for(final Project p: projecten){
                                    ProjectView projectView = new ProjectView(getApplicationContext(), p);
                                    projectView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String pString = gson.toJson(p);
                                            Intent naar_project = new Intent(getApplicationContext(),ProjectActivity.class);
                                            naar_project.putExtra("project",pString);
                                            startActivity(naar_project);
                                        }
                                    });
                                    llprojecten.addView(projectView, lp);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Projecten","Error: " + error.toString() + ", " + error.getMessage());
                            }
                        }
                );
                VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(projectRequest, "Inloggen");
            }

            //EINDE DEEL PJ

        } else if (id == R.id.nav_meter) {
            Intent naar_meter = new Intent(MainActivity.this, Meter.class);
            startActivity(naar_meter);

        } else if (id == R.id.nav_login) {
            Intent naar_login = new Intent(MainActivity.this, Splash.class);
            startActivity((naar_login));

        } else if (id == R.id.nav_logout) {
            //Sessie van APP
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();

            editor.clear();
            editor.apply();

            recreate();

        } else if (id == R.id.nav_about) {

        }

        else if (id == R.id.nav_newproject) {
            Intent naar_newproject = new Intent(MainActivity.this, NewProject.class);
            startActivity((naar_newproject));
        }

        return true;
    }
}
