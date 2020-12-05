package edu.neu.madcourse.decisionjournal.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Record {
    @PrimaryKey
    public int id;

    @Embedded
    public Decision decision;
    @Embedded
    public Emotion emotion;
}
