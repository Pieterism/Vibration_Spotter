package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vibrationspotter.QR_Utilities.BarcodeCaptureActivity;

public class QR_hub extends AppCompatActivity{
    private TextView textView;
    final int BARCODE_READER_REQUEST_CODE = 9966;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrhub);
        Button naarQRlezer = findViewById(R.id.naarQRlezer);
        textView = findViewById(R.id.QRresultaat);

        naarQRlezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && resultCode == CommonStatusCodes.SUCCESS){
            switch (requestCode){
                case BARCODE_READER_REQUEST_CODE:
                    final Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Intent doorgeven = new Intent();
                    doorgeven.putExtra("QR_code", barcode.displayValue);
                    setResult(CommonStatusCodes.SUCCESS);
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error: Verkeerde Requestcode", Toast.LENGTH_LONG).show();
                    break;
            }
        } else {
            textView.setText("Dit is een Bug");
            Intent canceled = new Intent();
            setResult(CommonStatusCodes.CANCELED);
            finish();
        }


    }
}
