package edu.neu.madcourse.decisionjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Date;
import java.time.LocalDate;

import edu.neu.madcourse.decisionjournal.dao.AsyncRecordRepository;
import edu.neu.madcourse.decisionjournal.model.DecisionEnum;
import edu.neu.madcourse.decisionjournal.model.EmoEnum;
import edu.neu.madcourse.decisionjournal.model.Record;

/**
 * CreateActivity is the activity for user to create decision entry.
 */
public class CreateActivity extends AppCompatActivity {
    private final String TAG = "CreateActivity";
    private AsyncRecordRepository repo;

    private Date test_date = Date.valueOf(LocalDate.of(2020, 02, 22).toString());

    private RadioGroup decisionTopGroup;
    private RadioGroup decisionBottomGroup;
    private RadioGroup emotionGroup;


    /* Below is a hacky way to combine two radiogroup to one. If not set listener to null, would
    result in recursive calling between two listener and stack overflow.
     */
    private RadioGroup.OnCheckedChangeListener topListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                decisionBottomGroup.setOnCheckedChangeListener(null);
                decisionBottomGroup.clearCheck();
                decisionBottomGroup.setOnCheckedChangeListener(bottomListener);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener bottomListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                decisionTopGroup.setOnCheckedChangeListener(null);
                decisionTopGroup.clearCheck();
                decisionTopGroup.setOnCheckedChangeListener(topListener);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Log.v(TAG, "onCreate");

        repo = new AsyncRecordRepository(getApplicationContext());
        decisionTopGroup = findViewById(R.id.top_radioGroup);
        decisionBottomGroup = findViewById(R.id.bottom_radioGroup);
        emotionGroup = findViewById(R.id.emotion_radioGroup);

        decisionTopGroup.setOnCheckedChangeListener(topListener);
        decisionBottomGroup.setOnCheckedChangeListener(bottomListener);
    }



    private Record generateRecord(EmoEnum emotion, DecisionEnum decision, String comment) {
        Date curr = new Date(System.currentTimeMillis());
        if (comment == null || comment.length()==0) {
            return new Record(decision, emotion, curr);
        } else {
            return new Record(decision, emotion, curr, comment);
        }
    }

    private boolean userInputIsValid() {
        return (decisionTopGroup.getCheckedRadioButtonId() != -1 ||
                decisionBottomGroup.getCheckedRadioButtonId() != -1) &&
                emotionGroup.getCheckedRadioButtonId() != -1;
    }

    private DecisionEnum idToDecisionEnum(int decisionBnId) {
        switch (decisionBnId) {
            case R.id.decision_workout:
                return DecisionEnum.WORKOUT;
            case R.id.decision_study:
                return DecisionEnum.STUDY;
            case R.id.decision_eat:
                return DecisionEnum.EAT;
            case R.id.decision_music:
                return DecisionEnum.MUSIC;
            case R.id.decision_game:
                return DecisionEnum.GAME;
            case R.id.decision_shop:
                return DecisionEnum.SHOP;
            default:
                return DecisionEnum.NONE;
        }
    }

    private EmoEnum idToEmoEnum(int emotionBnId) {
        switch (emotionBnId) {
            case R.id.emotion_positive:
                return EmoEnum.HAPPY;
            case R.id.emotion_neutral:
                return EmoEnum.NEUTRAL;
            case R.id.emotion_negative:
                return EmoEnum.SAD;
            default:
                return EmoEnum.NONE;
        }
    }

    private void handleAddDecision() {
        if (userInputIsValid()) {
            int emotionBnId = emotionGroup.getCheckedRadioButtonId();
            EmoEnum emotion = idToEmoEnum(emotionBnId);
            int decisionBnId = decisionTopGroup.getCheckedRadioButtonId() != -1 ?
                    decisionTopGroup.getCheckedRadioButtonId() : decisionBottomGroup.getCheckedRadioButtonId();

            DecisionEnum decision = idToDecisionEnum(decisionBnId);

            Record record = generateRecord(emotion, decision, "");
            Log.i(TAG, String.format("Record to be added: %s, %s, comment %s", emotion.toString(),
                    decision.toString(), ""));
            repo.insert(record);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.decision_add:
                handleAddDecision();
                Toast.makeText(this, "record added", Toast.LENGTH_SHORT).show();
                finish();
        }
    }

}