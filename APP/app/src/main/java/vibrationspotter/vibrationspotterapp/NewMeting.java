package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

public class NewMeting extends AppCompatActivity {

    Button bSave;
    EditText metingTitel;
    EditText metingDescription;
    ImageView ivImage;
    Button bRotate;

    private static final int METINGEN = 28;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        metingTitel = findViewById(R.id.metingTitle);
        metingDescription = findViewById(R.id.metingDescription);
        ivImage = findViewById(R.id.ivImage);
        //bRotate = findViewById(R.id.bRotate);
        bSave = findViewById(R.id.bSave);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
