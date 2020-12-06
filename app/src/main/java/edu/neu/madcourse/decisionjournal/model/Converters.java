package edu.neu.madcourse.decisionjournal.model;

import androidx.room.TypeConverter;

import java.sql.Date;

/**
 * Converter class is to convert complex types or class to types room persistence library can store.
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static int emoToVal(EmoEnum emotion) {
        return emotion.getVal();
    }

    @TypeConverter
    public static  EmoEnum valToEmo(int val) {
        return EmoEnum.intToEmo(val);
    }

    @TypeConverter
    public static int desToVal(DecisionEnum decisionEnum) {
        return decisionEnum.getVal();
    }

    @TypeConverter
    public static DecisionEnum valToDes(int val) {
        return DecisionEnum.intToDecision(val);
    }

}
