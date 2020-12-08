package edu.neu.madcourse.decisionjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlotActivity extends AppCompatActivity {

    ArrayList<BarEntry> entries;
    ArrayList labels;

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        barChart = findViewById(R.id.barchart);

        entries = new ArrayList<>();
        entries.add(new BarEntry(1, 3));
        entries.add(new BarEntry(1, 1));

        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry(3, 2));
        entries2.add(new BarEntry(3, 4));

        labels = new ArrayList();
        labels.add("one");
        labels.add("two");


        BarDataSet dataSet = new BarDataSet(entries, "Entries");
        BarDataSet dataSet2 = new BarDataSet(entries2, "Entries2");

        barChart.animateY(5000);
//        BarDataSet lb = new BarDataSet(labels, "lbl");
        BarData barData = new BarData(dataSet, dataSet2);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);
        barChart.setData(barData);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(13f);
        xAxis.setLabelCount(5);
        xAxis.setCenterAxisLabels(true);


    }
}