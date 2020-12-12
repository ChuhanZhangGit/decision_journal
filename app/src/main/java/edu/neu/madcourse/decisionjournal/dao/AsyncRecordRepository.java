package edu.neu.madcourse.decisionjournal.dao;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import edu.neu.madcourse.decisionjournal.AppDatabase;
import edu.neu.madcourse.decisionjournal.model.Record;

/**
 * AsyncRecordRepository is for crud operation on Record table. It uses other threads to run
 * database operation which will prevent the operation freeze UI thread.
 */
public class AsyncRecordRepository {
    private AppDatabase database;
    private RecordDao recordDao;
//    private static volatile AsyncRecordRepository INSTANCE;

    public AsyncRecordRepository(Context application) {
        database = AppDatabase.getDatabase(application);
        recordDao = database.recordDao();
    }

//    public static AsyncRecordRepository getInstance(Context application) {
//        if (INSTANCE == null) {
//            synchronized (AsyncRecordRepository.class) {
//                INSTANCE = new AsyncRecordRepository(application);
//            }
//        }
//        return INSTANCE;
//    }

    public LiveData<List<Record>> getRecordOnDate(Date date) {
        // date toString leave only date field.
        String dateOnly = date.toString();

        Date dayStart = Date.valueOf(dateOnly);

        // hard coded day end in millisecond
        Date dayEnd = new Date(dayStart.getTime() + 24 * 60 * 60 * 1000 - 1);
        return recordDao.getRecordBetweenDate(dayStart, dayEnd);
    }

    public List<Record> getRecordBetweenWholeDate(Date date) {
        // date toString leave only date field.
        String dateOnly = date.toString();

        Date dayStart = Date.valueOf(dateOnly);

        // hard coded day end in millisecond
        Date dayEnd = new Date(dayStart.getTime() + 24 * 60 * 60 * 1000 - 1);
        return recordDao.getRecordBetweenDateTest(dayStart, dayEnd);
    }

    public void insert(Record record) {
        AppDatabase.executor.execute(() -> {
            recordDao.insert(record);
        });
    }

    public LiveData<List<Record>> getSevenDays(Date date) {
        String dateOnly = date.toString();

        Date dayEnd = Date.valueOf(dateOnly);
        // hard coded day end in millisecond
        Date dayStart = new Date(dayEnd.getTime() - 24 * 60 * 60 * 1000 * 7 + 1);
        return recordDao.getRecordBetweenDate(dayStart, new Date(dayEnd.getTime() + 24 * 60 * 60 * 1000 - 1));
    }


}
