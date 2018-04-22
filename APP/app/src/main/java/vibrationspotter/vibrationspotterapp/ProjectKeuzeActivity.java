package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vibrationspotter.Custom_views.ProjectView;
import vibrationspotter.Models.Project;

/*-----
Deze activity maakt dynamisch objecten ProjectView aan, 1 per project van de persoon in de databank en plaatst ze in een scrollview
Wanneer de gebruiker op een ProjectView drukt wort hij doorverwezen naar de ProjectActivity
-----*/

public class ProjectKeuzeActivity extends AppCompatActivity {

    LinearLayout surface;
    ArrayList<Project> projecten;
    Gson gson;
    SharedPreferences settings;
    Map<String,?> sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_scrollview);

        surface = findViewById(R.id.surface);
        gson = new Gson();

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = settings.getAll();

        final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

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

                            Type type = new TypeToken<List<Project>>(){}.getType();
                            projecten = gson.fromJson(response.toString(), type);

                            for (final Project p : projecten) {
                                ProjectView projectView = new ProjectView(getApplicationContext(), p);
                                projectView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String pString = gson.toJson(p);
                                        Intent naar_project = new Intent(getApplicationContext(), ProjectActivity.class);
                                        naar_project.putExtra("project", pString);
                                        startActivity(naar_project);
                                    }
                                });
                                surface.addView(projectView, lp);
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
}
