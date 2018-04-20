package vibrationspotter.vibrationspotterapp;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.lang.reflect.Method;

public class MetingKeuzeActivity extends AppCompatActivity {

    LinearLayout surface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_scrollview);

        surface = findViewById(R.id.surface);
        System.out.println(getIntent().getStringExtra("projectID"));

        JsonArrayRequest alleMetingenVoorProjectRequest = new JsonArrayRequest(
                getString(R.string.url) + "Metingen/AlleMetingen/" + getIntent().getStringExtra("projectID"),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }
        );

        VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(alleMetingenVoorProjectRequest, "MetingKeuzeActivity");
        
    }
}
