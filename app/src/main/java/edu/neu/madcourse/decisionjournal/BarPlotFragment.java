package edu.neu.madcourse.decisionjournal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BarPlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarPlotFragment extends Fragment {

    BarChart barChart;

    public BarPlotFragment() {
        // Required empty public constructor
    }

    public static BarPlotFragment newInstance(String param1, String param2) {
        BarPlotFragment fragment = new BarPlotFragment();
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
        return inflater.inflate(R.layout.fragment_bar_plot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_plot_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BarPlotFragment.this)
                        .navigate(R.id.action_to_line_plot);
            }
        });

        view.findViewById(R.id.bar_to_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        barChart = view.findViewById(R.id.bar_emotion);

        BarDataSet barDataSet1 = new BarDataSet(dataStacked(), "Data Set 1");
        BarDataSet barDataSet2 = new BarDataSet(dataValues2(), "Data Set 2");



        barDataSet1.setColors(new int[]{ColorTemplate.JOYFUL_COLORS[0],ColorTemplate.JOYFUL_COLORS[1],ColorTemplate.JOYFUL_COLORS[2]});
        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);
//        barData.addDataSet(barDataSet2);

        String[] labels = new String[]{"One", "Two", "Three", "Four"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setData(barData);
        barChart.invalidate();


    }

    private ArrayList<BarEntry> dataStacked() {
        ArrayList<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(0, new float[]{1, 1, 2}));
        data.add(new BarEntry(1, new float[]{0, 1, 2}));
        data.add(new BarEntry(2, new float[]{3, 1, 0}));
        data.add(new BarEntry(3, new float[]{2, 0, 2}));

        return data;
    }

    private ArrayList<BarEntry> dataValues1() {
        ArrayList<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(0, 3));
        data.add(new BarEntry(1, 4));
        data.add(new BarEntry(2, 6));
        data.add(new BarEntry(3, 10));
        return data;
    }

    private ArrayList<BarEntry> dataValues2() {
        ArrayList<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(0, 3));
        data.add(new BarEntry(1, 8));
        data.add(new BarEntry(2, 3));
        data.add(new BarEntry(3, 7));
        return data;
    }
}