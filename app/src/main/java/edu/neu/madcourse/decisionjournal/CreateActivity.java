package edu.neu.madcourse.decisionjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.sql.Date;
import java.time.LocalDate;

import edu.neu.madcourse.decisionjournal.model.DecisionEnum;
import edu.neu.madcourse.decisionjournal.model.EmoEnum;
import edu.neu.madcourse.decisionjournal.model.Record;

/**
 * CreateActivity is the activity for user to create decision entry.
 */
public class CreateActivity extends AppCompatActivity {
    private final String TAG = "CreateActivity";
    private AppDatabase database;

    private Date test_date = Date.valueOf(LocalDate.of(2020, 02,22).toString());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Log.v(TAG,"onCreate");

        database = AppDatabase.getDatabase(getApplicationContext());
        Log.i(TAG, "before insert");
        database.recordDao().insert(new Record(DecisionEnum.WORKOUT, EmoEnum.HAPPY, test_date));
    }
}