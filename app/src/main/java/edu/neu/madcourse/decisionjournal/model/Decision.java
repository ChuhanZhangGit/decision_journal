package edu.neu.madcourse.decisionjournal.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "decisions")
public class Decision {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "decision")
    public String decision;

    public Decision(@NonNull String decision) {
        this.decision = decision;
    }
}
