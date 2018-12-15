package com.smartplanner;

import java.util.ArrayList;

public class LessonPicker {
    private ArrayList<Lesson> lessons;
    private int termIndexes[];
    private boolean hasFinished = false;
    private int daysInCycle;

    public LessonPicker(ArrayList<Lesson> lessons, int daysInCycle) {
        this.daysInCycle = daysInCycle;
        this.lessons = lessons;
        this.termIndexes = new int[lessons.size()];

        for(int index : termIndexes)
            index = 0;
    }

    public boolean isNext() {
        return !hasFinished;
    }

    public ArrayList<TimetableEntry> getNext() {
        ArrayList<TimetableEntry> possibleTimeTable = new ArrayList<TimetableEntry>();

        for (int lessonIndex = 0; lessonIndex < lessons.size(); ++lessonIndex) {
            ArrayList<Term> currLessonTerms = lessons.get(lessonIndex).getTerms();
            Term firstTerm = currLessonTerms.get(termIndexes[lessonIndex]);

            addRepeatsInCycle(possibleTimeTable, lessonIndex, firstTerm);
        }
        shiftIndexesToNextSet();
        return possibleTimeTable;
    }

    private void addRepeatsInCycle(ArrayList<TimetableEntry> timetable, int activityIndex, Term firstTerm) {
        for(int i = 0; ; ++i) { //adds repeats in cycle
            int nextRepeatDay = firstTerm.getCycleDayNumber() + i*lessons.get(activityIndex).getRepeatingPeriod();
            // -1 because nextRepeatDay is counted from 0 whereas daysInCycle is counted from 0
            if(nextRepeatDay > daysInCycle - 1)
                break;
            Term nextTerm = new Term(firstTerm.getDurationInMin(), nextRepeatDay, firstTerm.getStartTime());
            timetable.add(new TimetableEntry(lessons.get(activityIndex), nextTerm));
        }
    }

    private void shiftIndexesToNextSet() {
        ++termIndexes[0];

        for(int activityIndex = 0; activityIndex+1 < termIndexes.length; ++activityIndex)  {
            int qntOfTermsForCurrActivity = lessons.get(activityIndex).getTerms().size();
            if(termIndexes[activityIndex] >= qntOfTermsForCurrActivity) {
                termIndexes[activityIndex] = 0;
                ++termIndexes[activityIndex+1];
            }
        }

        int lastActivityIndex = termIndexes.length-1;
        if(termIndexes[lastActivityIndex] >= lessons.get(lastActivityIndex).getTerms().size())
            hasFinished = true;
    }
}
