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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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

        LineDataSet lineDataSet1 = new LineDataSet(dataValues(),"Data Set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private LineChart lineChart;
    private ArrayList<Entry> dataValues() {
        ArrayList<Entry> dataVal = new ArrayList<>();
        dataVal.add(new Entry(0, 20));
        dataVal.add(new Entry(1, 24));
        dataVal.add(new Entry(2, 50));
        dataVal.add(new Entry(3, 10));
        dataVal.add(new Entry(4, 28));
        return dataVal;
    }

}