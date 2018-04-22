package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*-----
Activity die de gebruiker laat kiezen hoe hij wil inloggen. Of Registreren.
-----*/

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/aclonica.regular.ttf");
        //textView.setTypeface(typeface);
        //PIETER FIX PLZ

        TextView registreren = findViewById(R.id.link_signup);
        Button schoolbutton = findViewById(R.id.scholenlogin);
        Button gebruikerslogin = findViewById(R.id.gebruikerslogin);

        schoolbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intetentSchool = new Intent(Splash.super.getApplicationContext(),LeerkrachtLogin.class);
                startActivity(intetentSchool);
            }
        });

        gebruikerslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGebruiker = new Intent(Splash.super.getApplicationContext(), GebruikerLogin.class);
                startActivity(intentGebruiker);
            }
        });

        registreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistreren = new Intent(Splash.super.getApplicationContext(), Registreren.class);
                startActivity(intentRegistreren);
            }
        });


    }
}
