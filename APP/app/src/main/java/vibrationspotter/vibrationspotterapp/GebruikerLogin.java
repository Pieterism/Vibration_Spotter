package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GebruikerLogin extends Activity{

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
                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if(username.equals("") || password.equals("")){
                    AlertDialog.Builder nietIngevuld = new AlertDialog.Builder(GebruikerLogin.this);
                    nietIngevuld.setMessage("Niet alle velden zijn ingevuld!")
                            .setNegativeButton("close",null)
                            .create()
                            .show();
                }
                else{

                }
            }
        });
    }
}
