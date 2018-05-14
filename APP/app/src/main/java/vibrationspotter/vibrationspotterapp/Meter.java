package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.common.api.CommonStatusCodes;

import org.json.JSONArray;

import java.util.ArrayList;

/*-----
Klasse die geprogrammeerde logica achter de activity_meting.xml bevat
1 van de 2 activities waar trillingen kunnen gemeten worden
-----*/

public class Meter extends Activity implements SensorEventListener {

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

        //startknop die de meting start en vooraf alle variabelen reset

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

        //stopknop die de meting stopzet en de gemeten waarden plot naar de grafieken

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
                xData.setColor(Color.parseColor("#005b7f"));
                xData.setDrawHorizontalHighlightIndicator(false);
                LineDataSet yData = new LineDataSet(yWaarden, "y");
                yData.setDrawCircles(false);
                yData.setColor(Color.parseColor("#fdc101"));
                yData.setDrawHorizontalHighlightIndicator(false);
                LineDataSet zData = new LineDataSet(zWaarden, "z");
                zData.setDrawCircles(false);
                zData.setColor(Color.parseColor("#c95854"));
                zData.setDrawHorizontalHighlightIndicator(false);

                xData.setDrawHorizontalHighlightIndicator(false);
                yData.setDrawHorizontalHighlightIndicator(false);
                zData.setDrawHorizontalHighlightIndicator(false);
                xData.setHighLightColor(Color.BLACK);

                lineChartX.setData(new LineData(xData));
                lineChartX.invalidate();
                lineChartY.setData(new LineData(yData));
                lineChartY.invalidate();
                lineChartZ.setData(new LineData(zData));
                lineChartZ.invalidate();

                System.out.println(doorzendData);
            }
        });

        //Saveknop die de activity laat weten dat de gebruiker de data goedkeurt

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(doorzendData);


                if (!started && hasData) {
                    Intent gelukt = new Intent();
                    gelukt.putExtra("data", doorzendData);
                    setResult(CommonStatusCodes.SUCCESS, gelukt);
                    finish();
                }
            }
        });

        //cleart de data wanneer de gebruiker niet tevreden is met het resultaat

        bReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!started) {
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

    //Telkens de accelerometers een verandering meten wordt deze methode opgeroepen
    //Data wordt verzameld in 2 formaten: de een voor de preview en de ander om door te sturen

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (started) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            tijd = System.currentTimeMillis() - starttijd;
            System.out.println(x + ", " + y + ", " + z + ",       " + tijd);

            xWaarden.add(new Entry(tijd, Float.parseFloat(String.valueOf(x))));
            yWaarden.add(new Entry(tijd, Float.parseFloat(String.valueOf(y))));
            zWaarden.add(new Entry(tijd, Float.parseFloat(String.valueOf(z))));

            tijdAs.add(entryNummer, String.valueOf(tijd));

            entryNummer++;

            doorzendData = doorzendData.concat(String.valueOf(tijd) + "," + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z) + "\n");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
