package vibrationspotter.vibrationspotterapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Registreren extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreren);

        final EditText etVoornaam = findViewById(R.id.etVoornaam);
        final EditText etAchternaam = findViewById(R.id.etAchternaam);
        final EditText etGebruikersnaam = findViewById(R.id.etGebruikersnaam);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etWachtwoord = findViewById(R.id.etWachtwoord);
        final EditText etWachtwoord2 = findViewById(R.id.etWachtwoord2);
        Button bRegistreer = findViewById(R.id.bRegistreer);

        bRegistreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean ingevuld = true;
                if(!checkIngevuld(etVoornaam)) ingevuld = false;
                if(!checkIngevuld(etAchternaam)) ingevuld = false;
                if(!checkIngevuld(etGebruikersnaam)) ingevuld = false;
                if(!checkIngevuld(etEmail)) ingevuld = false;
                if(!checkIngevuld(etWachtwoord)) ingevuld = false;
                if(!checkIngevuld(etWachtwoord2)) ingevuld = false;

                if(!ingevuld) {
                    AlertDialog.Builder nietIngevuld = new AlertDialog.Builder(Registreren.this);
                    nietIngevuld.setMessage("Niet Ingevuld!")
                            .setNegativeButton("close",null)
                            .create()
                            .show();
                }
                else{
                    if(!etWachtwoord.getText().toString().equals(etWachtwoord2.getText().toString())){
                        AlertDialog.Builder wachtwoordVerkeerd = new AlertDialog.Builder(Registreren.this);
                        wachtwoordVerkeerd.setMessage("Wachtwoord verkeerd ingegeven.")
                                .setNegativeButton("close",null)
                                .create()
                                .show();
                    }
                    else{
                        Map<String,String> registratiegegevens = new HashMap<>();
                        registratiegegevens.put("voornaam",etVoornaam.getText().toString());
                        registratiegegevens.put("achternaam",etAchternaam.getText().toString());
                        registratiegegevens.put("gebruikersnaam",etGebruikersnaam.getText().toString());
                        registratiegegevens.put("emailadres",etEmail.getText().toString());
                        registratiegegevens.put("paswoord",etWachtwoord.getText().toString());
                        JSONObject jsonObject = new JSONObject(registratiegegevens);
                        JSONArray jArray = new JSONArray();
                        jArray.put(jsonObject);

                        JsonArrayRequest registreerRequest = new JsonArrayRequest(Request.Method.POST,
                                getString(R.string.url) + "Registreren",
                                jArray,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Log.d("Registreren", response.toString());
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("QR","Error: " + error.toString() + ", " + error.getMessage());
                                    }
                                }
                        );
                        VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(registreerRequest, "Registreren");
                    }
                }

            }
        });
    }

    private boolean checkIngevuld(EditText et) {
        return !et.getText().toString().equals("");
    }
}
