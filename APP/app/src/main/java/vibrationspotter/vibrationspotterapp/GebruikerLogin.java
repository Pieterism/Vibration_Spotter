package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GebruikerLogin extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gebruikerlogin);

        Button naarMeter = findViewById(R.id.naarMeterG);

        naarMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GebruikerLogin.super.getApplicationContext(),Meter.class);
                startActivity(intent);
            }
        });

    }
}
