package apps.fuerza.calcularda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.calculaneitorv2.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class MainActivity extends AppCompatActivity {

    private TextView r;
    private int contadorF = 1;
    private int contadorA = 1;
    private int contadorFx = -1;
    private int contadorAx = -1;
    private final ArrayList<EditText> fuerzaE = new ArrayList<>();
    private final ArrayList<EditText> anguloE = new ArrayList<>();
    private final ArrayList<EditText> fuerzax = new ArrayList<>();
    private final ArrayList<EditText> angulox = new ArrayList<>();
    private double Rx = 0;
    private double Ry = 0;
    private double[] fuerzaX = new double[fuerzaE.size()];
    private double[] fuerzaY = new double[fuerzaE.size()];
    private double[] caminoX,caminoY;
    private double fminx,fminy,fmaxx,fmaxy;
    private boolean on = false;
    private Button BG;
    private LinearLayout layoutfuerza;
    private LinearLayout layoutangulo;
    private ImageButton el;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }});
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-5014261020839028/3539470045");

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        fuerzaE.add((EditText) findViewById(R.id.etn1));
        fuerzaE.add((EditText) findViewById(R.id.etn2));

        anguloE.add((EditText) findViewById(R.id.angulo_et1));
        anguloE.add((EditText) findViewById(R.id.angulo_et2));

        BG = findViewById(R.id.buttonw);
        el = findViewById(R.id.imageButton2);

        r = findViewById(R.id.tv);

        layoutfuerza = findViewById(R.id.FuerzaLayout);
        layoutangulo = findViewById(R.id.AnguloLayout);

    }

        public void Graficar (View view) {

        if (on){

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                int daton = fuerzaE.size();

                intent.putExtra("dato", fuerzaX);
                intent.putExtra("dato2", fuerzaY);
                intent.putExtra("dato3", daton);
                intent.putExtra("dato4", Rx);
                intent.putExtra("dato5", Ry);
                intent.putExtra("dato6", fmaxx);
                intent.putExtra("dato7", fmaxy);
                intent.putExtra("dato8", fminx);
                intent.putExtra("dato9", fminy);
                intent.putExtra("dato10",caminoX);
                intent.putExtra("dato11",caminoY);

                startActivity(intent);
            }
        }

        public void agregar (View view){

        int valueInpx = 52;
        int valueInDp= (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, valueInpx , getResources()
                        .getDisplayMetrics());

        el.setVisibility(View.VISIBLE);
        contadorF++;
        contadorA++;

        contadorFx++;
        contadorAx++;

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        fuerzax.add(new EditText(this));
        angulox.add(new EditText(this));

        fuerzax.get(contadorFx).setTextSize(24);
        angulox.get(contadorAx).setTextSize(24);

        param.leftMargin=valueInDp;
        param.topMargin=valueInDp;
        param.rightMargin=valueInDp;

        fuerzax.get(contadorFx).setLayoutParams(param);
        angulox.get(contadorAx).setLayoutParams(param);

        fuerzax.get(contadorFx).setEms(10);
        angulox.get(contadorAx).setEms(10);

        fuerzax.get(contadorFx).setInputType(InputType.TYPE_CLASS_NUMBER);
        angulox.get(contadorAx).setInputType(InputType.TYPE_CLASS_NUMBER);

        fuerzax.get(contadorFx).setHint("fuerza " + (contadorF + 1));
        angulox.get(contadorAx).setHint("angulo " + (contadorA + 1));

        layoutfuerza.addView(fuerzax.get(contadorFx));
        layoutangulo.addView(angulox.get(contadorAx));


        fuerzaE.add(fuerzax.get(contadorFx));
        anguloE.add(angulox.get(contadorAx));

    }

    public void eliminar (View view){

        if (contadorF<3){

            el.setVisibility(View.GONE);

        }
        layoutfuerza.removeView(fuerzax.get(contadorFx));
        layoutangulo.removeView(angulox.get(contadorAx));
        fuerzaE.remove(contadorF);
        anguloE.remove(contadorA);
        contadorA--;
        contadorF--;
        contadorFx--;
        contadorAx--;
    }

    @SuppressLint("SetTextI18n")
    public void Calcular(View view) {

        Rx = 0;
        Ry = 0;
        fmaxy = 0;
        fmaxx = 0;
        fminy = 0;
        fminx = 0;
        fuerzaX = new double[fuerzaE.size()];
        fuerzaY = new double[fuerzaE.size()];
        caminoX = new double[fuerzaE.size()];
        caminoY = new double[fuerzaE.size()];
        BG.setAlpha(1);

        String resultado1 = "Resultado:";
        String resultado2 = "";

        for (int i = 0; i < fuerzaE.size(); i++){

            String fuerzaS;
            String anguloS;
            double fuerzaI;
            double anguloI;

            fuerzaS = fuerzaE.get(i).getText().toString();
            anguloS = anguloE.get(i).getText().toString();

            Log.i("calculo", anguloS);

            fuerzaI = Double.parseDouble(fuerzaS);
            anguloI = Double.parseDouble(anguloS);


            double angulo_radines = Math.toRadians(anguloI);

            Log.i("calculo", "radianes " + angulo_radines);

            Log.i("calculo", "radianes2 " + (Math.PI/2));

            double prohibido = Math.PI/2;


            double coseno = Math.cos(angulo_radines);

            Log.i("calculo", "coseno " + coseno);

            double seno = Math.sin(angulo_radines);

            fuerzaX[i] = fuerzaI*coseno;
            fuerzaY[i] = fuerzaI*seno;

            if (angulo_radines%(prohibido*2) == 0){

                fuerzaY[i] = 0;

            }

            else if (angulo_radines%prohibido == 0){

                fuerzaX[i] = 0;

            }

            if(i > 0){
            caminoX[i] = caminoX[i-1]+fuerzaX[i];

            caminoY[i] = caminoY[i-1]+fuerzaY[i];}
            else{caminoX[i] = fuerzaX[i];
            caminoY[i]= fuerzaY[i];}


            Rx += fuerzaX[i];
            Ry += fuerzaY[i];

            resultado1 = resultado1 + "\n   Fx" + (i + 1) + " = " + fuerzaX[i];
            resultado2 = resultado2 + "\n   Fy" + (i + 1) + " = " + fuerzaY[i];

            if (fmaxx < fuerzaX[i]) {
                fmaxx = fuerzaX[i];
            }

            if (fmaxy < fuerzaY[i]) {
                fmaxy = fuerzaY[i];
            }

            if (fminx > fuerzaX[i]) {
                fminx = fuerzaX[i];
            }

            if (fminy > fuerzaY[i]) {
                fminy = fuerzaY[i];
            }


        }


        String resultado3 = "\n   Rx = " + Rx;
        String resultado4 = "\n   Ry = " + Ry;

        double Rt = Math.sqrt(Math.pow(Ry, 2) + Math.pow(Rx, 2));

        String resultado5 = "\n   Rt = " + Rt;

        double Rtang = Ry/Rx;
        double Rradian = Math.toRadians(Rtang);
        double Rangulo = Math.atan(Rradian);

        String resultado6 = "\n   R⊾ = " + Rangulo;

        r.setText(resultado1 + resultado2 + resultado3 + resultado4 + resultado5 + resultado6);

        on = true;

    }
}