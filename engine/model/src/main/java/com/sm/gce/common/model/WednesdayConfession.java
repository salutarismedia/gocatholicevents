package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class WednesdayConfession extends WeeklyConfession {

    public WednesdayConfession(int hour, int min) {
        super(Day.WED, hour, min);
    }
}
