package com.sm.gce.common.model;

import org.joda.time.LocalTime;

import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;

public class HolyDayMass extends ChurchEvent {

    public HolyDayMass(int hour, int min) {
        setEventType(EventType.MASS);
        setDay(Day.HOLY);
        setStartTime(new LocalTime(hour, min));
    }
}
