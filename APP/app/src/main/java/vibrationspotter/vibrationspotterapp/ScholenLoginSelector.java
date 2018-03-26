package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScholenLoginSelector extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholenloginselector);

        Button leerkracht = findViewById(R.id.leerkrachtlogin);
        Button leerling = findViewById(R.id.leerlinglogin);

        leerkracht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_leerkracht = new Intent(ScholenLoginSelector.super.getApplicationContext(),LeerkrachtLogin.class);
                startActivity(intent_leerkracht);
            }
        });

        leerling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_leerling = new Intent(ScholenLoginSelector.super.getApplicationContext(),QR_hub.class);
                startActivity(intent_leerling);
            }
        });
    }
}
