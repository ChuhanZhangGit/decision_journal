package edu.neu.madcourse.decisionjournal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.neu.madcourse.decisionjournal.dao.RecordDao;
import edu.neu.madcourse.decisionjournal.model.Converters;
import edu.neu.madcourse.decisionjournal.model.Decision;
import edu.neu.madcourse.decisionjournal.model.Emotion;
import edu.neu.madcourse.decisionjournal.model.Record;

@Database(entities = {Record.class, Decision.class, Emotion.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecordDao recordDao();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    /*
    TODO: async db operations
     Executors will use to run database operations asynchronously on a background thread.
     */
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
