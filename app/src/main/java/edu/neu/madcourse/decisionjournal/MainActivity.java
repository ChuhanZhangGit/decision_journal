package edu.neu.madcourse.decisionjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import java.time.LocalDate;


/**
 * MainActivity is the activity where user can browse previous decision entries.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppDatabase database;
    private LinearLayout pickDateArea;
    private AppBarLayout appBarLayout;
    private CalendarView calendarView;
    boolean appbarExpanded = false;

    private LocalDate selectedDate;
    private TextView selectedDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickDateArea = findViewById(R.id.pick_date_area);
        appBarLayout = findViewById(R.id.app_bar_layout);
        calendarView = findViewById(R.id.calendar_view);
        selectedDateTextView = findViewById(R.id.toolbar_date_display);
        appBarLayout.setExpanded(appbarExpanded);

        setupCalendarListener();
        // Initial calendar view to select today's date.
        calendarView.setDate(0);
        calendarView.setDate(System.currentTimeMillis());
    }

    private void setupCalendarListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectedDate = LocalDate.of(i, i1+1, i2);
                setToolbarDate();
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