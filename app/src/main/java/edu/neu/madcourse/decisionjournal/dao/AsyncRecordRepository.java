package edu.neu.madcourse.decisionjournal.dao;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.sql.Date;
import java.util.List;

import edu.neu.madcourse.decisionjournal.AppDatabase;
import edu.neu.madcourse.decisionjournal.model.Record;

/**
 * AsyncRecordRepository is for crud operation on Record table. It uses other threads to run
 * database operation which will prevent the operation freeze UI thread.
 */
public class AsyncRecordRepository {
    private static AppDatabase database;
    private static RecordDao recordDao;
    private static volatile AsyncRecordRepository INSTANCE;

    private AsyncRecordRepository(Context application) {
        database = AppDatabase.getDatabase(application);
        recordDao = database.recordDao();
    }

    public static AsyncRecordRepository getInstance(Context application) {
        if (INSTANCE == null) {
            synchronized (AsyncRecordRepository.class) {
                INSTANCE = new AsyncRecordRepository(application);
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Record>> getRecordOnDate(Date date) {
        return recordDao.getRecordByDate(date);
    }


    public void insert(Record record) {
        AppDatabase.executor.execute(()-> {
            recordDao.insert(record);
        });
    }


}
