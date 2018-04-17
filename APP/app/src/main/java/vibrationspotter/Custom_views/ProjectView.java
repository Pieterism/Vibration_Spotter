package vibrationspotter.Custom_views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vibrationspotter.vibrationspotterapp.R;


public class ProjectView extends ConstraintLayout {

    View rootView;
    ImageView projectAfbeelding;
    TextView titel;
    ImageView openProject;

    public ProjectView(Context context, int i) {
        super(context);
        init(context, i);
    }

    public ProjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context, int i){

        rootView = inflate(context, R.layout.projectview,this);
        titel = rootView.findViewById(R.id.project_titel);
        titel.setText("TEST " + i);
        projectAfbeelding = findViewById(R.id.project_logo);
        openProject = findViewById(R.id.project_button);

    }

    public void setTitel(String titel){
        this.titel.setText(titel);
    }


}
