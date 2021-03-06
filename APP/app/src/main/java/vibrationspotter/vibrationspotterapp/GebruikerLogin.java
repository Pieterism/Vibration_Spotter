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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*-----
Klasse die geprogrammeerde logica achter de activity_spotterlogin bevat
-----*/

public class GebruikerLogin extends AppCompatActivity{
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotterlogin);

        final EditText etusername = findViewById(R.id.spotterlogintext);
        final EditText etpassword = findViewById(R.id.spotterwachtwoordtext);
        Button bLogin = findViewById(R.id.btn_login);

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
                    inloggegevens.put("type", "gebruiker");
                    final JSONObject jsonObject = new JSONObject(inloggegevens);
                    JSONArray jArray = new JSONArray();
                    jArray.put(jsonObject);

                    /*-----
                    Jwt-token nog niet geïmplementeerd
                    -----*/

                    //Testing JWT:
                    // We need a signing key, so we'll create one just for this example. Usually
                    // the key would be read from your application configuration instead.
              /*      Key key = MacProvider.generateKey();

                    String compactJws = Jwts.builder()
                            .setSubject("Joe")
                            .signWith(SignatureAlgorithm.HS512, key)
                            .compact();
*/

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
                                        editor.putString("type", "gebruiker");
                                        editor.apply();
                                        Log.d("MyApp",email);
                                        //Sessie is aangemaakt

                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    } else{
                                            Log.d("MyApp", "verkeerd wachtwoord of gebruikersnaam");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("QR","Error: " + error.toString() + ", " + error.getMessage());
                                    Toast.makeText(GebruikerLogin.this, "Login mislukt", Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                    VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(inloggenRequest, "Inloggen");

                }
            }
        });
    }
}
