package vibrationspotter.vibrationspotterapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
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
    Map<String, ?> sharedPreferences;

    TextView textTitel;
    Button bDelete;
    LineChart lcf;
    LineChart lct;
    TextView xvalue, yvalue, zvalue, tvalue, fvalue, axvalue, ayvalue, azvalue;
    String tData, fData;

    int entryNummerT;
    ArrayList<String> tijdAsT;
    ArrayList<Entry> xWaardenT, yWaardenT, zWaardenT;
    List<ILineDataSet> xyzDataT;

    int entryNummerF;
    ArrayList<String> tijdAsF;
    ArrayList<Entry> xWaardenF, yWaardenF, zWaardenF;
    List<ILineDataSet> xyzDataF;

    OnChartValueSelectedListener ocvslT, ocvslF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metingview);

        gson = new Gson();

        metingstring = getIntent().getStringExtra("meting");

        meting = gson.fromJson(metingstring, Meting.class);

        textTitel = findViewById(R.id.textTitel);
        bDelete = findViewById(R.id.bDeleteMeting);
        lct = findViewById(R.id.lct);
        lcf = findViewById(R.id.lcf);
        xvalue = findViewById(R.id.xvalue);
        yvalue = findViewById(R.id.yvalue);
        zvalue = findViewById(R.id.zvalue);
        tvalue = findViewById(R.id.tvalue);
        fvalue = findViewById(R.id.fvalue);

        axvalue = findViewById(R.id.axvalue);
        ayvalue = findViewById(R.id.ayvalue);
        azvalue = findViewById(R.id.azvalue);

        textTitel.setText(meting.getTitel());

        tijdAsT = new ArrayList<>();
        xWaardenT = new ArrayList<>();
        yWaardenT = new ArrayList<>();
        zWaardenT = new ArrayList<>();

        tijdAsF = new ArrayList<>();
        xWaardenF = new ArrayList<>();
        yWaardenF = new ArrayList<>();
        zWaardenF = new ArrayList<>();


        tData = new String(Base64.decode(meting.getDataset1(), Base64.DEFAULT));
        fData = new String(Base64.decode(meting.getDataset2(), Base64.DEFAULT));

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MetingActivity.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you sure wou want to delete this project? ");
                alertDialog.setPositiveButton("OK",null);
                alertDialog.setNegativeButton("CANCEL",null);
                alertDialog.create().show();

            }
        });


        /*-----
        Verwerken van versnellingsData:
        tekstfile per regel inlezen, data-Entries maken,
        Entries bundelen, eigenschappen aan de grafiek instellen,
        grafieken bundelen en meegeven aan de ChartView,
        onCharSelectedValueListener instellen zodat de gekozen waarden
        weergegeven worden in de correcte textview
        -----*/

        xWaardenT.clear();
        yWaardenT.clear();
        zWaardenT.clear();
        tijdAsT.clear();
        entryNummerT = 0;

        String[] tData_split = tData.split("\n");

        for (int a = 0; a < tData_split.length; a++) {
            String[] lijn = tData_split[a].split(",");
            if (lijn.length == 4) {
                xWaardenT.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[1])));
                yWaardenT.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[2])));
                zWaardenT.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[3])));

                tijdAsT.add(entryNummerT, String.valueOf(Float.parseFloat(lijn[0])));
                entryNummerT++;
            } else System.out.println("False line: " + a);
        }

        LineDataSet xData = new LineDataSet(xWaardenT, "x");
        xData.setDrawCircles(false);
        xData.setColor(Color.parseColor("#005b7f"));
        xData.setDrawHorizontalHighlightIndicator(false);
        LineDataSet yData = new LineDataSet(yWaardenT, "y");
        yData.setDrawCircles(false);
        yData.setColor(Color.parseColor("#fdc101"));
        yData.setDrawHorizontalHighlightIndicator(false);
        LineDataSet zData = new LineDataSet(zWaardenT, "z");
        zData.setDrawCircles(false);
        zData.setColor(Color.parseColor("#c95854"));
        zData.setDrawHorizontalHighlightIndicator(false);

        System.out.println(xData);

        xyzDataT = new ArrayList<>();
        xyzDataT.add(xData);
        xyzDataT.add(yData);
        xyzDataT.add(zData);

        LineData dataT = new LineData(xyzDataT);

        ocvslT = new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                tvalue.setText(String.valueOf(e.getX()));

                for (Entry temp : xWaardenT) {
                    if (temp.getX() == e.getX()) {
                        xvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : yWaardenT) {
                    if (temp.getX() == e.getX()) {
                        yvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : zWaardenT) {
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

        xWaardenF.clear();
        yWaardenF.clear();
        zWaardenF.clear();
        tijdAsF.clear();
        entryNummerF = 0;

        fData = new String(Base64.decode(meting.getDataset2(), Base64.DEFAULT));
        String[] fData_split = fData.split("\n");

        for (int a = 0; a < fData_split.length; a++) {
            String[] lijn = fData_split[a].split(",");
            if (lijn.length == 4) {
                xWaardenF.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[1])));
                yWaardenF.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[2])));
                zWaardenF.add(new Entry(Float.parseFloat(lijn[0]), Float.parseFloat(lijn[3])));

                tijdAsF.add(entryNummerF, String.valueOf(Float.parseFloat(lijn[0])));
                entryNummerF++;
            } else System.out.println("False line: " + a);
        }

        LineDataSet xDataF = new LineDataSet(xWaardenF, "x");
        xDataF.setDrawCircles(false);
        xDataF.setColor(Color.parseColor("#005b7f"));
        xDataF.setDrawHorizontalHighlightIndicator(false);
        LineDataSet yDataF = new LineDataSet(yWaardenF, "y");
        yDataF.setDrawCircles(false);
        yDataF.setColor(Color.parseColor("#fdc101"));
        yDataF.setDrawHorizontalHighlightIndicator(false);
        LineDataSet zDataF = new LineDataSet(zWaardenF, "z");
        zDataF.setDrawCircles(false);
        zDataF.setColor(Color.parseColor("#c95854"));
        zDataF.setDrawHorizontalHighlightIndicator(false);

        xyzDataF = new ArrayList<>();
        xyzDataF.add(xDataF);
        xyzDataF.add(yDataF);
        xyzDataF.add(zDataF);

        LineData dataF = new LineData(xyzDataF);

        ocvslF = new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                fvalue.setText(String.valueOf(e.getX()));

                for (Entry temp : xWaardenF) {
                    if (temp.getX() == e.getX()) {
                        axvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : yWaardenF) {
                    if (temp.getX() == e.getX()) {
                        ayvalue.setText(String.valueOf(temp.getY()));
                    }
                }
                for (Entry temp : zWaardenF) {
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
