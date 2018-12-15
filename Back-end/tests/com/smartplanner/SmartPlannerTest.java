package com.smartplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/* DISCLAIMER:
 *
 * Lessons objects must be created in order as they occurs in TimeDistanceManager
 * OptimizedActivity must be created as the last one object (after creation of all Lesson objects)
 */
class SmartPlannerTest {

    @BeforeEach
    void beforeEach() {
        Activity.resetId(); //so that id doesn't crash if one run all tests at once
    }

    @Test
    void getOptimalPlanTest() {

        ArrayList<Term> lesson1Terms = new ArrayList<>(Arrays.asList(
                new Term(60, 0, LocalTime.of(9, 00)),
                new Term(60, 0, LocalTime.of(10, 00))
        ));
        Lesson lesson1 = new Lesson("Lesson 1", lesson1Terms, 7);

        ArrayList<Term> lesson2Terms = new ArrayList<>(Arrays.asList(
                new Term(60, 0, LocalTime.of(11, 00)),
                new Term(60, 1, LocalTime.of(14, 00))
        ));
        Lesson lesson2 = new Lesson("Lesson 2", lesson2Terms, 7);

        ArrayList<Term> lesson3Terms = new ArrayList<>(Arrays.asList(
                new Term(60, 1, LocalTime.of(9, 00)),
                new Term(60, 1, LocalTime.of(12, 00))
        ));
        Lesson lesson3 = new Lesson("Lesson 3", lesson3Terms, 7);

        ArrayList<Term> lesson4Terms = new ArrayList<>(Arrays.asList(
                new Term(60, 0, LocalTime.of(15, 00)),
                new Term(60, 1, LocalTime.of(8, 00))
        ));
        Lesson lesson4 = new Lesson("Lesson 4", lesson4Terms, 7);

        ArrayList<Lesson> lessons = new ArrayList<Lesson>(Arrays.asList(lesson1, lesson2, lesson3, lesson4));

        int daysInCycle = 7;
        ArrayList<Boolean> isOpenedInDay = new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, false, false));

          /* COMMUTE MATRIX (A = lesson1, B = lesson2, C = lesson3, D = lesson4, W = optimized activity)
                to
              A  B  C  D  W
    from   A 10 10 10 10 10
           B 10 10 10 10 10
           C 10 10 10 10 10
           D 10 10 10 10 10
           W 10 10 10 10 10 */

        TimeDistanceManager distManag = new TimeDistanceManager(
                new ArrayList<ArrayList<Integer>>(
                        Arrays.asList(
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10, 10, 10)),
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10, 10, 10)),
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10, 10, 10)),
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10, 10, 10)),
                                new ArrayList<Integer>(Arrays.asList(10, 10, 10, 10, 10))
                        )
                )
        );

        int maxCommutesPerDay = Integer.MAX_VALUE;
        int minTimeSpentOnOptimizedAtOnceInMinutes = 120;
        OptimizedActivity optimizedActivity = new OptimizedActivity("work", LocalTime.of(8,00),
                LocalTime.of(18, 00), 8*60, isOpenedInDay);

        SmartPlanner planner = new SmartPlanner(lessons, daysInCycle, distManag, maxCommutesPerDay,
                minTimeSpentOnOptimizedAtOnceInMinutes, optimizedActivity);

        TimetableWithDecisionPointsAndScore optimal = planner.getOptimalPlan();
        assertEquals(optimal.getMinutesSpentAtOptimizedActivity(), 2390);
    }

}