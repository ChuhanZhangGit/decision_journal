package edu.neu.madcourse.decisionjournal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinePlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinePlotFragment extends Fragment {

    public LinePlotFragment() {
        // Required empty public constructor
    }

    public static LinePlotFragment newInstance(String param1, String param2) {
        LinePlotFragment fragment = new LinePlotFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_line_plot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.btn_plot_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LinePlotFragment.this)
                        .navigate(R.id.action_to_bar_plot);
            }
        });

        view.findViewById(R.id.line_to_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        lineChart = view.findViewById(R.id.line_emotion);

        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");
        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(), "Data Set 2");

        lineDataSet1.setColor(ColorTemplate.JOYFUL_COLORS[0]);
        lineDataSet2.setColor(ColorTemplate.JOYFUL_COLORS[1]);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        LineData lineData = new LineData(dataSets);


        lineChart.setData(lineData);


//        lineChart.setBorderWidth(20);
//        lineChart.setPadding(10,20,20,10);
        lineChart.setNoDataText("No record found!");
        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(Color.rgb(232, 234, 246));
        lineChart.setDrawBorders(true);
        Description description = new Description();
        description.setText("Your emotion curves for last 7 days");
        description.setTextSize(20);
        description.setTextColor(Color.rgb(26, 35, 126));
        lineChart.setDescription(description);

        LegendEntry[] legendEntries = new LegendEntry[2];
        for (int i = 0; i < legendEntries.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = lineData.getColors()[i];
            entry.label = "data " + i;
            legendEntries[i] = entry;
        }
        Legend legend = lineChart.getLegend();
        legend.setCustom(legendEntries);


        lineChart.invalidate();
    }

    private LineChart lineChart;

    private ArrayList<Entry> dataValues1() {
        ArrayList<Entry> dataVal = new ArrayList<>();
        dataVal.add(new Entry(0, 20));
        dataVal.add(new Entry(1, 24));
        dataVal.add(new Entry(2, 50));
        dataVal.add(new Entry(3, 10));
        dataVal.add(new Entry(4, 28));
        return dataVal;
    }

    private ArrayList<Entry> dataValues2() {
        ArrayList<Entry> dataVal = new ArrayList<>();
        dataVal.add(new Entry(0, 10));
        dataVal.add(new Entry(2, 34));
        dataVal.add(new Entry(3, 40));
        dataVal.add(new Entry(5, 20));
        dataVal.add(new Entry(6, 38));
        return dataVal;
    }

}