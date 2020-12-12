package edu.neu.madcourse.decisionjournal.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.sql.Date;
import java.util.List;

import edu.neu.madcourse.decisionjournal.model.Record;

@Dao
public interface RecordDao {
    @Query("SELECT * FROM records")
    List<Record> getAll();

    // Date has long value, get record of one day, need to retrieve records from day start time to day end time.
    @Query("SELECT * FROM records WHERE date BETWEEN :dayStart AND :dayEnd ORDER BY date ASC")
    LiveData<List<Record>> getRecordBetweenDate(Date dayStart, Date dayEnd);

    @Insert
    void insert(Record record);

    @Delete
    void delete(Record record);

    @Query("DELETE FROM records")
    void deleteAll();

    @Query("SELECT * FROM records WHERE date BETWEEN :dayStart AND :dayEnd ORDER BY date ASC")
    List<Record> getRecordBetweenDateTest(Date dayStart, Date dayEnd);

}
