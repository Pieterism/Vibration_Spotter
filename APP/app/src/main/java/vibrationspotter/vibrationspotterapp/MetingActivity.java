package vibrationspotter.vibrationspotterapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;

import java.util.Map;

import vibrationspotter.Models.Meting;

public class MetingActivity extends AppCompatActivity {

    String metingstring;
    Meting meting;

    Gson gson;
    SharedPreferences settings;
    Map<String,?> sharedPreferences;
    private static final int GET_METING = 325;

    TextView textTitel;
    Button bDelete;
    LineChart lcf;
    LineChart lct;
    TextView xvalue, yvalue, zvalue, tvalue, fvalue, avalue;
    String tData, fData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metingview);

        gson = new Gson();
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = settings.getAll();

        metingstring = sharedPreferences.get("meting").toString();

        meting = gson.fromJson(metingstring, Meting.class);

        textTitel = findViewById(R.id.textTitel);
        bDelete = findViewById(R.id.bDeleteMeting);
        lct = findViewById(R.id.lcx);
        lcf = findViewById(R.id.lcf);
        xvalue = findViewById(R.id.xvalue);
        yvalue = findViewById(R.id.yvalue);
        zvalue = findViewById(R.id.zvalue);
        tvalue = findViewById(R.id.tvalue);
        fvalue = findViewById(R.id.fvalue);
        avalue = findViewById(R.id.avalue);

        textTitel.setText(meting.getTitel());

        tData = meting.getDataset1();

        fData = meting.getDataset2();
    }
}
