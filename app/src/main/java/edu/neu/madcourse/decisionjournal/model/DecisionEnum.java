package edu.neu.madcourse.decisionjournal.model;


public enum  DecisionEnum {
    NONE(0),
    EAT(1),
    STUDY(2),
    SHOP(3),
    GAME(4),
    MUSIC(5),
    WORKOUT(6);

    public final int desVal;
    DecisionEnum(int val) {
        desVal = val;
    }

    public int getVal() {return desVal;}

    public static DecisionEnum intToDecision(int val) {
        switch (val) {
            case 1: return EAT;
            case 2: return STUDY;
            case 3: return SHOP;
            case 4: return GAME;
            case 5: return MUSIC;
            case 6: return WORKOUT;
            default: return NONE;
        }
    }
}
