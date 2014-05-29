package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class MondayConfession extends WeeklyConfession {

    public MondayConfession(int hour, int min) {
        super(Day.MON, hour, min);
    }
}
