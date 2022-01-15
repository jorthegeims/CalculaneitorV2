package apps.fuerza.calcularda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.calculaneitorv2.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private final ArrayList<LineGraphSeries<DataPoint>> series = new ArrayList<>();
    private GraphView funcion;
    private int posicion = 0;
    private MenuItem BA,BS;
    private double[] X;
    private double[] Y;
    private int E;
    private int dou;
    private double GRx;
    private double GRy;
    private double maxx;
    private double maxy;
    private double minx;
    private double miny;
    private double[] X2;
    private double[] Y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }});
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-5014261020839028/1414780566");

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        X = getIntent().getDoubleArrayExtra("dato");
        Y = getIntent().getDoubleArrayExtra("dato2");

        E = getIntent().getIntExtra("dato3",0);
        dou = 0;
        GRx = getIntent().getDoubleExtra("dato4",0);
        GRy = getIntent().getDoubleExtra("dato5",0);
        maxx = getIntent().getDoubleExtra("dato6",0);
        maxy = getIntent().getDoubleExtra("dato7",0);
        minx = getIntent().getDoubleExtra("dato8",0);
        miny = getIntent().getDoubleExtra("dato9",0);

        X2 = getIntent().getDoubleArrayExtra("dato10");
        Y2 = getIntent().getDoubleArrayExtra("dato11");

        funcion = findViewById(R.id.graphViewFunction2);

        funcion.getViewport().setScalable(true);
        funcion.getViewport().setScrollable(true);
        funcion.getViewport().setScrollableY(true);
        funcion.getViewport().setScalableY(true);

        funcion.getViewport().setMaxX(maxx);
        funcion.getViewport().setMaxY(maxy);
        funcion.getViewport().setMinX(minx);
        funcion.getViewport().setMinY(miny);

        for (int i = 0; i < E; i++) {

            series.add(i, new LineGraphSeries<DataPoint>());

            series.get(i).setColor(Color.RED);

            if (X[i] < 0) {

                series.get(i).appendData(new DataPoint(X[i], Y[i]), true, 500);

                series.get(i).appendData(new DataPoint(0, 0), true, 500);

            }else {

                series.get(i).appendData(new DataPoint(0, 0), true, 500);

                series.get(i).appendData(new DataPoint(X[i], Y[i]), true, 500);

            }

            funcion.addSeries(series.get(i));

            dou = i+1;

        }

        series.add(dou, new LineGraphSeries<DataPoint>());

        if (GRx < 0) {
            series.get(dou).appendData(new DataPoint(GRx, GRy), true, 500);
            series.get(dou).appendData(new DataPoint(0, 0), true, 500);
        }else {
            series.get(dou).appendData(new DataPoint(0, 0), true, 500);
            series.get(dou).appendData(new DataPoint(GRx, GRy), true, 500);

        }
        funcion.addSeries(series.get(dou));
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menuoverflow,menu);
        BA = menu.findItem(R.id.anteriorG);
        BS = menu.findItem(R.id.siguienteG);
        BA.setIcon(R.drawable.flecha_invicible);
        BA.setEnabled(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.siguienteG) {
            posicion++;
            if (posicion >= 2){
                BS.setIcon(R.drawable.flecha_invicible);
                BS.setEnabled(false);
            }
            BA.setIcon(R.drawable.flecha_izquierda);
            BA.setEnabled(true);
        }
        else if(id == R.id.anteriorG){
            posicion--;
            if (posicion <= 0){
                BA.setIcon(R.drawable.flecha_invicible);
                BA.setEnabled(false);

            }
            BS.setIcon(R.drawable.flecha_derecha);
            BS.setEnabled(true);
        }

        if (posicion == 1) { series.clear();
            funcion.removeAllSeries();

            funcion.getViewport().setMaxX(maxx);
            funcion.getViewport().setMaxY(maxy);
            funcion.getViewport().setMinX(minx);
            funcion.getViewport().setMinY(miny);

            double x = 0;
            double y = 0;
            for (int i = 0; i < E; i++) {


                series.add(new LineGraphSeries<DataPoint>());

                series.get(i).setColor(Color.GREEN);

                if (X[i] < 0) {

                    series.get(i).appendData(new DataPoint(X2[i], Y2[i]), true, 500);

                    series.get(i).appendData(new DataPoint(x, y), true, 500);


                } else {

                    series.get(i).appendData(new DataPoint(x, y), true, 500);

                    series.get(i).appendData(new DataPoint(X2[i], Y2[i]), true, 500);

                }

                x = X2[i];
                y = Y2[i];

                funcion.getSeries().add(i,series.get(i));}

                series.add(new LineGraphSeries<DataPoint>());

                if (GRx < 0) {
                    series.get(dou).appendData(new DataPoint(GRx, GRy), true, 500);
                    series.get(dou).appendData(new DataPoint(0, 0), true, 500);
                }else {
                    series.get(dou).appendData(new DataPoint(0, 0), true, 500);
                    series.get(dou).appendData(new DataPoint(GRx, GRy), true, 500);

                }
                funcion.getSeries().add(dou,series.get(dou));
        }

        else if (posicion == 0 || posicion == 2){

            int dou2 = 0;

            if (posicion== 0){
                series.clear();
                funcion.removeAllSeries();

                funcion.getViewport().setMaxX(maxx);
                funcion.getViewport().setMaxY(maxy);
                funcion.getViewport().setMinX(minx);
                funcion.getViewport().setMinY(miny);

            }

            for (int i = 0; i < E; i++) {

                series.add(i, new LineGraphSeries<DataPoint>());

                series.get(i).setColor(Color.RED);

                if (X[i] < 0) {

                    series.get(i).appendData(new DataPoint(X[i], Y[i]), true, 500);

                    series.get(i).appendData(new DataPoint(0, 0), true, 500);

                }else {

                    series.get(i).appendData(new DataPoint(0, 0), true, 500);

                    series.get(i).appendData(new DataPoint(X[i], Y[i]), true, 500);

                }

                funcion.addSeries(series.get(i));

                dou2 = dou2+1;

            }

            series.add(dou2, new LineGraphSeries<DataPoint>());

            if (GRx < 0) {
                series.get(dou2).appendData(new DataPoint(GRx, GRy), true, 500);
                series.get(dou2).appendData(new DataPoint(0, 0), true, 500);
            }else {
                series.get(dou2).appendData(new DataPoint(0, 0), true, 500);
                series.get(dou2).appendData(new DataPoint(GRx, GRy), true, 500);

            }
            funcion.addSeries(series.get(dou2));
        }
        return super.onOptionsItemSelected(item);
    }
}