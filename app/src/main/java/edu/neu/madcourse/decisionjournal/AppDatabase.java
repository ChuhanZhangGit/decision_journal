package edu.neu.madcourse.decisionjournal;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.neu.madcourse.decisionjournal.dao.RecordDao;
import edu.neu.madcourse.decisionjournal.model.Converters;

import edu.neu.madcourse.decisionjournal.model.DecisionEnum;
import edu.neu.madcourse.decisionjournal.model.EmoEnum;
import edu.neu.madcourse.decisionjournal.model.Record;

@Database(entities = {Record.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {


    public abstract RecordDao recordDao();

    private final static String TAG = AppDatabase.class.getSimpleName();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static Date test_date = Date.valueOf(LocalDate.of(2020, 12,9).toString());
    private static Date today = Date.valueOf(LocalDate.now().toString());
    public static final ExecutorService executor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    Log.i(TAG, "IN db getdb");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            executor.execute(() -> {
                Log.i(TAG, "IN db callback");
                RecordDao dao = INSTANCE.recordDao();
                dao.deleteAll();

                // Can Prepopulate database below.
                INSTANCE.recordDao().insert(new Record(DecisionEnum.WORKOUT, EmoEnum.HAPPY, test_date));
                INSTANCE.recordDao().insert(new Record(DecisionEnum.STUDY, EmoEnum.HAPPY, new Date(test_date.getTime() + 1000)));

                INSTANCE.recordDao().insert(new Record(DecisionEnum.MUSIC, EmoEnum.HAPPY, today));
                INSTANCE.recordDao().insert(new Record(DecisionEnum.EAT, EmoEnum.HAPPY, new Date(today.getTime() + 1000)));
            });
        }
    };
}
