package vibrationspotter.Custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import vibrationspotter.Models.Project;
import vibrationspotter.vibrationspotterapp.R;
import vibrationspotter.vibrationspotterapp.VolleyClass;

/*-----
View die als data een project bevat en de layou projectview.xml heeft
Het aantal metingen wordt per Projectview opgevraagd
-----*/
public class ProjectView extends LinearLayout {

    View rootView;
    TextView titel;
    Project project;
    TextView tvaantalMetingen;
    Gson gson;


    public ProjectView(Context context, Project p) {
        super(context);
        this.project = p;
        init(context);
    }

    public ProjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context){

        gson = new Gson();

        rootView = inflate(context, R.layout.projectview,this);
        titel = rootView.findViewById(R.id.project_titel);
        titel.setText(project.getTitel());
        tvaantalMetingen = rootView.findViewById(R.id.textAantalMetingen);


        JsonArrayRequest projectensizeRequest =  new JsonArrayRequest(
                context.getString(R.string.url) + "Projecten/Size/" + getProject().getIdProject(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("OphalenProjectensize", response.toString());

                        Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
                        List<Map<String,String>>  sizes = gson.fromJson(response.toString(), type);
                        tvaantalMetingen.setText("Metingen in project:" + sizes.get(0).get("size"));


                      //  aantalmetingen = sizes.get(0);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("OphalenProjectensize", "Error: " + error.toString() + ", " + error.getMessage());
                    }
                }
        );
        VolleyClass.getInstance(context).addToRequestQueue(projectensizeRequest, "MetingKeuzeActivity");
    }

    public void setTitel(String titel){
        this.titel.setText(titel);
    }

    public Project getProject() {
        return project;
    }
}
