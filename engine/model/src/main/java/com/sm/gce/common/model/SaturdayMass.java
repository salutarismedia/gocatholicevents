package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class SaturdayMass extends WeeklyMass {

    public SaturdayMass(int hour, int min) {
        super(Day.SAT, hour, min);
    }
}
