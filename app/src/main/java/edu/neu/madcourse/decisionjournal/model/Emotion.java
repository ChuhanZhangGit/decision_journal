package edu.neu.madcourse.decisionjournal.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "emotions")
public class Emotion {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "emotion")
    public String emotion;

    public Emotion(@NonNull String emotion) {
        this.emotion = emotion;
    }
}
