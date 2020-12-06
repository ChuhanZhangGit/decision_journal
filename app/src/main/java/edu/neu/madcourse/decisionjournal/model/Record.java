package edu.neu.madcourse.decisionjournal.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "records")
public class Record {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @Embedded
    @NonNull
    @ColumnInfo(name = "decision")
    public Decision decision;
    @Embedded
    @NonNull
    @ColumnInfo(name = "emotion")
    public Emotion emotion;

    @ColumnInfo(name = "date")
    public Date date;



    public Record(@NonNull Decision decision, @NonNull Emotion emotion) {
        this.decision = decision;
        this.emotion = emotion;
    }
}
