package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vibrationspotter.Models.Project;

public class LeerkrachtLogin extends AppCompatActivity{
    String email;
    private static final int QR = 555;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stemlogin);

        gson = new Gson();

        Button bLogin = findViewById(R.id.bglogin);
        Button naarQr = findViewById(R.id.naarQR);
        final EditText etusername = findViewById(R.id.leerkrachtlogintext);
        final EditText etpassword = findViewById(R.id.leerkrachtwachtwoordtext);

        naarQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qr_intent = new Intent(LeerkrachtLogin.this, QR_hub.class);
                startActivityForResult(qr_intent, QR);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    AlertDialog.Builder nietIngevuld = new AlertDialog.Builder(LeerkrachtLogin.this);
                    nietIngevuld.setMessage("Niet alle velden zijn ingevuld!")
                            .setNegativeButton("close", null)
                            .create()
                            .show();
                } else {
                    //Login passwoord checken
                    Map<String, String> inloggegevens = new HashMap<>();
                    inloggegevens.put("email", username);
                    inloggegevens.put("paswoord", password);
                    inloggegevens.put("type", "leerkracht");
                    final JSONObject jsonObject = new JSONObject(inloggegevens);
                    JSONArray jArray = new JSONArray();
                    jArray.put(jsonObject);

                    JsonArrayRequest inloggenRequest = new JsonArrayRequest(Request.Method.POST,
                            getString(R.string.url) + "Inloggen",
                            jArray,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("Inloggen", response.toString());
                                    if (response.equals("[{\"Inloggen\": Verkeerd_wachtwoord!!!}]")) {
                                        email = null;
                                    } else {
                                        try {
                                            email = jsonObject.get("email").toString();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = settings.edit();

                                    if (!response.toString().contains("Verkeerd")) {
                                        editor.putString("email", email);
                                        editor.putString("type", "leerkracht");
                                        editor.apply();
                                        Log.d("MyApp", email);
                                        //Sessie is aangemaakt
                                    } else {
                                        Log.d("MyApp", "verkeerd wachtwoord of gebruikersnaam");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("QR", "Error: " + error.toString() + ", " + error.getMessage());
                                }
                            }
                    );
                    VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(inloggenRequest, "Inloggen");
                }
            }
        });
    }

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
}