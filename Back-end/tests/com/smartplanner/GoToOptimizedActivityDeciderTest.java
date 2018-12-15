package com.smartplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoToOptimizedActivityDeciderTest {

    private ArrayList<TimetableEntry> timetable;
    private ArrayList<ArrayList<Boolean>> allPossibilities;
    private GoToOptimizedActivityDecider bruteforcer;
    private int numberOfAllPossibilities;

    @BeforeEach
    void beforeEach() {
        timetable = new ArrayList<TimetableEntry>();
        timetable.add(new TimetableEntry(new Activity("test"), new Term(60, 1, LocalTime.of(12, 00))));
        timetable.add(new TimetableEntry(new Activity("test"), new Term(60, 1, LocalTime.of(15, 00))));

        bruteforcer = new GoToOptimizedActivityDecider(timetable);
        numberOfAllPossibilities = (int) Math.pow(2, timetable.size() + 1); // we have n+1 decision points (before first activity, between activities and after the last one)
    }

    @Test
    void isNextReturnsTrueIfThereIsNextCombinationToGet() {
        for (int i = 0; i < numberOfAllPossibilities; ++i, bruteforcer.getNext())
            assertTrue(bruteforcer.isNext());
        assertFalse(bruteforcer.isNext());
    }

    @Test
    void getNextReturnsAllPossibletimeTables() {
        ArrayList<ArrayList<Boolean>> allPossibleOutputsShouldBe = init();
        ArrayList<ArrayList<Boolean>> allActualOutputs = new ArrayList<ArrayList<Boolean>>();

        for (int i = 0; i < allPossibleOutputsShouldBe.size(); ++i)
            allActualOutputs.add(bruteforcer.getNext());

        for(ArrayList<Boolean> singleOutputShouldBe : allPossibleOutputsShouldBe)
            assertTrue(hasFoundTimetableInside(singleOutputShouldBe, allActualOutputs));
    }


    private ArrayList<ArrayList<Boolean>> init() {
        ArrayList<ArrayList<Boolean>> allPossibleOutputsShouldBe = new ArrayList<ArrayList<Boolean>>();

        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(false, false, false)));
        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(true, false, false)));
        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(false, true, false)));
        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(true, true, false)));
        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(false, false, true)));
        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(true, false, true)));
        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(false, true, true)));
        allPossibleOutputsShouldBe.add(new ArrayList<Boolean>(Arrays.asList(true, true, true)));

        return allPossibleOutputsShouldBe;
    }

    boolean hasFoundTimetableInside(ArrayList<Boolean> timeTable, ArrayList<ArrayList<Boolean>> searchInside) {
        for(int j = 0; j<searchInside.size(); ++j)
            if (isTimeTableEqual(timeTable, searchInside.get(j)))
                return true;
        return false;
    }

    boolean isTimeTableEqual(ArrayList<Boolean> lhs,  ArrayList<Boolean> rhs) {
        if(lhs.size()!=rhs.size())
            return false;

        for(Boolean singleLhsEntry : lhs)
            if(rhs.indexOf(singleLhsEntry) == -1)
                return false;

        return true;
    }
}
