package vsb.fei.tmz3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.slider.Slider;

import java.lang.reflect.Array;
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
        chart.getDescription().setEnabled(false);
        Legend l = chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors
        float[] intervals = new float[]{ 3, 18 };
        DashPathEffect dp = new DashPathEffect(intervals, 5);

        List<LegendEntry> legendEntries = new ArrayList<LegendEntry>();
        legendEntries.add(new LegendEntry("Suma", Legend.LegendForm.CIRCLE, 10, 10, dp, Color.RED));
        legendEntries.add(new LegendEntry("Vklad", Legend.LegendForm.CIRCLE, 10, 10, dp, Color.BLUE));
        legendEntries.add(new LegendEntry("Úroky", Legend.LegendForm.CIRCLE, 10, 10, dp, Color.GREEN));
        l.setCustom(legendEntries);

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
        textViewUrok.setText("Úrok: " + (float)urokF);
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
        entries.add(new BarEntry(1, (int)sumaF));
        entries.add(new BarEntry(2, (int)vkladF));
        int tmp = (int) (sumaF - vkladF);
        entries.add(new BarEntry(3, tmp));
        BarDataSet dataSet = new BarDataSet(entries, "Vklad");

        dataSet.setValueTextColor(Color.BLUE);
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        dataSet.setColors(colors);
        BarData data = new BarData(dataSet);
        data.setDrawValues(false);
        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}