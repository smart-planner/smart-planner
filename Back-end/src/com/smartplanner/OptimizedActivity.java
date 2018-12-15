package com.smartplanner;

import java.time.LocalTime;
import java.util.ArrayList;

public class OptimizedActivity extends Activity {
    private LocalTime opensAt;
    private LocalTime closesAt;
    private int maxTimeSpentInActivityInMin;
    private ArrayList<Boolean> isOpenedInDay;

    public OptimizedActivity(String name, LocalTime opensAt, LocalTime closesAt, int maxTimeSpentInActivityInMin, ArrayList<Boolean> isOpenedInDay) {
        super(name);
        this.opensAt = opensAt;
        this.closesAt = closesAt;
        this.maxTimeSpentInActivityInMin = maxTimeSpentInActivityInMin;
        this.isOpenedInDay=isOpenedInDay;
    }

    public int getMaxTimeSpentInActivityInMin() {
        return maxTimeSpentInActivityInMin;
    }

    public LocalTime getOpensAt() {
        return opensAt;
    }

    public LocalTime getClosesAt() {
        return closesAt;
    }

    public Boolean isOpenedInDay(int dayNumber) {
        return isOpenedInDay.get(dayNumber);
    }
}
