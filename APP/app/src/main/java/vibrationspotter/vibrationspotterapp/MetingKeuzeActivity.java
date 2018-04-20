package vibrationspotter.vibrationspotterapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vibrationspotter.Custom_views.MetingView;
import vibrationspotter.Models.Meting;

public class MetingKeuzeActivity extends AppCompatActivity {

    LinearLayout surface;
    ArrayList<Meting> metingen;
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_scrollview);

        surface = findViewById(R.id.surface);
        gson = new Gson();

        System.out.println(getIntent().getStringExtra("projectID"));

        JsonArrayRequest alleMetingenVoorProjectRequest = new JsonArrayRequest(
                getString(R.string.url) + "Metingen/AlleMetingen/" + getIntent().getStringExtra("projectID"),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());

                        Type type = new TypeToken<List<Meting>>(){}.getType();
                        metingen = gson.fromJson(response.toString(), type);

                        for(final Meting m : metingen){
                            MetingView mView = new MetingView(getApplicationContext(),m);
                            mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String mString = gson.toJson(m);
                                    Intent naar_meting = new Intent(getApplicationContext(),MetingActivity.class);
                                    naar_meting.putExtra("meting", mString);
                                    startActivity(naar_meting);
                                }
                            });
                            surface.addView(mView);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }
        );

        VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(alleMetingenVoorProjectRequest, "MetingKeuzeActivity");
        
    }
}
