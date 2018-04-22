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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import vibrationspotter.Models.Meting;

public class NewMeting extends AppCompatActivity {

    private static final int SELECTED_PIC = 1111;
    private static final int METINGEN = 28;

    Button bSave;
    EditText metingTitel;
    EditText metingDescription;
    ImageView ivImage;
    Button bRotate;

    Bitmap bitmap;
    boolean hasImage;

    byte[] meetdata;
    String doorzendData;

    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmeting);

        metingTitel = findViewById(R.id.metingTitle);
        metingDescription = findViewById(R.id.metingDescription);
        ivImage = findViewById(R.id.ivImage);
        bRotate = findViewById(R.id.bRotate);
        bSave = findViewById(R.id.bSave);
        hasImage = false;
        gson = new Gson();

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECTED_PIC);
            }
        });

        bRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivImage.setRotation(ivImage.getRotation()+90);
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasImage) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    Meting doorzendmeting = new Meting(metingTitel.getText().toString(), metingDescription.getText().toString(), imageString, meetdata);

                    JSONArray jArray = doorzendmeting.toJArray();

                    JsonArrayRequest newMetingrequest = new JsonArrayRequest(Request.Method.POST,
                            getString(R.string.url) + "Metingen",
                            jArray,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    System.out.println(response.toString());

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(NewMeting.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(newMetingrequest, "newMeting");

                } else {
                    Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_LONG).show();
                }

            }
        });

        Intent eerst_meting_maken = new Intent(getApplicationContext(), Meter.class);
        startActivityForResult(eerst_meting_maken, METINGEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {
                case METINGEN:
                    doorzendData = data.getStringExtra("data");
                    meetdata = doorzendData.getBytes();
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
                    hasImage = true;
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "REQUESTCODE_BUG", Toast.LENGTH_LONG).show();
                    break;
            }

        } else {
            System.out.println("Hmmm");
        }
    }
}
