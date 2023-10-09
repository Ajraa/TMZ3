package vsb.fei.tmz3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BarChart chart;
    List<BarEntry> entries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (BarChart) findViewById(R.id.chart);
        entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(5, 5));
        entries.add(new BarEntry(4, 4));
        entries.add(new BarEntry(3, 3));
        entries.add(new BarEntry(2, 2));
        BarDataSet dataSet = new BarDataSet(entries, "Label");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLUE);
        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.invalidate();
    }
}