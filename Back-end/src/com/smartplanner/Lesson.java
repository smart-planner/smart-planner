package com.smartplanner;

import java.util.ArrayList;

public class Lesson extends Activity {
    private int repeatingPeriod;
    private ArrayList<Term> terms;

    public Lesson(String name, ArrayList<Term> terms, int repeatingPeriod) {
        super(name);
        this.terms = terms;
        this.repeatingPeriod=repeatingPeriod;
    }

    public ArrayList<Term> getTerms() {
        return terms;
    }

    public int getRepeatingPeriod() {
        return repeatingPeriod;
    }
};
