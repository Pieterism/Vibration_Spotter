package vibrationspotter.vibrationspotterapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vibrationspotter.Models.Meting;

/*-----
Klasse die geprogrammeerde logica achter de activity_metingview.xml bevat
Deze klasse vertoont de data van een door de gebruiker gekozen meting
-----*/

public class MetingActivity extends AppCompatActivity {

    String metingstring;
    Meting meting;

    Gson gson;
    SharedPreferences settings;
    Map<String,?> sharedPreferences;

    TextView textTitel;
    Button bDelete;
    LineChart lcf;
    LineChart lct;
    TextView xvalue, yvalue, zvalue, tvalue, fvalue, axvalue, ayvalue, azvalue;
    String tData, fData;

    int entryNummer;
    ArrayList<String> tijdAs;
    ArrayList<Entry> xWaarden;
    ArrayList<Entry> yWaarden;
    ArrayList<Entry> zWaarden;
    List<ILineDataSet> xyzData;

    OnChartValueSelectedListener ocvslT, ocvslF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metingview);

        gson = new Gson();
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = settings.getAll();

        metingstring = sharedPreferences.get("meting").toString();

        meting = gson.fromJson(metingstring, Meting.class);

        textTitel = findViewById(R.id.textTitel);
        bDelete = findViewById(R.id.bDeleteMeting);
        lct = findViewById(R.id.lcx);
        lcf = findViewById(R.id.lcf);
        xvalue = findViewById(R.id.xvalue);
        yvalue = findViewById(R.id.yvalue);
        zvalue = findViewById(R.id.zvalue);
        tvalue = findViewById(R.id.tvalue);
        fvalue = findViewById(R.id.fvalue);
/*
        axvalue = findViewById(R.id.axvalue);
        ayvalue = findViewById(R.id.ayvalue);
        azvalue = findViewById(R.id.azvalue);
*/

        textTitel.setText(meting.getTitel());

        tijdAs = new ArrayList<>();
        xWaarden = new ArrayList<>();
        yWaarden = new ArrayList<>();
        zWaarden = new ArrayList<>();

        fData = new String(Base64.decode(meting.getDataset2(), Base64.DEFAULT));

        System.out.println(tData);
        System.out.println(fData);

        /*-----
        Verwerken van versnellingsData:
        tekstfile per regel inlezen, data-Entries maken,
        Entries bundelen, eigenschappen aan de grafiek instellen,
        grafieken bundelen en meegeven aan de ChartView,
        onCharSelectedValueListener instellen zodat de gekozen waarden
        weergegeven worden in de correcte textview
        -----*/

        xWaarden.clear();
        yWaarden.clear();
        zWaarden.clear();
        tijdAs.clear();
        entryNummer = 0;

        tData = new String(Base64.decode(meting.getDataset1(), Base64.DEFAULT));
        String[] tData_split = tData.split("\n");

        for(int a=0; a<tData_split.length; a++){
            String[] lijn = tData_split[a].split(",");
            if(lijn.length == 4){
                xWaarden.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[1])));
                yWaarden.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[2])));
                zWaarden.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[3])));

                tijdAs.add(entryNummer, String.valueOf(Float.parseFloat(lijn[0])));
                entryNummer++;
            } else System.out.println("False line: " + a);
        }

        LineDataSet xData = new LineDataSet(xWaarden, "x");
        xData.setDrawCircles(false);
        xData.setColor(Color.parseColor("#005b7f"));
        xData.setDrawHorizontalHighlightIndicator(false);
        LineDataSet yData = new LineDataSet(yWaarden, "y");
        yData.setDrawCircles(false);
        yData.setColor(Color.parseColor("#fdc101"));
        yData.setDrawHorizontalHighlightIndicator(false);
        LineDataSet zData = new LineDataSet(zWaarden, "z");
        zData.setDrawCircles(false);
        zData.setColor(Color.parseColor("#c95854"));
        zData.setDrawHorizontalHighlightIndicator(false);

        xyzData = new ArrayList<>();
        xyzData.add(xData);
        xyzData.add(yData);
        xyzData.add(zData);

        LineData dataT = new LineData(xyzData);

        ocvslT = new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                tvalue.setText(String.valueOf(e.getX()));

                for (Entry temp : xWaarden) {
                    if (temp.getX() == e.getX()) {
                        xvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : yWaarden) {
                    if (temp.getX() == e.getX()) {
                        yvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : zWaarden) {
                    if (temp.getX() == e.getX()) {
                        zvalue.setText(String.valueOf(temp.getY()));
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        };

        lct.setData(dataT);
        lct.setOnChartValueSelectedListener(ocvslT);
        lct.invalidate();

        /*-----
        Verwerking van de FrequentieData: Zelfde werkwijze als bij versnellingsdata
        -----*/

        xWaarden.clear();
        yWaarden.clear();
        zWaarden.clear();
        tijdAs.clear();
        entryNummer = 0;

        fData = new String(Base64.decode(meting.getDataset2(), Base64.DEFAULT));
        String[] fData_split = tData.split("\n");

        for(int a=0; a<fData_split.length; a++){
            String[] lijn = fData_split[a].split(",");
            if(lijn.length == 4){
                xWaarden.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[1])));
                yWaarden.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[2])));
                zWaarden.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[3])));

                tijdAs.add(entryNummer, String.valueOf(Float.parseFloat(lijn[0])));
                entryNummer++;
            } else System.out.println("False line: " + a);
        }

        LineDataSet xDataF = new LineDataSet(xWaarden, "x");
        xDataF.setDrawCircles(false);
        xDataF.setColor(Color.parseColor("#005b7f"));
        xDataF.setDrawHorizontalHighlightIndicator(false);
        LineDataSet yDataF = new LineDataSet(yWaarden, "y");
        yDataF.setDrawCircles(false);
        yDataF.setColor(Color.parseColor("#fdc101"));
        yDataF.setDrawHorizontalHighlightIndicator(false);
        LineDataSet zDataF = new LineDataSet(zWaarden, "z");
        zDataF.setDrawCircles(false);
        zDataF.setColor(Color.parseColor("#c95854"));
        zDataF.setDrawHorizontalHighlightIndicator(false);

        xyzData = new ArrayList<>();
        xyzData.add(xDataF);
        xyzData.add(yDataF);
        xyzData.add(zDataF);

        LineData dataF = new LineData(xyzData);

        ocvslF = new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                fvalue.setText(String.valueOf(e.getX()));

                for (Entry temp : xWaarden) {
                    if (temp.getX() == e.getX()) {
                        axvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : yWaarden) {
                    if (temp.getX() == e.getX()) {
                        ayvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : zWaarden) {
                    if (temp.getX() == e.getX()) {
                        azvalue.setText(String.valueOf(temp.getY()));
                    }
                }

            }

            @Override
            public void onNothingSelected() {

            }
        };

        lcf.setData(dataF);
        lcf.setOnChartValueSelectedListener(ocvslF);
        lcf.invalidate();
    }
}
