package edu.neu.madcourse.decisionjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.sql.Date;
import java.time.LocalDate;

import edu.neu.madcourse.decisionjournal.model.DecisionEnum;
import edu.neu.madcourse.decisionjournal.model.EmoEnum;
import edu.neu.madcourse.decisionjournal.model.Record;

import static edu.neu.madcourse.decisionjournal.model.DecisionEnum.WORKOUT;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;

    private Date test_date = Date.valueOf(LocalDate.of(2020, 02,22).toString());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(getApplicationContext());
        database.recordDao().insert(new Record(WORKOUT, EmoEnum.HAPPY, test_date));
    }
}