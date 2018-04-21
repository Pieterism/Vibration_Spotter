package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NewMeting extends AppCompatActivity {

    private static final int SELECTED_PIC = 1111;
    private static final int METINGEN = 28;

    Button bSave;
    EditText metingTitel;
    EditText metingDescription;
    ImageView ivImage;
    Button bRotate;

    Bitmap bitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmeting);

        metingTitel = findViewById(R.id.metingTitle);
        metingDescription = findViewById(R.id.metingDescription);
        ivImage = findViewById(R.id.ivImage);
        //bRotate = findViewById(R.id.bRotate);
        bSave = findViewById(R.id.bSave);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECTED_PIC);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && resultCode == CommonStatusCodes.SUCCESS){
            switch (requestCode){
                case METINGEN:
                    System.out.println("Case:Meting");
                    break;
                case SELECTED_PIC:
                    Uri uri = data.getData();
                    try {
                        InputStream is = getContentResolver().openInputStream(uri);
                        bitmap = BitmapFactory.decodeStream(is);
                        ivImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT); */
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "REQUESTCODE_BUG", Toast.LENGTH_LONG).show();
                    break;
            }

        } else {

        }
    }
}
