package edu.neu.madcourse.decisionjournal.dao;

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

    @Query("SELECT * FROM records WHERE date LIKE :date")
    List<Record> getRecordByDate(Date date);

    @Insert
    void insert(Record record);

    @Delete
    void delete(Record record);
}
