package com.sm.gce.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.toString();
    }

    @Override
    public LocalDate unmarshal(String localDate) throws Exception {
        return new LocalDate(localDate);
    }

}
