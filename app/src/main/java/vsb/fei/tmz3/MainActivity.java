package vsb.fei.tmz3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
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
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button saveButton;
    int sliderRequest = 0;
    int historyRequest = 1;
    int chartRequest = 2;
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

        this.saveButton = (Button) findViewById(R.id.saveButton);
        this.saveButton.setOnClickListener(v -> this.save());

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
        this.load();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.chartTypeMenu) {

        }

        if(id == R.id.historyMenu) {
            this.historyActivity();
        }

        if(id == R.id.sliderValuesMenu) {
            this.sliderActivity();
        }
        return true;
    }

    public void changeChart() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == this.sliderRequest && resultCode == RESULT_OK) {
            this.processSliderRequest(data);
        }

        if(requestCode == this.historyRequest && resultCode == RESULT_OK) {
            return;
        }

        if(requestCode == this.chartRequest && resultCode == RESULT_OK) {

        }
    }

    public void sliderActivity() {
        Intent intent = new Intent(this, SliderValuesActivity.class);
        intent.putExtra("urokMin", this.urok.getValueFrom());
        intent.putExtra("urokMax", this.urok.getValueTo());
        intent.putExtra("vkladMin", this.vklad.getValueFrom());
        intent.putExtra("vkladMax", this.vklad.getValueTo());
        intent.putExtra("obdobiMin", this.obdobi.getValueFrom());
        intent.putExtra("obdobiMax", this.obdobi.getValueTo());
        startActivityForResult(intent, this.sliderRequest);
    }

    public void chartActivity() {
        Intent intent = new Intent(this, ChartTypeActivity.class);
        startActivityForResult(intent, this.chartRequest);
    }

    public void historyActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void processSliderRequest(Intent data) {
        float urokMin = data.getFloatExtra("urokMin", 0);
        float urokMax = data.getFloatExtra("urokMax", 0);
        urok.setValue(urokMin);
        urok.setValueFrom(urokMin);
        urok.setValueTo(urokMax);

        float vkladMin = data.getFloatExtra("vkladMin", 0);
        float vkladMax = data.getFloatExtra("vkladMax", 0);
        vklad.setValue(vkladMin);
        vklad.setValueFrom(vkladMin);
        vklad.setValueTo(vkladMax);

        float minObdobi = data.getFloatExtra("obdobiMin", 0);
        float maxObdobi = data.getFloatExtra("obdobiMax", 0);
        obdobi.setValue(minObdobi);
        obdobi.setValueFrom(minObdobi);
        obdobi.setValueTo(maxObdobi);
    }

    public void save() {
        FinanceData data = new FinanceData(this.sumaF, this.vkladF, this.urokF, this.obdobiF);
        SharedPreferences sharedPreferences = getSharedPreferences("TAMZ3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Date date = new Date();
        String json = gson.toJson(data);
        editor.putString(date.toString(), json);
        editor.commit();
    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("TAMZ3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Map<String, ?> keys = sharedPreferences.getAll();
        if ( keys.size() == 0 )
            return;

        String s = keys.values().toArray()[keys.values().size()-1].toString();
        FinanceData data = gson.fromJson(s, FinanceData.class);
        Log.w("Data suma", String.valueOf(data.getSuma()));
        this.sumaF = data.getSuma();
        this.vkladF = data.getVklad();
        this.obdobiF = data.getObdobi();
        this.urokF = data.getUrok();
        //this.calculateSum();
        this.vklad.setValue((float) this.vkladF);
        this.obdobi.setValue((float) this.obdobiF);
        this.urok.setValue((float) this.urokF);
        this.setVklad();
        this.setObdobi();
        this.setUrok();
        this.setHeader();
        this.setChart();
    }
}









