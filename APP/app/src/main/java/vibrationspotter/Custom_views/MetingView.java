package vibrationspotter.Custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vibrationspotter.Models.Meting;
import vibrationspotter.vibrationspotterapp.R;

/*-----
View die als data een Meting bevat en de layout listrow.xml heeft
----*/

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

    public MetingView(Context context, AttributeSet attrs){super(context,attrs);}

    public void init(Context context) {
        rootView = inflate(context, R.layout.listrow, this);
        titel = rootView.findViewById(R.id.textIdMeting);
        titel.setText(meting.getTitel());
        datum = rootView.findViewById(R.id.textDatum);
        datum.setText(meting.getTijdstip());
    }
}
