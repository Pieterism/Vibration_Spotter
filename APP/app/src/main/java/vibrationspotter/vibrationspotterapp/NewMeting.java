package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

public class NewMeting extends AppCompatActivity {

    Button bTest;
    TextView tv1;
    TextView tv2;
    private static final int METINGEN = 28;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        bTest = findViewById(R.id.bTest);
        tv1 = findViewById(R.id.tv1Test);
        tv2 = findViewById(R.id.tv2Test);

        bTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent naar_meting = new Intent(getApplicationContext(), Meter.class);
                startActivityForResult(naar_meting, METINGEN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null && resultCode == CommonStatusCodes.SUCCESS){
            switch (requestCode){
                case METINGEN:
                    System.out.println("Case:Meting");
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "REQUESTCODE_BUG", Toast.LENGTH_LONG).show();
                    break;
            }

        } else {

        }
    }
}
