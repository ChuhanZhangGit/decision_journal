package edu.neu.madcourse.decisionjournal.model;

public enum EmoEnum {
    SAD(-1),
    NEUTRAL(0),
    HAPPY(1),
    NONE(-100);

    public final int emoVal;
    private EmoEnum(int val) {
        emoVal = val;
    }

    public int getVal() {
        return emoVal;
    }

    public static  EmoEnum intToEmo(int val) {
        switch (val) {
            case -1:
                return SAD;
            case  0:
                return NEUTRAL;
            case 1:
                return HAPPY;
            default:
                return NONE;
        }
    }
}
