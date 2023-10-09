package vsb.fei.tmz3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BarChart chart;
    Slider vklad;
    TextView textViewVklad;
    Slider urok;
    TextView textViewUrok;
    Slider obdobi;
    TextView textViewObdobi;
    TextView suma;
    TextView urokySuma;

    double obdobiF;
    double vkladF;
    double urokF;
    double sumaF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (BarChart) findViewById(R.id.chart);

        vklad = (Slider) findViewById(R.id.SliderVklad);
        textViewVklad = (TextView) findViewById(R.id.textViewVklad);
        this.setVklad();

        urok = (Slider) findViewById(R.id.SliderUrok);
        textViewUrok = (TextView) findViewById(R.id.textViewUrok);
        this.setUrok();

        obdobi = (Slider) findViewById(R.id.SliderObdobi);
        textViewObdobi = (TextView) findViewById(R.id.textViewObdobi);
        this.setObdobi();

        this.calculateSum();

        suma = (TextView) findViewById(R.id.sumaTextView);

        urokySuma = (TextView) findViewById(R.id.urokyTextView);

        this.setHeader();

        this.setChart();

        vklad.addOnChangeListener( (slider, value, fromUser) -> {
            setVklad();
            calculateSum();
            setChart();
            setHeader();
        });

        urok.addOnChangeListener((slider, value, fromUser) -> {
            setUrok();
            calculateSum();
            setChart();
            setHeader();
        });

        obdobi.addOnChangeListener((slider, value, fromUser) -> {
            setObdobi();
            calculateSum();
            setChart();
            setHeader();
        });
    }

    void calculateSum() {
        this.sumaF = vkladF * (Math.pow((1+(urokF/100)), obdobiF));
    }

    void setUrok() {
        urokF = urok.getValue();
        textViewUrok.setText("Urok: " + (float)urokF);
    }

    void setObdobi() {
        obdobiF = obdobi.getValue();
        textViewObdobi.setText("Období: " + (float)obdobiF);
    }

    void setHeader() {
        suma.setText("Naspořená suma: " + (int)sumaF);
        urokySuma.setText("Z toho úroky: " + (int)(sumaF - vkladF));
    }

    void setVklad() {
        vkladF = vklad.getValue();
        textViewVklad.setText("Vklad: " + (int)vkladF);
    }

    void setChart() {
        List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(1, (int)vkladF));
        int tmp = (int) (sumaF - vkladF);
        entries.add(new BarEntry(2, tmp));
        BarDataSet dataSet = new BarDataSet(entries, "Vklad");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLUE);
        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.invalidate();
    }
}