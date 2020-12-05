package edu.neu.madcourse.decisionjournal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Decision {
    @PrimaryKey
    public String decision;
}
