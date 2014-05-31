package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;

public class MondayMass extends WeeklyMass {

    public MondayMass(int hour, int min) {
        super(Day.MON, hour, min);
    }
}
