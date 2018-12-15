package com.smartplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class LessonPickerTest {
    ArrayList<Lesson> lessons;
    LessonPicker lessonPicker;
    int allPossibilities;

    @BeforeEach
    void beforeEach() {
        lessons = new ArrayList<Lesson>();

        ArrayList<Term> termsAct1 = new ArrayList<Term>();
        termsAct1.add(new Term(10, 1, LocalTime.of(12, 30)));
        termsAct1.add(new Term(20, 2, LocalTime.of(12, 30)));
        lessons.add(new Lesson("lesson1", termsAct1, 7));

        ArrayList<Term> termsAct2 = new ArrayList<Term>();
        termsAct2.add(new Term(30, 3, LocalTime.of(12, 30)));
        termsAct2.add(new Term(40, 4, LocalTime.of(12, 30)));
        lessons.add(new Lesson("lesson2", termsAct2, 7));

        ArrayList<Term> termsAct3 = new ArrayList<Term>();
        termsAct3.add(new Term(50, 5, LocalTime.of(12, 30)));
        termsAct3.add(new Term(60, 6, LocalTime.of(12, 30)));
        lessons.add(new Lesson("lesson3", termsAct3, 7));

        lessonPicker = new LessonPicker(lessons, 7);
        allPossibilities=termsAct1.size()*termsAct2.size()*termsAct3.size();
    }

    @Test
    void isNextReturnsTrueIfThereIsNextCombinationToGet() {
        for(int i=0; i<allPossibilities; ++i, lessonPicker.getNext())
            assertTrue(lessonPicker.isNext());

        assertFalse(lessonPicker.isNext());
    }

    @Test
    void getNextReturnsAllPossibletimeTables() {
        ArrayList<ArrayList<TimetableEntry>> allPossibleTimeTablesShouldBe = init();

        ArrayList<ArrayList<TimetableEntry>> allActualPossibleTimeTables = new ArrayList<>();
        for(int i = 0; i<allPossibilities; ++i)
            allActualPossibleTimeTables.add(lessonPicker.getNext());

        for(int i = 0; i<allPossibleTimeTablesShouldBe.size(); ++i) {
            ArrayList<TimetableEntry> singleTimeTableShouldBe = allPossibleTimeTablesShouldBe.get(i);
            assertTrue(hasFoundTimetableInside(singleTimeTableShouldBe, allActualPossibleTimeTables));
        }
    }

    boolean hasFoundTimetableInside(ArrayList<TimetableEntry> timeTable, ArrayList<ArrayList<TimetableEntry>> searchInside) {
        for(int j = 0; j<searchInside.size(); ++j)
            if (isTimeTableEqual(timeTable, searchInside.get(j)))
                return true;
        return false;
    }

    boolean isTimeTableEqual(ArrayList<TimetableEntry> lhs,  ArrayList<TimetableEntry> rhs) {
        if(lhs.size()!=rhs.size())
            return false;

        for(TimetableEntry singleLhsEntry : lhs)
            if(rhs.indexOf(singleLhsEntry) == -1)
                return false;

        return true;
    }

    private ArrayList<ArrayList<TimetableEntry>> init() {
        ArrayList<ArrayList<TimetableEntry>> allPossibleTimeTablesShouldBe = new ArrayList<>();

        ArrayList<TimetableEntry> timeTable1 = new ArrayList<>();
        timeTable1.add(new TimetableEntry(new Lesson("test", null, 7), new Term(10, 1, LocalTime.of(12, 30))));
        timeTable1.add(new TimetableEntry(new Lesson("test", null, 7), new Term(30, 3, LocalTime.of(12, 30))));
        timeTable1.add(new TimetableEntry(new Lesson("test", null, 7), new Term(50, 5, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable1);

        ArrayList<TimetableEntry> timeTable2 = new ArrayList<>();
        timeTable2.add(new TimetableEntry(new Lesson("test", null, 7), new Term(10, 1, LocalTime.of(12, 30))));
        timeTable2.add(new TimetableEntry(new Lesson("test", null, 7), new Term(40, 4, LocalTime.of(12, 30))));
        timeTable2.add(new TimetableEntry(new Lesson("test", null, 7), new Term(60, 6, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable2);

        ArrayList<TimetableEntry> timeTable3 = new ArrayList<>();
        timeTable3.add(new TimetableEntry(new Lesson("test", null, 7), new Term(10, 1, LocalTime.of(12, 30))));
        timeTable3.add(new TimetableEntry(new Lesson("test", null, 7), new Term(40, 4, LocalTime.of(12, 30))));
        timeTable3.add(new TimetableEntry(new Lesson("test", null, 7), new Term(50, 5, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable3);

        ArrayList<TimetableEntry> timeTable4 = new ArrayList<>();
        timeTable4.add(new TimetableEntry(new Lesson("test", null, 7), new Term(10, 1, LocalTime.of(12, 30))));
        timeTable4.add(new TimetableEntry(new Lesson("test", null, 7), new Term(30, 3, LocalTime.of(12, 30))));
        timeTable4.add(new TimetableEntry(new Lesson("test", null, 7), new Term(60, 6, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable4);

        ArrayList<TimetableEntry> timeTable5 = new ArrayList<>();
        timeTable5.add(new TimetableEntry(new Lesson("test", null, 7), new Term(20, 2, LocalTime.of(12, 30))));
        timeTable5.add(new TimetableEntry(new Lesson("test", null, 7), new Term(30, 3, LocalTime.of(12, 30))));
        timeTable5.add(new TimetableEntry(new Lesson("test", null, 7), new Term(50, 5, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable5);

        ArrayList<TimetableEntry> timeTable6 = new ArrayList<>();
        timeTable6.add(new TimetableEntry(new Lesson("test", null, 7), new Term(20, 2, LocalTime.of(12, 30))));
        timeTable6.add(new TimetableEntry(new Lesson("test", null, 7), new Term(40, 4, LocalTime.of(12, 30))));
        timeTable6.add(new TimetableEntry(new Lesson("test", null, 7), new Term(50, 5, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable6);

        ArrayList<TimetableEntry> timeTable7 = new ArrayList<>();
        timeTable7.add(new TimetableEntry(new Lesson("test", null, 7), new Term(20, 2, LocalTime.of(12, 30))));
        timeTable7.add(new TimetableEntry(new Lesson("test", null, 7), new Term(30, 3, LocalTime.of(12, 30))));
        timeTable7.add(new TimetableEntry(new Lesson("test", null, 7), new Term(60, 6, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable7);

        ArrayList<TimetableEntry> timeTable8 = new ArrayList<>();
        timeTable8.add(new TimetableEntry(new Lesson("test", null, 7), new Term(20, 2, LocalTime.of(12, 30))));
        timeTable8.add(new TimetableEntry(new Lesson("test", null, 7), new Term(40, 4, LocalTime.of(12, 30))));
        timeTable8.add(new TimetableEntry(new Lesson("test", null, 7), new Term(60, 6, LocalTime.of(12, 30))));
        allPossibleTimeTablesShouldBe.add(timeTable8);

        return allPossibleTimeTablesShouldBe;
    }
}