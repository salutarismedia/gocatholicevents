package com.sm.gce.common.model;

import com.sm.gce.common.exceptions.ParseException;

public interface ChurchDetailProvider {
    public ChurchDetail getChurchDetail() throws ParseException;
}
