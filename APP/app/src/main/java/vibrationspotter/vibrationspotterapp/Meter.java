package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Meter extends Activity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean started;
    private double x, y, z;
    private long tijd, starttijd;
    private String tekst;
    private JSONArray jArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        final Button button = findViewById(R.id.button);
        tekst = "Start";
        button.setText(tekst);
        started = false;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(started) {
                    tekst = "Start";
                    button.setText(tekst);
                    starttijd = System.currentTimeMillis();
                }
                else {
                    tekst = "Stop";
                    button.setText(tekst);
                }
                started = !started;
            }
        });
        jArray = new JSONArray();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(started){
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            tijd = System.currentTimeMillis() - starttijd;
            System.out.println(x + ", " + y + ", " + z + ",       " + tijd);
            JSONObject jObject = new JSONObject();
            try {
                jObject.put("x", x);
                jObject.put("y", y);
                jObject.put("z", z);
                jObject.put("tijd", tijd);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jArray.put(jObject);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, mSensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    protected void onStop(){
        super.onStop();
        System.out.println(jArray.toString());
    }
}
