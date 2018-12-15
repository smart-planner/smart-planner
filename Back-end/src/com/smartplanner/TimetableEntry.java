package com.smartplanner;

public class TimetableEntry {
    private Activity activity;
    private Term pickedTerm;

    public TimetableEntry(Activity activity, Term pickedTerm) {
        this.activity = activity;
        this.pickedTerm = pickedTerm;
    }

    public Term getTerm() {
        return pickedTerm;
    }

    public String getName() {
        return activity.getName();
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof TimetableEntry))
            return false;
        if (other == this)
            return true;

        TimetableEntry otherTimetableEntry = (TimetableEntry) other;
        return otherTimetableEntry.pickedTerm.equals(this.pickedTerm);
    }
}
