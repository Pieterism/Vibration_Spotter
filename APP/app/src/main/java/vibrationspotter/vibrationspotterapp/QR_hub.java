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
    private String baseurl;
    final int BARCODE_READER_REQUEST_CODE = 9966;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrhub);

        baseurl = getString(R.string.url);
        Button naarQRlezer = findViewById(R.id.naarQRlezer);
        textView = findViewById(R.id.QRresultaat);

        naarQRlezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BARCODE_READER_REQUEST_CODE){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    final Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    JSONObject jObject = new JSONObject();
                    try{
                        jObject.put("QR_code", barcode.displayValue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jArray = new JSONArray();
                    jArray.put(jObject);
                    JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.POST,
                            baseurl + "QR",
                            jArray,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("QR", response.toString());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(QR_hub.this);
                                    builder.setMessage(response.toString())
                                            .setNegativeButton("Close",null)
                                            .create()
                                            .show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("QR","Error: " + error.toString() + ", " + error.getMessage());
                                }
                            }
                    ){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> map = new HashMap<>();
                            map.put("String",barcode.displayValue);
                            return map;
                        }
                    };
                    VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, "QR");
                }
                else textView.setText("Data == null");
            }
            else textView.setText("Geen Succes");
        }
        else textView.setText("Mislukt");
    }
}
