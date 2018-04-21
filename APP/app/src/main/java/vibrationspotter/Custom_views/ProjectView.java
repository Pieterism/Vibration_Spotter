package vibrationspotter.Custom_views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vibrationspotter.Models.Project;
import vibrationspotter.vibrationspotterapp.R;
import vibrationspotter.vibrationspotterapp.VolleyClass;


public class ProjectView extends LinearLayout {

    View rootView;
    TextView titel;
    Project project;
    int aantalmetingen;

    public ProjectView(Context context, Project p) {
        super(context);
        this.project = p;
        init(context);
    }

    public ProjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context){

        rootView = inflate(context, R.layout.projectview,this);
        titel = rootView.findViewById(R.id.project_titel);
        titel.setText(project.getTitel());

        JsonArrayRequest projectensize =  new JsonArrayRequest(
                context.getString(R.string.url) + "Projecten/Size/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("OphalenProjectensize", response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("OphalenProjectensize", "Error: " + error.toString() + ", " + error.getMessage());
                    }
                }
        );




    }

    public void setTitel(String titel){
        this.titel.setText(titel);
    }

    public Project getProject() {
        return project;
    }
}
