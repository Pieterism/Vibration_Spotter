package vibrationspotter.vibrationspotterapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vibrationspotter.Models.Project;

public class ProjectActivity extends AppCompatActivity{

    String projectString;
    Project project;

    Gson gson;
    SharedPreferences settings;
    Map<String,?> sharedPreferences;
    private static final int METING = 555;
    private static final int QR = 777;

    TextView textTitel;
    TextView textGebruikersnaam;
    TextView textTime;
    TextView textLocation;
    TextView textAantalMetingen;
    ImageView imageviewProject;
    Button bViewMetingen;
    Button bAddMeting;
    Button bqrProject;
    Button bDeleteProject;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        gson = new Gson();
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = settings.getAll();

        projectString = getIntent().getStringExtra("project");
        project = gson.fromJson(projectString, Project.class);

        if(project.getIdProject() == 0) Toast.makeText(this, "ID =0", Toast.LENGTH_LONG).show();
        else Toast.makeText(this, "PROJECT IS TOT HIER GERAAKT!", Toast.LENGTH_LONG).show();

        textTitel = findViewById(R.id.textTitel);
        textGebruikersnaam = findViewById(R.id.textGebruikersnaam);
        textLocation = findViewById(R.id.textLocation);
        textAantalMetingen = findViewById(R.id.textAantalMetingen);
        bViewMetingen = findViewById(R.id.bViewMetingen);
        bAddMeting = findViewById(R.id.bAddMeting);
        bqrProject = findViewById(R.id.bqrProject);
        bDeleteProject = findViewById(R.id.bDeleteProject);

        textTitel.setText(project.getTitel());
        textGebruikersnaam.setText(sharedPreferences.get("email").toString());
        textTime.setText(project.getTimestamp());
        textLocation.setText(String.valueOf(project.getLatitude()) + " LA ," + String.valueOf(project.getLongtitude()) + " LO");
        //textAantalMetingen.setText("Nog te bepalen");

//        imageviewProject.setImageResource(R.drawable.logo);
        final Intent naar_mainactivity = new Intent(this, MainActivity.class);

        bViewMetingen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMetingenlijst = new Intent(getApplicationContext(), MetingKeuzeActivity.class);
                openMetingenlijst.putExtra("projectID", String.valueOf(project.getIdProject()));
                startActivity(openMetingenlijst);
            }
        });
        bAddMeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent naar_meter = new Intent(getApplicationContext(),NewMeting.class);
                startActivityForResult(naar_meter, METING);
            }
        });
        bqrProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent naar_QR = new Intent(getApplicationContext(), QR_hub.class);
                startActivityForResult(naar_QR, QR);
            }
        });
        bDeleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> inloggegevens = new HashMap<>();
                String idProject = "" +  project.getIdProject();
                inloggegevens.put("idProject", idProject );
                final JSONObject jsonObject = new JSONObject(inloggegevens);
                JSONArray jArray = new JSONArray();
                jArray.put(jsonObject);

                JsonArrayRequest inloggenRequest = new JsonArrayRequest(Request.Method.POST,
                        getString(R.string.url) + "Projecten/VerwijderenProjecten",
                        jArray,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("Verwijderen", response.toString());
                                startActivity((naar_mainactivity));
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Verwijderen", "Error: " + error.toString() + ", " + error.getMessage());
                            }
                        }
                );
                VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(inloggenRequest, "Verwijderen");



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null && resultCode == CommonStatusCodes.SUCCESS){
            switch(requestCode){
                case METING:

                    break;
                case QR:
                    break;
                default: Toast.makeText(getApplicationContext(), "Hoe kan er nu een andere requestcode toekomen?", Toast.LENGTH_LONG).show();
            }
        } else if(data == null){
            Toast.makeText(this, "DATA == NULL ??", Toast.LENGTH_LONG).show();
            Log.d("Error", "DATA ++ NULL ??");
        } else {
            switch(resultCode){
                case CommonStatusCodes.SIGN_IN_REQUIRED:
                    Toast.makeText(this, "Niet Ingelogd?", Toast.LENGTH_SHORT).show();
                    break;
                default: Toast.makeText(this, "Andere Resultcode?", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
