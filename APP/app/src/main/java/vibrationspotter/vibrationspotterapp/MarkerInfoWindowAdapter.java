package vibrationspotter.vibrationspotterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import vibrationspotter.Models.Project;

//TODO: TextView values uit databank aanvullen
public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;


    public MarkerInfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        Project p = (Project) arg0.getTag();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.map_marker_info_window, null);

        TextView titel = v.findViewById(R.id.project_titel);
        titel.setText(p.getTitel());

        TextView opmerking = v.findViewById(R.id.textAantalMetingen);
        opmerking.setText(p.getBeschrijving());

        return v;
    }

}