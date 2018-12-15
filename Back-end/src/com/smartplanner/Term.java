package com.smartplanner;

import java.time.LocalTime;

public class Term {
    private int durationInMin;
    private int cycleDayNumber; //date in cycle
    private LocalTime startTime;

    public Term(int durationInMin, int numberOfWeekDay, LocalTime startTime) {
        this.durationInMin = durationInMin;
        this.cycleDayNumber = numberOfWeekDay;
        this.startTime = startTime;
}

    public int getDurationInMin() {
        return durationInMin;
    }

    public int getCycleDayNumber() {
        return cycleDayNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        LocalTime endTime = LocalTime.of(startTime.getHour(), startTime.getMinute());
        return endTime.plusMinutes(durationInMin);
    }
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Term) )
            return false;
        if (other == this)
            return true;

        Term otherTimetableEntry = (Term) other;
        return otherTimetableEntry.durationInMin ==this.durationInMin
                && otherTimetableEntry.cycleDayNumber == this.cycleDayNumber
                && otherTimetableEntry.startTime.equals(this.startTime);
    }
}
