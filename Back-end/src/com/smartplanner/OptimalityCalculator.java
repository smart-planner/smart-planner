package com.smartplanner;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class OptimalityCalculator {
    private TimeDistanceManager timeDistanceManager;
    private int maxCommutesPerDay;
    private int minTimeSpentOnOptimizedAtOnceInMinutes;
    private int numberOfDaysInCycle;
    private OptimizedActivity optimizedActivity;

    public OptimalityCalculator(TimeDistanceManager timeDistanceManager,
                                int maxCommutesPerDay,
                                int minTimeSpentOnOptimizedAtOnceInMinutes,
                                int numberOfDaysInCycle,
                                OptimizedActivity optimizedActivity) {
        this.optimizedActivity = optimizedActivity;
        this.timeDistanceManager = timeDistanceManager;
        this.maxCommutesPerDay = maxCommutesPerDay;
        this.minTimeSpentOnOptimizedAtOnceInMinutes = minTimeSpentOnOptimizedAtOnceInMinutes;
        this.numberOfDaysInCycle = numberOfDaysInCycle;
    }

    public TimetableWithDecisionPointsAndScore calculate(ArrayList<TimetableEntry> timetable) {
        ArrayList<ArrayList<Boolean>> optimalDecisionPoints = new ArrayList<ArrayList<Boolean>>(numberOfDaysInCycle);
        int timeSpentInWorkInCycle = 0;

        for (int cycleDayNumber = 0; cycleDayNumber < numberOfDaysInCycle; ++cycleDayNumber) {

            if(optimizedActivity.isOpenedInDay(cycleDayNumber) == false) {
                optimalDecisionPoints.add(new ArrayList<Boolean>(Arrays.asList(false)));
                continue;
            }

            GoToOptimizedActivityDecider decider = new GoToOptimizedActivityDecider(timetable, cycleDayNumber);
            int maxForCurrentDay = 0;
            ArrayList<Boolean> bestDecisionPoints = null;

            while (decider.isNext()) {
                ArrayList<Boolean> currDecisionPoints = decider.getNext();
                int currentVal = calculateRegardingProvidedDecisionPoints(timetable, currDecisionPoints, cycleDayNumber);

                if (currentVal > maxForCurrentDay) {
                    bestDecisionPoints = currDecisionPoints;
                    maxForCurrentDay = currentVal;
                }
            }

            if(maxForCurrentDay > optimizedActivity.getMaxTimeSpentInActivityInMin())
                maxForCurrentDay = optimizedActivity.getMaxTimeSpentInActivityInMin();

            timeSpentInWorkInCycle += maxForCurrentDay;
            optimalDecisionPoints.add(bestDecisionPoints);
        }

        return new TimetableWithDecisionPointsAndScore(timeSpentInWorkInCycle, timetable, optimalDecisionPoints);
    }

    private int getNumberOfCommutesToWork(ArrayList<Boolean> decisionPoints) {
        int numberOfOnes = 0;
        for (Boolean decision : decisionPoints)
            if (decision == true)
                ++numberOfOnes;

        return numberOfOnes;
    }

    private int calculateRegardingProvidedDecisionPoints(ArrayList<TimetableEntry> timetable,
                                                         ArrayList<Boolean> currDecisionPoints,
                                                         int cycleDayNumber) {
        if (getNumberOfCommutesToWork(currDecisionPoints) > maxCommutesPerDay)
            return 0;

        int timeSpentInWork = 0;
        ArrayList<TimetableEntry> specifiedDayTimetable = extractEntriesForSpecifiedDay(timetable, cycleDayNumber);
        if(specifiedDayTimetable.size() ==  0) //there are no lessons that day
            return calculateMinutesBetweenTwoTimePoints(optimizedActivity.getOpensAt(), optimizedActivity.getClosesAt());

        if (currDecisionPoints.get(0) == true) {
            int timeBetweenOptimizedActivityOpenAndFirstActivityStart = calculateMinutesBetweenTwoTimePoints(
                    optimizedActivity.getOpensAt(), specifiedDayTimetable.get(0).getTerm().getStartTime())
                    - timeDistanceManager.getTimeDistanceInMin(specifiedDayTimetable.get(0).getActivity(), optimizedActivity);
            int minutesSpentInWorkForCurrDecisionPoint = timeBetweenOptimizedActivityOpenAndFirstActivityStart;

            if (minutesSpentInWorkForCurrDecisionPoint >= minTimeSpentOnOptimizedAtOnceInMinutes)
                timeSpentInWork += minutesSpentInWorkForCurrDecisionPoint;
        }

        for (int i = 1; i < currDecisionPoints.size() - 1; ++i) {
            if (currDecisionPoints.get(i) == true) {
                int timeBetweenTwoActivities = calculateMinutesBetweenTwoTimePoints(specifiedDayTimetable.get(i).getTerm()
                                .getStartTime(), specifiedDayTimetable.get(i - 1).getTerm().getEndTime());
                int transportTimeFromFirstActToOptimized = timeDistanceManager.getTimeDistanceInMin(specifiedDayTimetable
                        .get(i - 1).getActivity(), optimizedActivity);
                int transportTimeFromOptimizedToSecondAct = timeDistanceManager.getTimeDistanceInMin(optimizedActivity,
                        specifiedDayTimetable.get(i).getActivity());
                int minutesSpentInWorkForCurrDecisionPoint = timeBetweenTwoActivities
                        - transportTimeFromFirstActToOptimized - transportTimeFromOptimizedToSecondAct;

                if (minutesSpentInWorkForCurrDecisionPoint >= minTimeSpentOnOptimizedAtOnceInMinutes)
                    timeSpentInWork += minutesSpentInWorkForCurrDecisionPoint;
            }
        }

        if (currDecisionPoints.get(currDecisionPoints.size() - 1) == true) {
            int timeBetweenLastActivityEndingAndOptimizedActivityClose = calculateMinutesBetweenTwoTimePoints(specifiedDayTimetable
                    .get(specifiedDayTimetable.size() - 1).getTerm().getEndTime(), optimizedActivity.getClosesAt());
            int travelTimeFromLastActivityToOptimizedActivity = timeDistanceManager.getTimeDistanceInMin(specifiedDayTimetable
                    .get(specifiedDayTimetable.size() - 1).getActivity(), optimizedActivity);
            int minutesSpentInWorkForCurrDecisionPoint = timeBetweenLastActivityEndingAndOptimizedActivityClose
                    - travelTimeFromLastActivityToOptimizedActivity;

            if (minutesSpentInWorkForCurrDecisionPoint >= minTimeSpentOnOptimizedAtOnceInMinutes)
                timeSpentInWork += minutesSpentInWorkForCurrDecisionPoint;
        }

        return timeSpentInWork;
    }

    private ArrayList<TimetableEntry> extractEntriesForSpecifiedDay(ArrayList<TimetableEntry> timetable, int cycleDayNumber) {
        ArrayList<TimetableEntry> entriesForSpecifiedDay = new ArrayList<TimetableEntry>();
        for (TimetableEntry entry : timetable)
            if (entry.getTerm().getCycleDayNumber() == cycleDayNumber)
                entriesForSpecifiedDay.add(entry);

        return entriesForSpecifiedDay;
    }

    private int calculateMinutesBetweenTwoTimePoints(LocalTime lhs, LocalTime rhs) {
        Duration duration = Duration.between(lhs, rhs);
        return (int) Math.abs(duration.toMinutes());
    }
}
