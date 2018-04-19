package vibrationspotter.vibrationspotterapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = (TextView) findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/aclonica.regular.ttf");
        textView.setTypeface(typeface);
        setContentView(R.layout.activity_splash);
    }
}
