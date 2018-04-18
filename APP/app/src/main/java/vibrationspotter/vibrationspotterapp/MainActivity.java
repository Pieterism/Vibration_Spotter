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

import vibrationspotter.Custom_views.ProjectView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SELECTED_PIC = 1;

    ScrollView svProjectview;
    ConstraintLayout clHomePage;
    LinearLayout llprojecten;
    ImageView imageView;

    int width;
    int height;

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
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        imageView.setBackground(drawable);
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
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECTED_PIC);

            //Wat je hier schrijft wordt uitgevoerd waneer de share knop ingeduwd wordt (doet voorlopig nog niets :p)

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
