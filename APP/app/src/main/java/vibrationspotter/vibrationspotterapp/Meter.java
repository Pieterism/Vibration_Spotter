package vibrationspotter.vibrationspotterapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Meter extends Activity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean started;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter);
        started = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); //trycatch?
        final Button bStart = findViewById(R.id.bStart);
        final Button bStop = findViewById(R.id.bStop);
        bStop.setVisibility(View.INVISIBLE);
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
                xWaarden.clear();
                yWaarden.clear();
                zWaarden.clear();
                tijdAs.clear();
                entryNummer = 0;
                starttijd = System.currentTimeMillis();
                started = true;
            }
        });

        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bStop.setVisibility(View.INVISIBLE);
                bStart.setVisibility(View.VISIBLE);
                started = false;

                String[] tijdas = new String[tijdAs.size()];
                for(int i=0; i<tijdAs.size(); i++){
                    tijdas[i] = tijdAs.get(i);
                }


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
        mSensorManager.registerListener(this, mAccelerometer, mSensorManager.SENSOR_DELAY_GAME);
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

      //DEEL PJ
        JSONObject jObject = new JSONObject();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();

       String email = settings.getString("email",null);

    /*    try {
            jObject.put("email",email);
            jObject.put("titel","titel");           //titel moet nog ingevuld worden in APP
           // jObject.put("idProject", idProject);               //Kiezen bij welk project hoort
          //  jObject.put("tijdstip","titel");                   //
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jArray.put(jObject);
*/
        final String REQUEST_TAG = "Metingen";
        JsonArrayRequest strReq = new JsonArrayRequest(Request.Method.POST,

                getString(R.string.url) + "Metingen",
                jArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Metingen", response.toString());






                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Metingen", "Error: " + error.toString() + ", " + error.getMessage());
                    }
                }
        ){
        };

        VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(strReq, "Metingen");
    }
}
