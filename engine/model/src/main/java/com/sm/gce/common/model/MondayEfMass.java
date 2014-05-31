package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class MondayEfMass extends WeeklyEfMass {

    public MondayEfMass(int hour, int min) {
        super(Day.MON, hour, min);
    }
}
