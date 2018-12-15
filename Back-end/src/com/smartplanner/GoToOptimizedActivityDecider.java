package com.smartplanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class GoToOptimizedActivityDecider {
    private ArrayList<TimetableEntry> singleDayTimetable;
    private ArrayList<AtomicInteger> currentCombination;
    private boolean hasFinished;

    public GoToOptimizedActivityDecider(ArrayList<TimetableEntry> singleDayTimetable) {
        this.singleDayTimetable = singleDayTimetable;
        this.hasFinished = false;

        int numberOfDecisionPoints = singleDayTimetable.size() + 1;
        this.currentCombination = new ArrayList<AtomicInteger>(numberOfDecisionPoints);
        for(int i = 0; i < numberOfDecisionPoints; ++i)
            currentCombination.add(new AtomicInteger(0));
    }

    public GoToOptimizedActivityDecider(ArrayList<TimetableEntry> completeTimetable, int getDecisionsForCycleNumberDay) {
        ArrayList<TimetableEntry> singleDayTimetable = new ArrayList<TimetableEntry>();

        for(TimetableEntry entry : completeTimetable)
            if(entry.getTerm().getCycleDayNumber() == getDecisionsForCycleNumberDay)
                singleDayTimetable.add(entry);

        this.singleDayTimetable = singleDayTimetable;
        this.hasFinished = false;

        int numberOfDecisionPoints = singleDayTimetable.size() + 1;
        this.currentCombination = new ArrayList<AtomicInteger>(numberOfDecisionPoints);
        for(int i = 0; i < numberOfDecisionPoints; ++i)
            currentCombination.add(new AtomicInteger(0));
    }

    public ArrayList<Boolean> getNext() {
        ArrayList<Boolean> returnedCombination = castToBooleanArrayList(currentCombination);

        shiftIndexesToNextSet();
        return returnedCombination;
    }

    private ArrayList<Boolean> castToBooleanArrayList(ArrayList<AtomicInteger> currentCombination) {
        ArrayList<Boolean> casted = new ArrayList<Boolean>(currentCombination.size());

        for(int i=0; i<currentCombination.size(); ++i)
            casted.add(currentCombination.get(i).get() == 1);

        return casted;
    }

    public boolean isNext() {
        return !hasFinished;
    }

    private void shiftIndexesToNextSet() {
        currentCombination.get(0).incrementAndGet();

        for (int i = 0; i + 1 < currentCombination.size(); ++i) {
            if (currentCombination.get(i).get() > 1) {
                currentCombination.get(i).getAndSet(0);
                currentCombination.get(i + 1).getAndIncrement();
            }
        }

        int lastIndex = currentCombination.size() - 1;
        if(currentCombination.get(lastIndex).get() > 1)
            hasFinished = true;
    }
}
