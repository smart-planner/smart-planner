package com.smartplanner;

import java.util.ArrayList;

public class TimeDistanceManager {
    private ArrayList<ArrayList<Integer>> timeDistanceInMin; //timeDistance[from ID][to ID]

    public TimeDistanceManager(ArrayList<ArrayList<Integer>> timeDistanceInMin) {
        this.timeDistanceInMin = timeDistanceInMin;
    }

    public int getTimeDistanceInMin(Activity from, Activity to) {
        return timeDistanceInMin.get(from.getId()).get(to.getId());
    }
}
