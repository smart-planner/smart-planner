package com.smartplanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TimetableValidatorTest {

    private TimeDistanceManager distManag;

    @BeforeEach
    void beforeEach() {

        Activity.resetId(); //so that it doesn't crash if all tests are run at once
        distManag = new TimeDistanceManager(
                new ArrayList<ArrayList<Integer>>(
                        Arrays.asList(
                                new ArrayList<Integer>(Arrays.asList(0, 0)),
                                new ArrayList<Integer>(Arrays.asList(0, 0))
                        )
                )
        );
    }

    @Test
    void validatorReturnsFalseIfThereAreEntriesThatOverlapsEeachOther() {
        ArrayList<TimetableEntry> invalidTimetable=new ArrayList<TimetableEntry>(
                Arrays.asList(
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(14,00))),
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(14,30)))
                )
        );

        TimetableValidator validator = new TimetableValidator(distManag);
        assertFalse(validator.isValid(invalidTimetable));
    }

    @Test
    void validatorReturnsTrueIfEntriesDoesNotOverlapEachOther() {
        ArrayList<TimetableEntry> validTimetable = new ArrayList<TimetableEntry>(
                Arrays.asList(
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(14, 00))),
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(16, 00)))
                )
        );

        TimetableValidator validator = new TimetableValidator(distManag);
        assertTrue(validator.isValid(validTimetable));
    }

    @Test
    void validatorReturnsTrueIfOneActivityEndsPreciselyWhenTheOtherStarts() {
        ArrayList<TimetableEntry> validTimetable = new ArrayList<TimetableEntry>(
                Arrays.asList(
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(14, 00))),
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(15, 00)))
                )
        );

        TimetableValidator validator = new TimetableValidator(distManag);
        assertTrue(validator.isValid(validTimetable));
    }

    @Test
    void validatorReturnsFalseIfCanNotGetOnTimeFromOneToAnotherActivity() {

        distManag = new TimeDistanceManager(
                new ArrayList<ArrayList<Integer>>(
                        Arrays.asList(
                                new ArrayList<Integer>(Arrays.asList(10, 10)),
                                new ArrayList<Integer>(Arrays.asList(10, 10))
                        )
                )
        );

        ArrayList<TimetableEntry> validTimetable = new ArrayList<TimetableEntry>(
                Arrays.asList(
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(14, 00))),
                        new TimetableEntry(new Lesson("test", null, 7), new Term(60, 5, LocalTime.of(15, 00)))
                )
        );

        TimetableValidator validator = new TimetableValidator(distManag);
        assertFalse(validator.isValid(validTimetable));
    }

}
