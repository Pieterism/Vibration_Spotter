package vibrationspotter.Custom_views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vibrationspotter.Models.Meting;
import vibrationspotter.vibrationspotterapp.R;

public class MetingView extends LinearLayout {

    View rootView;
    TextView titel;
    TextView datum;
    Meting meting;

    public MetingView(Context context, Meting m) {
        super(context);
        this.meting = m;
        init(context);

    }

    public void init(Context context) {
        rootView = inflate(context, R.layout.listrow, this);
        titel = rootView.findViewById(R.id.textIdMeting);
        titel.setText(meting.getTitel());
        datum = rootView.findViewById(R.id.textDatum);
        datum.setText(meting.getTijdstip());
    }
}
