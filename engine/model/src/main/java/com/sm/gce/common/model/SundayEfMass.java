package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class SundayEfMass extends WeeklyEfMass {

    public SundayEfMass(int hour, int min) {
        super(Day.SUN, hour, min);
    }
}
