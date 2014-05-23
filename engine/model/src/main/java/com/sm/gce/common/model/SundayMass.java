package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class SundayMass extends WeeklyMass {

    public SundayMass(int hour, int min) {
        super(Day.SUN, hour, min);
    }
}
