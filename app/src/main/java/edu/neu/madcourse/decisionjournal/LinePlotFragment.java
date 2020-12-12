package edu.neu.madcourse.decisionjournal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.neu.madcourse.decisionjournal.dao.AsyncRecordRepository;
import edu.neu.madcourse.decisionjournal.model.DecisionEnum;
import edu.neu.madcourse.decisionjournal.model.Record;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinePlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinePlotFragment extends Fragment {

    private AsyncRecordRepository recordRepository;

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
        recordRepository = new AsyncRecordRepository(this.getActivity().getApplicationContext());

        lineChart = view.findViewById(R.id.line_emotion);

//        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");
//        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(), "Data Set 2");
//
//        lineDataSet1.setColor(ColorTemplate.JOYFUL_COLORS[0]);
//        lineDataSet1.setLineWidth(4);
//        lineDataSet2.setColor(ColorTemplate.JOYFUL_COLORS[1]);
//        lineDataSet2.setLineWidth(4);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(lineDataSet1);
//        dataSets.add(lineDataSet2);
//
//        LineData lineData = new LineData(dataSets);
//
//        String[] labels = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven"};
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        lineChart.setData(lineData);
//
//        lineChart.setNoDataText("No record found!");
//        lineChart.setDrawGridBackground(true);
//        lineChart.setGridBackgroundColor(Color.rgb(232, 234, 246));
//        lineChart.setDrawBorders(true);
//        Description description = new Description();
//        description.setText("Your emotion curves for last 7 days");
//        description.setTextSize(20);
//        description.setTextColor(Color.rgb(26, 35, 126));
//        lineChart.setDescription(description);
//
//        LegendEntry[] legendEntries = new LegendEntry[2];
//        for (int i = 0; i < legendEntries.length; i++) {
//            LegendEntry entry = new LegendEntry();
//            entry.formColor = lineData.getColors()[i];
//            entry.label = "data " + i;
//            legendEntries[i] = entry;
//        }
//        Legend legend = lineChart.getLegend();
//        legend.setCustom(legendEntries);
//
//
//        lineChart.invalidate();
    }

    private LineChart lineChart;

    @Override
    public void onResume() {
        super.onResume();
        Date day = Date.valueOf(LocalDate.now().toString());

        recordRepository.getSevenDays(day).observe(this, records -> {
            int size = records == null ? 0 : records.size();
            Log.v("ReadDay", " " + size + " ");

            double MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
            // date, index
            Map<String, Integer> indexMap = new HashMap<>();

            Collections.sort(records, (a, b) -> a.date.compareTo(b.date));

            Date day_temp = Date.valueOf(LocalDate.now().toString());
            for (int i = 0; i < 7; i++) {
                indexMap.put(day_temp.toString(), 6 - i);
                day_temp = new Date(day_temp.getTime() - (long) MILLIS_IN_A_DAY);
                Log.v("date:", day_temp.toString());
            }


            String[] decisionNames = new String[]{"Eat", "Study", "Shop", "Game", "Music", "Workout"};


            // date, count
            Map<String, Integer>[] decisionMap = new HashMap[6];
            for (int i = 0; i < 6; i++) {
                decisionMap[i] = new HashMap();
            }

            for (Record record : records) {
                Log.v("r:", record.date.toString() + " " + record.decision);
                int count = decisionMap[record.decision.getVal() - 1].getOrDefault(record.date.toString(), 0);
                decisionMap[record.decision.getVal() - 1].put(record.date.toString(), count + 1);
            }

            ArrayList<Entry>[] data = new ArrayList[6];

            for (int i = 0; i < 6; i++) {
                data[i] = new ArrayList<>();
            }

            List<String> dates = new ArrayList<>();
            dates.addAll(indexMap.keySet());
            Collections.sort(dates);
            for (String date : dates) {
                // iterate over 6 decisions
                for (int i = 0; i < 6; i++) {
                    data[i].add(new Entry(indexMap.get(date), decisionMap[i].getOrDefault(date, 0)));
                }
                Log.v("check array", "" + data[0].toString());
            }

            int[] colors = new int[]{
                    Color.argb(0.99f, 0.0f, 0.4470f, 0.7410f),
                    Color.argb(0.7f, 0.8500f, 0.3250f, 0.0980f),
                    Color.argb(0.7f, 0.9290f, 0.6940f, 0.1250f),
                    Color.argb(0.7f, 0.4940f, 0.1840f, 0.5560f),
                    Color.argb(0.7f, 0.4660f, 0.6740f, 0.1880f),
                    Color.argb(0.7f, 0.3010f, 0.7450f, 0.9330f)};
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            float[] lineLength = new float[]{3, 5, 7, 11, 13, 9};
            float[] spaceLength = new float[]{5, 3, 7, 6, 5, 9};
            float[] width = new float[]{2f, 2, 3f, 3, 4f, 4f};

            for (int i = 0; i < 6; i++) {
                LineDataSet lineDataSet = new LineDataSet(data[i], decisionNames[i]);
                lineDataSet.setColor(colors[i]);
                lineDataSet.setLineWidth(width[i]);
                lineDataSet.enableDashedLine(lineLength[i] * 10, spaceLength[i] * 10, 0);
                dataSets.add(lineDataSet);
            }

            LineData lineData = new LineData(dataSets);
            lineChart.setData(lineData);

            Description description = new Description();
            description.setText("Your decisions in last 7 days");
            description.setTextSize(20);
            description.setTextColor(Color.rgb(26, 35, 126));
            lineChart.setDescription(description);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
//            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


            Legend legend = lineChart.getLegend();
            legend.setTextSize(15);
            lineChart.invalidate();

        });
    }

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