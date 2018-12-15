package com.smartplanner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class TimetableValidator {
    TimeDistanceManager distManag;

    public TimetableValidator(TimeDistanceManager distManag) {
        this.distManag = distManag;
    }

    public boolean isValid(ArrayList<TimetableEntry> timetable) {

        ArrayList<TimetableEntry> timetableSorted = new ArrayList<TimetableEntry>(timetable);

        Comparator<TimetableEntry> timeComparator = new Comparator<TimetableEntry>() {
            @Override
            public int compare(TimetableEntry lhs, TimetableEntry rhs) {
                if(lhs.getTerm().getCycleDayNumber() < rhs.getTerm().getCycleDayNumber())
                    return -1;
                else if(lhs.getTerm().getCycleDayNumber() > rhs.getTerm().getCycleDayNumber())
                    return 1;
                else
                    return lhs.getTerm().getStartTime().compareTo(rhs.getTerm().getStartTime());
            }
        };

        timetableSorted.sort(timeComparator);

        return noneOfActivitesOverlaps(timetable) && allActivitiesAreTransportationReachable(timetableSorted);
    }

    private boolean allActivitiesAreTransportationReachable(ArrayList<TimetableEntry> sortedTimetable) {
        for(int i=0; i+1<sortedTimetable.size(); ++i)
            if(isTransportationReachable(sortedTimetable.get(i), sortedTimetable.get(i+1)) == false)
                return false;

        return true;
    }

    private boolean isTransportationReachable(TimetableEntry first, TimetableEntry second) {
        if(first.getTerm().getCycleDayNumber() != second.getTerm().getCycleDayNumber())
            return true;

        LocalTime possibleArrivalToSecondActivityTime = first.getTerm().getEndTime()
                .plusMinutes(distManag.getTimeDistanceInMin(first.getActivity(), second.getActivity()));

        return possibleArrivalToSecondActivityTime.isAfter(second.getTerm().getStartTime()) == false;
    }

    private boolean noneOfActivitesOverlaps(ArrayList<TimetableEntry> timetable) {
        for(int i = 0; i < timetable.size(); ++i) {
            for(int j = 0; j < timetable.size(); ++j) {
                if(i == j)
                    continue;
                if(doesOverlap(timetable.get(i), timetable.get(j)))
                    return false;
            }
        }
        return true;
    }


    private boolean doesOverlap(TimetableEntry lhs, TimetableEntry rhs) {
        Term lhsTerm = lhs.getTerm();
        Term rhsTerm = rhs.getTerm();

        Term earlier;
        Term later;
        if (lhsTerm.getStartTime().isAfter(rhsTerm.getStartTime())) {
            later = lhsTerm;
            earlier = rhsTerm;
        } else {
            later = rhsTerm;
            earlier = lhsTerm;
        }

        LocalTime earlierEndingTime = earlier.getStartTime().plusMinutes(earlier.getDurationInMin());
        if (earlierEndingTime.isAfter(later.getStartTime()))
            return true;
        else
            return false;
    }
}
