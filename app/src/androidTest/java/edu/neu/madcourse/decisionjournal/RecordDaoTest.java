package edu.neu.madcourse.decisionjournal;

import android.content.Context;

import androidx.room.Room;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.decisionjournal.dao.RecordDao;
import edu.neu.madcourse.decisionjournal.model.DecisionEnum;
import edu.neu.madcourse.decisionjournal.model.EmoEnum;
import edu.neu.madcourse.decisionjournal.model.Record;


@RunWith(AndroidJUnit4.class)
public class RecordDaoTest {
    private AppDatabase database;
    private RecordDao recordDao;

    private final Date date = Date.valueOf(LocalDate.of(2000, 1, 1).toString());
    private final Date nextDay = Date.valueOf(LocalDate.of(2000,1,2).toString());
    private final Date sameDayDiffTime = new Date(date.getTime() + 400000);
    private final Record record1 = new Record(DecisionEnum.WORKOUT, EmoEnum.HAPPY, date);
    private final Record record2 = new Record(DecisionEnum.EAT, EmoEnum.SAD, sameDayDiffTime);

    @Before
    public void createDb() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        recordDao = database.recordDao();
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    @Test
    public void insertTest() {
        recordDao.insert(record1);
        recordDao.insert(record2);

        List<Record> records = recordDao.getRecordBetweenDateTest(date, nextDay);
        Assert.assertEquals(2, records.size());
    }

    @Test
    public void orderByTest() {
        recordDao.insert(record2);
        recordDao.insert(record1);

        List<Record> records = recordDao.getRecordBetweenDateTest(date, nextDay);
        Assert.assertEquals(2, records.size());
        Assert.assertEquals(record1.decision, records.get(0).decision);
        Assert.assertEquals(record2.decision, records.get(1).decision);
    }
}
