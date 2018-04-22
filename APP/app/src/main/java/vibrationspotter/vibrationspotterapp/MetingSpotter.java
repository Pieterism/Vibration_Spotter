package vibrationspotter.vibrationspotterapp;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MetingSpotter extends AppCompatActivity implements SensorEventListener {

    LineChart lcTest;
    Button bStart;
    Button bStop;
    TextView tvX, tvY, tvZ, tvT;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean started;
    private double x, y, z;
    private long tijd, starttijd;
    private JSONArray jArray;
    ArrayList<String> tijdAs;
    ArrayList<Entry> xWaarden;
    ArrayList<Entry> yWaarden;
    ArrayList<Entry> zWaarden;
    List<ILineDataSet> xyzData;
    int entryNummer;
    String doorzendData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meting_multimeting);

        lcTest = findViewById(R.id.lcTest);
        bStart = findViewById(R.id.bStart);
        bStop = findViewById(R.id.bStop);

        tvT = findViewById(R.id.tvT);
        tvX = findViewById(R.id.tvX);
        tvY = findViewById(R.id.tvY);
        tvZ = findViewById(R.id.tvZ);

        started = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        bStop.setVisibility(View.INVISIBLE);
        jArray = new JSONArray();

        tijdAs = new ArrayList<>();
        xWaarden = new ArrayList<>();
        yWaarden = new ArrayList<>();
        zWaarden = new ArrayList<>();

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bStart.setVisibility(View.INVISIBLE);
                bStop.setVisibility(View.VISIBLE);
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
            @SuppressWarnings("SuspiciousNameCombination")
            @Override
            public void onClick(View view) {
                started = false;
                bStop.setVisibility(View.INVISIBLE);
                bStart.setVisibility(View.VISIBLE);

                System.out.println(doorzendData);
                System.out.println(doorzendData.length());
                System.out.println(entryNummer);

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


                xyzData = new ArrayList<>();
                xyzData.add(xData);
                xyzData.add(yData);
                xyzData.add(zData);

                LineData data = new LineData(xyzData);

                OnChartValueSelectedListener listener = new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {

                        tvT.setText("T: " + String.valueOf(e.getX()));

                        for (Entry temp : xWaarden) {
                            if (temp.getX() == e.getX()) {
                                tvX.setText("X: " + String.valueOf(temp.getY()));
                            }
                        }
                        for (Entry temp : yWaarden) {
                            if (temp.getX() == e.getX()) {
                                tvY.setText("Y: " + String.valueOf(temp.getY()));
                            }
                        }
                        for (Entry temp : zWaarden) {
                            if (temp.getX() == e.getX()) {
                                tvZ.setText("Z: " + String.valueOf(temp.getY()));
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                };

                lcTest.setData(data);
                lcTest.setOnChartValueSelectedListener(listener);
                lcTest.invalidate();
            }
        });

    }

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

            doorzendData = doorzendData.concat(String.valueOf(tijd) + "," + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z) + "\n");

            entryNummer++;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, mSensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
    }
}
