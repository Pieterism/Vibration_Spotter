package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import vibrationspotter.QR_Utilities.BarcodeCaptureActivity;

public class QR_hub extends Activity{
    TextView textView;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BARCODE_READER_REQUEST_CODE){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    textView.setText(barcode.displayValue);
                }
                else textView.setText("Data == null");
            }
            else textView.setText("Geen Succes");
        }
        else textView.setText("Mislukt");
    }
}
