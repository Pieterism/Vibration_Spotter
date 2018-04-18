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

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vibrationspotter.Custom_views.ProjectView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SELECTED_PIC = 1111;

    ScrollView svProjectview;
    ConstraintLayout clHomePage;
    LinearLayout llprojecten;
    ImageView imageView;

    int width;
    int height;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        */

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            svProjectview.setVisibility(View.INVISIBLE);
            clHomePage.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_projecten) {

            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            llprojecten.removeAllViews();

            clHomePage.setVisibility(View.INVISIBLE);
            svProjectview.setVisibility(View.VISIBLE);

            //Deel PJ:
      /*      SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();

            String email = settings.getString("email",null);
            if(email!=null){
                Map<String,String> gegevens = new HashMap<>();
                gegevens.put("email", email);
                final JSONObject jsonObject = new JSONObject(gegevens);
                final JSONArray jArray = new JSONArray();
                jArray.put(jsonObject);
                final Map<Integer,String> map = new HashMap<>();

                JsonArrayRequest inloggenRequest = new JsonArrayRequest(Request.Method.POST,
                        getString(R.string.url) + "Projecten",
                        jArray,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                String geg = response.toString();
                                geg = geg.substring(1, geg.length() - 1);
                                for(int i=0; i<response.length();i++) {
                                    try {


                                        int index1 = geg.indexOf("{");
                                        int index2 = geg.indexOf("}");
                                        String deel = geg.substring(index1, index2);
                                        JSONObject jsonObject2 = new JSONObject(deel);
                                        geg = geg.substring(index2 + 1);
                                        map.put(i, jsonObject2.getString("projecttitel"));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                  //  ProjectView projectView = new ProjectView(this, i);
                                  //  llprojecten.addView(projectView, lp);

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
                VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(inloggenRequest, "Inloggen");


                for(int i=1; i<=map.size(); i++) {
                    ProjectView projectView = new ProjectView(this, i);
                    llprojecten.addView(projectView, lp);
                }


            }

            //EINDE DEEL PJ

*/


            for(int i=1; i<=20; i++) {
                ProjectView projectView = new ProjectView(this, i);
                llprojecten.addView(projectView, lp);
            }

        } else if (id == R.id.nav_meter) {
            Intent naar_meter = new Intent(MainActivity.this, Meter.class);
            startActivity(naar_meter);

        } else if (id == R.id.nav_login) {
            Intent naar_login = new Intent(MainActivity.this, LoginSelector.class);
            startActivity((naar_login));

        } else if (id == R.id.nav_logout) {
            //Sessie van APP
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();

            editor.clear();
            editor.commit();

        } else if (id == R.id.nav_registreren) {
            Intent naar_registreren = new Intent(MainActivity.this, Registreren.class);
            startActivity(naar_registreren);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECTED_PIC);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            AlertDialog.Builder settings = new AlertDialog.Builder(this);
            settings.setMessage(imageString)
                    .setNegativeButton("close", null)
                    .create()
                    .show();

            StringRequest request = new StringRequest(Request.Method.POST,
                    getString(R.string.url) + "Foto",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(MainActivity.this, "Upload succes!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Upload ail :(", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams () throws AuthFailureError {
                            Map<String,String> parameters = new HashMap<>();
                            parameters.put("image",imageString);
                            return parameters;
                        }
            };

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
