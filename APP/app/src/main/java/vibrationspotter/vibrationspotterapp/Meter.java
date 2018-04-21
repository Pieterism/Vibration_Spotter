package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.common.api.CommonStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Meter extends Activity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean started, hasData;
    private double x, y, z;
    private long tijd, starttijd;
    private JSONArray jArray;
    LineChart lineChartX;
    LineChart lineChartY;
    LineChart lineChartZ;
    ArrayList<String> tijdAs;
    ArrayList<Entry> xWaarden;
    ArrayList<Entry> yWaarden;
    ArrayList<Entry> zWaarden;
    int entryNummer;
    String doorzendData;

    Button bStart;
    Button bStop;
    Button bSave;
    Button bReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meting);
        started = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); //trycatch?
        bStart = findViewById(R.id.bStart);
        bStop = findViewById(R.id.bStop);
        bSave = findViewById(R.id.bSave);
        bStop.setVisibility(View.INVISIBLE);
        bReject = findViewById(R.id.bRejectMeting);
        jArray = new JSONArray();
        lineChartX = findViewById(R.id.lcx);
        lineChartY = findViewById(R.id.lcy);
        lineChartZ = findViewById(R.id.lcz);

        tijdAs = new ArrayList<>();
        xWaarden = new ArrayList<>();
        yWaarden = new ArrayList<>();
        zWaarden = new ArrayList<>();

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bStart.setVisibility(View.INVISIBLE);
                bStop.setVisibility(View.VISIBLE);
                bSave.setVisibility(View.INVISIBLE);
                bReject.setVisibility(View.INVISIBLE);
                xWaarden.clear();
                yWaarden.clear();
                zWaarden.clear();
                tijdAs.clear();
                entryNummer = 0;
                doorzendData = "";
                starttijd = System.currentTimeMillis();
                started = true;
            }
        });

        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bStop.setVisibility(View.INVISIBLE);
                bStart.setVisibility(View.VISIBLE);
                bSave.setVisibility(View.VISIBLE);
                bReject.setVisibility(View.VISIBLE);

                started = false;
                hasData = true;

                LineDataSet xData = new LineDataSet(xWaarden, "x");
                xData.setDrawCircles(false);
                LineDataSet yData = new LineDataSet(yWaarden, "y");
                yData.setDrawCircles(false);
                LineDataSet zData = new LineDataSet(zWaarden, "z");
                zData.setDrawCircles(false);

                lineChartX.setData(new LineData(xData));
                lineChartX.invalidate();
                lineChartY.setData(new LineData(yData));
                lineChartY.invalidate();
                lineChartZ.setData(new LineData(zData));
                lineChartZ.invalidate();

                System.out.println(doorzendData);
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!started && hasData) {
                    Intent gelukt = new Intent();
                    gelukt.putExtra("data", doorzendData);
                    setResult(CommonStatusCodes.SUCCESS);
                    finish();
                }
            }
        });

        bReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!started){
                    lineChartZ.clear();
                    lineChartY.clear();
                    lineChartX.clear();

                    xWaarden.clear();
                    yWaarden.clear();
                    zWaarden.clear();
                    tijdAs.clear();
                    entryNummer = 0;
                    doorzendData = "";
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(started){
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            tijd = System.currentTimeMillis() - starttijd;
            System.out.println(x + ", " + y + ", " + z + ",       " + tijd);

            xWaarden.add(new Entry(tijd,Float.parseFloat(String.valueOf(x))));
            yWaarden.add(new Entry(tijd,Float.parseFloat(String.valueOf(y))));
            zWaarden.add(new Entry(tijd,Float.parseFloat(String.valueOf(z))));

            tijdAs.add(entryNummer,String.valueOf(tijd));

            entryNummer++;

            doorzendData = doorzendData.concat(String.valueOf(tijd) + "," + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, mSensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
