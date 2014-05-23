package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class FridayConfession extends WeeklyConfession {

    public FridayConfession(int hour, int min) {
        super(Day.FRI, hour, min);
    }
}
