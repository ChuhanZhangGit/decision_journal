package edu.neu.madcourse.decisionjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private Button positiveEmotionBn;
    private Button neutralEmotionBn;
    private Button negativeEmotionBn;
    private List<Button> emoBnList = new ArrayList<>();
    private List<Button> typeBnList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Log.v(TAG, "onCreate");

        repo = new AsyncRecordRepository(getApplicationContext());
        Log.i(TAG, "before insert");
        repo.insert(new Record(DecisionEnum.WORKOUT, EmoEnum.HAPPY, test_date));
        negativeEmotionBn = findViewById(R.id.emotion_negative);
        neutralEmotionBn = findViewById(R.id.emotion_neutral);
        positiveEmotionBn = findViewById(R.id.emotion_positive);
        generateList(emoBnList, negativeEmotionBn, neutralEmotionBn, positiveEmotionBn);
    }

    private void generateList(List<Button> list, Button... buttons) {
        list.addAll(Arrays.asList(buttons));
    }

    public void emotionOnClick(View view) {
        switch (view.getId()) {
            case R.id.emotion_positive:
                onClickHelper(positiveEmotionBn, emoBnList);
            case R.id.emotion_negative:
                onClickHelper(negativeEmotionBn, emoBnList);
            case R.id.emotion_neutral:
                onClickHelper(neutralEmotionBn, emoBnList);
        }
    }

    private void onClickHelper(Button button, List<Button> bnList) {
        boolean prevState = button.isPressed();
        if (!prevState) {
            for (Button bn : bnList) {
                if (bn.isPressed()) {
                    bn.setSelected(false);
                }
            }
        }
        button.setSelected(!prevState);
    }
}