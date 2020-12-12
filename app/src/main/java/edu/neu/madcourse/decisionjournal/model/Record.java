package edu.neu.madcourse.decisionjournal.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import java.sql.Date;

//@Entity(tableName = "records",
//        foreignKeys = @ForeignKey(entity = ParentClass.class,
//        parentColumns = "parentClassColumn",
//        childColumns = "childClassColumn",
//        onDelete = ForeignKey.CASCADE))
//public class Record {
//    @PrimaryKey(autoGenerate = true)
//    public int id;
//
//    @Embedded
//    @NonNull
//    public Decision decision;
//
//    @Embedded
//    @NonNull
//    public Emotion emotion;
//
//    @NonNull
//    @ColumnInfo(name = "date")
//    public Date date;
//
//    @Nullable
//    @ColumnInfo(name = "title")
//    public String title;
//
//    @Nullable
//    @ColumnInfo(name = "detail")
//    public String detail;
//
//
//
//    @Ignore
//    public Record(@NonNull Decision decision, @NonNull Emotion emotion,@NonNull Date date) {
//        this.decision = decision;
//        this.emotion = emotion;
//        this.date = date;
//    }
//
//    public Record(@NonNull Decision decision, @NonNull Emotion emotion, @NonNull Date date,
//                  @Nullable String title, @Nullable String detail) {
//        this.decision = decision;
//        this.emotion = emotion;
//        this.date = date;
//        this.title = title;
//        this.detail = detail;
//    }
//}


@Entity(tableName = "records")
public class Record {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public DecisionEnum decision;

    @NonNull
    public EmoEnum emotion;

    @NonNull
    @ColumnInfo(name = "date")
    public Date date;

    @Nullable
    @ColumnInfo(name = "comment")
    public String comment;

    public Record(@NonNull DecisionEnum decision, @NonNull EmoEnum emotion, @NonNull Date date) {
        this.decision = decision;
        this.emotion = emotion;
        this.date = date;
    }

    @Ignore
    public Record(@NonNull DecisionEnum decision, @NonNull EmoEnum emotion, @NonNull Date date, @Nullable String comment) {
        this.decision = decision;
        this.emotion = emotion;
        this.date = date;
        this.comment = comment;
    }
}