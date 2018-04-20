package vibrationspotter.Custom_views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vibrationspotter.Models.Project;
import vibrationspotter.vibrationspotterapp.R;


public class ProjectView extends LinearLayout {

    View rootView;
    TextView titel;
    ImageView openProject;
    Project project;

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
        openProject = findViewById(R.id.project_button);

    }

    public void setTitel(String titel){
        this.titel.setText(titel);
    }

    public Project getProject() {
        return project;
    }
}
