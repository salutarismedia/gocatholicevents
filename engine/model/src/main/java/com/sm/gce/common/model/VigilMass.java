package com.sm.gce.common.model;

import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;

public class VigilMass extends WeeklyMass {

    public VigilMass(int hour, int min) {
        super(Day.SAT, hour, min);
        this.setEventType(EventType.VIGIL_MASS);
    }
}
