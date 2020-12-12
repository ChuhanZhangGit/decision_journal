package edu.neu.madcourse.decisionjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import edu.neu.madcourse.decisionjournal.dao.AsyncRecordRepository;


/**
 * MainActivity is the activity where user can browse previous decision entries.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppBarLayout appBarLayout;
    private CalendarView calendarView;
    boolean appbarExpanded = false;

    private LocalDate selectedDate = LocalDate.now();
    private TextView selectedDateTextView;

    private RecyclerView recordRecyclerView;
    private RecordRecyclerAdapter recordRecyclerAdapter;

    private AsyncRecordRepository recordRepository;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener mSensorListener;

    DialogFragment catDialog = CatDialogFragment.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appBarLayout = findViewById(R.id.app_bar_layout);
        calendarView = findViewById(R.id.calendar_view);
        selectedDateTextView = findViewById(R.id.toolbar_date_display);

        appBarLayout.setExpanded(appbarExpanded);

        recordRecyclerView = findViewById(R.id.record_recycler_view);

        recordRepository = new AsyncRecordRepository(getApplicationContext());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorListener = new MotionSensorListener();
        sensorManager.registerListener(mSensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        setupCalendarListener();
        // Initialize text view to select today's date. Calendarview by default initialize to today's
        // date.
        setToolbarDate();
        setUpRecyclerView();
        Log.i(TAG, "after onCreate");
    }

    /**
     * Listener for accelerometer sensor which listen to device shakes.
     */
    private class MotionSensorListener implements SensorEventListener {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
        private final float alpha = 0.8f;
        float[] gravity = new float[]{0, 0, 0};
        float[] linear_acceleration = new float[3];
        private float lastAcceleration = 0;
        private float currAcceleration = 0;
        private int THRESHOLD = 12;
        private boolean firstReading = true;

        @Override
        public void onSensorChanged(SensorEvent event) {

            // Isolate the force of gravity with the low-pass filter.
//            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
//            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
//            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            // Remove the gravity contribution with the high-pass filter.
//            float ax = event.values[0] - gravity[0];
//            float ay = event.values[1] - gravity[1];
//            float az = event.values[2] - gravity[2];
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2] - 9.85f;
//            Log.i(TAG, String.format("gravity read in xyz: %f, %f, %f", gravity[0], gravity[1],
//                    gravity[2]));
//            Log.i(TAG, String.format("adjusted accerlation read in xyz: %f, %f, %f",
//                    linear_acceleration[0], linear_acceleration[1], linear_acceleration[2]));
            lastAcceleration = currAcceleration;
            currAcceleration = ax * ax + ay * ay + az * az;
            Log.i(TAG, String.format("Current accelation: %f, previous acceleration: %f",
                    currAcceleration, lastAcceleration));
            if (!firstReading && currAcceleration - lastAcceleration > THRESHOLD * THRESHOLD) {
                Log.i(TAG, String.format("Triggered when Current accelation: %f, previous acceleration: %f",
                        currAcceleration, lastAcceleration));
                launchCatDialog();
            }
            firstReading = false;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private void setUpRecyclerView() {
        recordRecyclerAdapter = new RecordRecyclerAdapter();
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
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
                // calendarview's setdate/getdate wont change upon date selection, only this listener is called.
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
            case R.id.fab:
                startActivity(new Intent(this, CreateActivity.class));
                break;
            case R.id.cat_dialog_bn:
                launchCatDialog();
                break;
            case R.id.plot_activity_bn:
                startActivity(new Intent(this, PlotActivity.class));

        }
    }

    private void launchCatDialog() {
        if (catDialog.getDialog() == null ||
                !catDialog.getDialog().isShowing()) {
            catDialog.show(getSupportFragmentManager(), "cat dialog");
        }
    }
}