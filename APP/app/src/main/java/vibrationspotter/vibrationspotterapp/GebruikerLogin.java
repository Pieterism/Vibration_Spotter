package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.preference.PreferenceManager;
import android.content.SharedPreferences;

public class GebruikerLogin extends AppCompatActivity{
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gebruikerlogin);

        Button naarMeter = findViewById(R.id.naarMeterG);
        final EditText etusername = findViewById(R.id.g_username);
        final EditText etpassword = findViewById(R.id.g_password);
        Button bLogin = findViewById(R.id.bglogin);

        naarMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GebruikerLogin.super.getApplicationContext(),Meter.class);
                startActivity(intent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if(username.equals("") || password.equals("")){
                    AlertDialog.Builder nietIngevuld = new AlertDialog.Builder(GebruikerLogin.this);
                    nietIngevuld.setMessage("Niet alle velden zijn ingevuld!")
                            .setNegativeButton("close",null)
                            .create()
                            .show();
                }
                else{
                    //Login passwoord checken
                    Map<String,String> inloggegevens = new HashMap<>();
                    inloggegevens.put("email",username);
                    inloggegevens.put("paswoord",password);
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
                                    if(response.equals("[{\"Inloggen\": Verkeerd_wachtwoord!!!}]")){
                                        email = null;
                                    }
                                    else{
                                        try {
                                            email = jsonObject.get("email").toString();
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = settings.edit();

                         if(!response.toString().contains("Verkeerd")){
                          editor.putString("email", email);
                          editor.apply();
                          Log.d("MyApp",email);
                          //Sessie is aangemaakt
                    }
                    else{
                        Log.d("MyApp", "verkeerd wachtwoord of gebruikersnaam");
                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("QR","Error: " + error.toString() + ", " + error.getMessage());
                                }
                            }
                    );
                    VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(inloggenRequest, "Inloggen");

                }
            }
        });
    }
}
