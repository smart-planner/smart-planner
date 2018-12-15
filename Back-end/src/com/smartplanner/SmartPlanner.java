package com.smartplanner;

import java.util.ArrayList;




public class SmartPlanner {
    ArrayList<Lesson> lessons;
    int daysInCycle;
    TimeDistanceManager distManag;
    int maxCommutesPerDay;
    int minTimeSpentAtOptimizedAtOnceInMinutes;
    OptimizedActivity optimizedActivity;

    public SmartPlanner(ArrayList<Lesson> lessons, int daysInCycle, TimeDistanceManager distManag,
                        int maxCommutesPerDay, int minTimeSpentAtOptimizedAtOnceInMinutes,
                        OptimizedActivity optimizedActivity) {
        this.lessons = lessons;
        this.daysInCycle = daysInCycle;
        this.distManag = distManag;
        this.maxCommutesPerDay = maxCommutesPerDay;
        this.minTimeSpentAtOptimizedAtOnceInMinutes = minTimeSpentAtOptimizedAtOnceInMinutes;
        this.optimizedActivity = optimizedActivity;
    }

    //returns timetable with 0 score, and everything else set to null if data provided for calculations was not valid
    public TimetableWithDecisionPointsAndScore getOptimalPlan() {
        LessonPicker lessonPicker = new LessonPicker(lessons, daysInCycle);

        ArrayList<ArrayList<TimetableEntry>> potentialTimetables = new ArrayList<ArrayList<TimetableEntry>>();

        while(lessonPicker.isNext())
            potentialTimetables.add(lessonPicker.getNext());

        TimetableValidator validator = new TimetableValidator(distManag);

        ArrayList<ArrayList<TimetableEntry>> validTimetables = new ArrayList<ArrayList<TimetableEntry>>();

        for(ArrayList<TimetableEntry> timetable : potentialTimetables)
            if(validator.isValid(timetable))
                validTimetables.add(timetable);

        TimetableWithDecisionPointsAndScore bestTimetable = new TimetableWithDecisionPointsAndScore(0, null, null);
        if(validTimetables.size() == 0)
            return bestTimetable;

        OptimalityCalculator optimalityCalculator = new OptimalityCalculator(distManag, maxCommutesPerDay,
                minTimeSpentAtOptimizedAtOnceInMinutes, daysInCycle, optimizedActivity);

        for(ArrayList<TimetableEntry> validTimetable : validTimetables) {
            TimetableWithDecisionPointsAndScore currentTimetable = optimalityCalculator.calculate(validTimetable);
            if(currentTimetable.getMinutesSpentAtOptimizedActivity()>bestTimetable.getMinutesSpentAtOptimizedActivity())
                bestTimetable = currentTimetable;
        }

        return bestTimetable;
    }
}
