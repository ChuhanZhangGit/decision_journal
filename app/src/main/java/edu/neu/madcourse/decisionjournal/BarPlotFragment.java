package edu.neu.madcourse.decisionjournal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import edu.neu.madcourse.decisionjournal.dao.AsyncRecordRepository;
import edu.neu.madcourse.decisionjournal.model.Record;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BarPlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarPlotFragment extends Fragment {

    BarChart barChart;
    private AsyncRecordRepository recordRepository;

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

        recordRepository = new AsyncRecordRepository(this.getActivity().getApplicationContext());
        barChart = view.findViewById(R.id.bar_emotion);
    }

    @Override
    public void onResume() {
        super.onResume();

        BarDataSet barDataSet1 = new BarDataSet(dataStacked(), "Emotion counts");

        barDataSet1.setStackLabels(new String[]{"Sad", "Neutral", "Happy"});
        barDataSet1.setColors(getColors());
        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);

        getData();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getLabels()));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setData(barData);
        barChart.invalidate();

    }

    private int[] getColors() {
        return new int[]{ColorTemplate.JOYFUL_COLORS[0],
                ColorTemplate.JOYFUL_COLORS[1],
                ColorTemplate.JOYFUL_COLORS[2]};
    }

    private static final double MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    private String[] getLabels() {
        Date day = Date.valueOf(LocalDate.now().toString());
        String[] xLabel = new String[7];
        //https://stackoverflow.com/questions/7672597/how-to-get-timezone-from-android-mobile
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        for (int i = 0; i < 7; i++) {
            calendar.setTime(day);
            int month = calendar.get(Calendar.MONTH) + 1;
            int dayNum = calendar.get(Calendar.DAY_OF_MONTH);

            xLabel[6 - i] = dayNum + "/" + month;
            // offset to previous day
            day = new Date(day.getTime() - (long) MILLIS_IN_A_DAY);
        }

        return xLabel;
    }

    private ArrayList<BarEntry> getData() {
        BarEntry[] entries = new BarEntry[7];
        Date day = Date.valueOf(LocalDate.now().toString());

        recordRepository.getSevenDays(day).observe(this, records -> {
            int size = records == null ? 0 : records.size();
            Log.v("ReadDay", " " + size + " ");
            for (Record record : records) {
                Log.v("r:", record.date.toString() + " " + record.decision);
            }
        });

        ArrayList<BarEntry> data = new ArrayList<>();
        return data;
    }

    private ArrayList<BarEntry> dataStacked() {
        ArrayList<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(0, new float[]{1, 1, 2}));
        data.add(new BarEntry(1, new float[]{0, 1, 2}));
        data.add(new BarEntry(2, new float[]{3, 1, 0}));
        data.add(new BarEntry(3, new float[]{2, 0, 2}));
        data.add(new BarEntry(4, new float[]{2, 0, 2}));
        data.add(new BarEntry(5, new float[]{2, 0, 5}));
        data.add(new BarEntry(6, new float[]{2, 0, 6}));

        return data;
    }

}