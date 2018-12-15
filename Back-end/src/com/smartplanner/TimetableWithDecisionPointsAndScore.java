package com.smartplanner;

import java.util.ArrayList;

public class TimetableWithDecisionPointsAndScore {
    private int minutesSpentAtOptimizedActivity;
    private ArrayList<TimetableEntry> optimalTimetable;
    private ArrayList<ArrayList<Boolean>> optimalDecisionPoints;

    // first diemension is responsible for the day in cycle
    // second dimension is responsible for decision point
    // i.e.
    // optimalDecisionPoints.get(0).get(0) answers question: should I go to work in day 0 before first
    // optimalDecisionPoints.get(0).get(1) answers question: should I go to work in day 0 after first activity
    // ...
    // optimalDecisionPoints.get(0).get(n) answers question: should I go to work in day 0 after last activity (where n = last index in array)


    public TimetableWithDecisionPointsAndScore(int minutesSpentAtOptimizedActivity, ArrayList<TimetableEntry> optimalTimetable,
                                               ArrayList<ArrayList<Boolean>> optimalDecisionPoints) {
        this.minutesSpentAtOptimizedActivity = minutesSpentAtOptimizedActivity;
        this.optimalTimetable = optimalTimetable;
        this.optimalDecisionPoints = optimalDecisionPoints;
    }

    public int getMinutesSpentAtOptimizedActivity() {
        return minutesSpentAtOptimizedActivity;
    }

    public ArrayList<TimetableEntry> getOptimalTimetable() {
        return optimalTimetable;
    }

    public ArrayList<ArrayList<Boolean>> getOptimalDecisionPoints() {
        return optimalDecisionPoints;
    }
}
