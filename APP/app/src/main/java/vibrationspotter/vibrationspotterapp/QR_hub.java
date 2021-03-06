package vibrationspotter.vibrationspotterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import vibrationspotter.QR_Utilities.BarcodeCaptureActivity;

/*-----
Een tussenklasse om debuggen te vermakkelijken en de mogelijkheid te bieden om de camera later op
andere manieren te gebruiken dan voor QR-codes te scannen
-----*/

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

        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BARCODE_READER_REQUEST_CODE){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    final Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Intent doorgeven = getIntent();
                    doorgeven.putExtra("QR_code", barcode.displayValue);
                    setResult(CommonStatusCodes.SUCCESS, doorgeven);
                    finish();
                }
                else textView.setText("Data == null");
            }
            else {
                textView.setText("Dit is de fout");
            }
        }
        else textView.setText("Dit is een BUG");
    }
}
