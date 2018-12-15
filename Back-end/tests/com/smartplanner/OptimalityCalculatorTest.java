package com.smartplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OptimalityCalculatorTest {
    private TimeDistanceManager distManag;
    private OptimalityCalculator optimalityCalculator;
    private ArrayList<TimetableEntry> timetable;
    private OptimizedActivity optimizedAct;

    @BeforeEach
    void beforeEach() {
        Activity.resetId(); //so that id doesn't crash if one run all tests at once
    }

    private void initDataForTest() {

          /* COMMUTE MATRIX (A, B - activities, W - optimized activity)
                to
              A  B  W
    from   A 10 10 10
           B 10 10 10
           W 10 10 10 */

        distManag = new TimeDistanceManager(
                new ArrayList<ArrayList<Integer>>(
                        Arrays.asList(
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10)),
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10)),
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10))
                        )
                )
        );

        timetable = new ArrayList<TimetableEntry>(
                Arrays.asList(
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 0, LocalTime.of(8, 00))),
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 0, LocalTime.of(14, 00)))
                )
        );

        ArrayList<Boolean> isOpenedInDay = new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, false, false));

        optimizedAct = new OptimizedActivity("work", LocalTime.of(8, 00), LocalTime.of(18, 00), 8*60, isOpenedInDay);
        optimalityCalculator = new OptimalityCalculator(distManag, Integer.MAX_VALUE, 60, 1, optimizedAct);
    }

    @Test
    void unlimitedCommutes_optimalityCalculatorCalculatesAmountOfMinutesSpentInOptimizedActivity() {
        initDataForTest();
        final int MINUTES_IN_HOUR = 60;

        Activity firstLesson = timetable.get(0).getActivity();
        int firstLessonDuration = timetable.get(0).getTerm().getDurationInMin();
        Activity secondLesson = timetable.get(1).getActivity();
        int secondLessonDuration = timetable.get(1).getTerm().getDurationInMin();

        int optActOpenedInMin = optimizedAct.getClosesAt().getHour() * MINUTES_IN_HOUR + optimizedAct.getClosesAt().getMinute()
                - (optimizedAct.getOpensAt().getHour() * MINUTES_IN_HOUR + optimizedAct.getOpensAt().getMinute());
        int minutesInTransportation = distManag.getTimeDistanceInMin(firstLesson, optimizedAct)
                + distManag.getTimeDistanceInMin(optimizedAct, secondLesson) + distManag.getTimeDistanceInMin(secondLesson, optimizedAct);
        int minutesSpentOnActivities = firstLessonDuration + secondLessonDuration;

        int minSpentOnOptAct = optActOpenedInMin - minutesInTransportation - minutesSpentOnActivities;
        assertEquals(minSpentOnOptAct, optimalityCalculator.calculate(timetable).getMinutesSpentAtOptimizedActivity());
    }
}
