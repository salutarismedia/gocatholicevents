package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class SaturdayConfession extends WeeklyConfession {

    public SaturdayConfession(int hour, int min) {
        super(Day.SAT, hour, min);
    }
}
