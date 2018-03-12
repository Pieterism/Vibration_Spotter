package vibrationspotter.vibrationspotterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.knoppie);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String REQUEST_TAG = "Stringrequest";


                StringRequest strReq = new StringRequest(Request.Method.GET,
                        "http://192.168.1.37:8080/VibrationspotterREST/Restservice/restTest",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(REQUEST_TAG, response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(REQUEST_TAG, "Error: " + error.toString() + ", " + error.getMessage());
                            }
                        }
                );

                VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
            }
        });

    }
}
