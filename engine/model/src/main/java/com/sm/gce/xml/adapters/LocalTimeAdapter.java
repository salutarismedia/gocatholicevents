package com.sm.gce.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalTime;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public String marshal(LocalTime localTime) throws Exception {
        return localTime.toString();
    }

    @Override
    public LocalTime unmarshal(String localTime) throws Exception {
        return new LocalTime(localTime);
    }

}
