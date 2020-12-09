package edu.neu.madcourse.decisionjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import java.sql.Date;
import java.time.LocalDate;

import edu.neu.madcourse.decisionjournal.dao.AsyncRecordRepository;


/**
 * MainActivity is the activity where user can browse previous decision entries.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout pickDateArea;
    private AppBarLayout appBarLayout;
    private CalendarView calendarView;
    boolean appbarExpanded = false;

    private LocalDate selectedDate = LocalDate.now();
    private TextView selectedDateTextView;

    private RecyclerView.LayoutManager recyclerLayoutManager;
    private RecyclerView recordRecyclerView;
    private RecordRecyclerAdapter recordRecyclerAdapter;

    private AsyncRecordRepository recordRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickDateArea = findViewById(R.id.pick_date_area);
        appBarLayout = findViewById(R.id.app_bar_layout);
        calendarView = findViewById(R.id.calendar_view);
        selectedDateTextView = findViewById(R.id.toolbar_date_display);
        appBarLayout.setExpanded(appbarExpanded);

        recordRecyclerView = findViewById(R.id.record_recycler_view);

        recordRepository = new AsyncRecordRepository(getApplicationContext());

        setupCalendarListener();
        // Initialize text view to select today's date. Calendarview by default initialize to today's
        // date.
        setToolbarDate();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recordRecyclerAdapter = new RecordRecyclerAdapter();
        recyclerLayoutManager = new LinearLayoutManager(this);
        recordRecyclerView.setAdapter(recordRecyclerAdapter);
        recordRecyclerView.setLayoutManager(recyclerLayoutManager);

        handleDateChanged();

        // Code for: Debug recyclerview
//        Record r1 = new Record(DecisionEnum.WORKOUT, EmoEnum.HAPPY, currDate);
//        Record r2 = new Record(DecisionEnum.STUDY, EmoEnum.HAPPY, new Date(currDate.getTime() + 1000));
//        List<Record> list = new ArrayList<>();
//        list.add(r1);
//        list.add(r2);
//        recordRecyclerAdapter.submitList(list);

    }

    // Observe new date when selected date changes.
    private void handleDateChanged() {
        Date currDate = Date.valueOf(selectedDate.toString());
        recordRepository.getRecordOnDate(currDate).observe(this, records -> {
            recordRecyclerAdapter.submitList(records);
        });
    }

    private void setupCalendarListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectedDate = LocalDate.of(i, i1 + 1, i2);
                setToolbarDate();
                handleDateChanged();
                // calendarview's date wont change upon date selection, only this listener is called.
//                LocalDate newDate = Instant.ofEpochMilli(calendarView.getDate()).atZone(ZoneId.systemDefault()).toLocalDate();
//                Log.i(TAG, String.format("in date listener, new date: %s", newDate.toString()));
            }
        });
    }

    private void setToolbarDate() {
        this.selectedDateTextView.setText(selectedDate.toString());
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pick_date_area:
                appbarExpanded = !appbarExpanded;
                appBarLayout.setExpanded(appbarExpanded, true);
                break;
        }
    }
}