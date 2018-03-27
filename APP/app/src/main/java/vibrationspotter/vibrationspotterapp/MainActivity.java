package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.Login_knop);
        TextView tvRegistreren = findViewById(R.id.tvRegistreren);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.super.getApplicationContext(),LoginSelector.class);
                startActivity(intent);
            }
        });

        tvRegistreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regeistreerIntent = new Intent(MainActivity.this,Registreren.class);
                startActivity(regeistreerIntent);
            }
        });
    }
}
