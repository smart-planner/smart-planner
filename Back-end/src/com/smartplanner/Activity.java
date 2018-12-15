package com.smartplanner;

import java.util.ArrayList;

public class Activity {
    private int id;
    static private int objectsCreated = 0;
    private String name;

    public Activity(String name) {
        this.name = name;
        this.id = objectsCreated;
        ++objectsCreated;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static void resetId() {
        objectsCreated=0;
    }
}


