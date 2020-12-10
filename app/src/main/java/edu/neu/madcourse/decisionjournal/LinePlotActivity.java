package edu.neu.madcourse.decisionjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LinePlotActivity extends AppCompatActivity {
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_plot);
        lineChart = findViewById(R.id.line_plot);

        LineDataSet lineDataSet1 = new LineDataSet(dataValues(),"Data Set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }

    private ArrayList<Entry> dataValues() {
        ArrayList<Entry> dataVal = new ArrayList<>();
        dataVal.add(new Entry(0, 20));
        dataVal.add(new Entry(1, 24));
        dataVal.add(new Entry(2, 2));
        dataVal.add(new Entry(3, 10));
        dataVal.add(new Entry(4, 28));

        return dataVal;
    }
}