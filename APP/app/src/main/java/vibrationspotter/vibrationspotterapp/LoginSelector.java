package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginSelector extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginselector);

        Button schoolbutton = findViewById(R.id.scholenlogin);
        Button gebruikerslogin = findViewById(R.id.gebruikerslogin);

        schoolbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intetentSchool = new Intent(LoginSelector.super.getApplicationContext(),ScholenLoginSelector.class);
                startActivity(intetentSchool);
            }
        });

        gebruikerslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGebruiker = new Intent(LoginSelector.super.getApplicationContext(), GebruikerLogin.class);
                startActivity(intentGebruiker);
            }
        });

    }
}
