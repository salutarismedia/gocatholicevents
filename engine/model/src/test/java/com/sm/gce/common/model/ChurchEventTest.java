package com.sm.gce.common.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import com.sm.gce.util.LoggingObject;

public class ChurchEventTest extends LoggingObject {

    private File tmpFile;
    private LocalTime startTime;
    private LocalTime stopTime;
    private LocalDate startDate;
    private LocalDate stopDate;

    @Before
    public void before() throws Exception {
        setTmpVariables();
        marshalTmpEvent();
    }

    private void marshalTmpEvent() throws Exception {
        ChurchEvent churchEvent = new ChurchEvent();
        churchEvent.setStartTime(startTime);
        churchEvent.setStopTime(stopTime);
        churchEvent.setStartDate(startDate);
        churchEvent.setStopDate(stopDate);
        JAXBContext context = JAXBContext.newInstance(ChurchEvent.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(churchEvent, tmpFile);
    }

    private void setTmpVariables() throws IOException {
        tmpFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
        startTime = new LocalTime(1, 2, 3);
        stopTime = new LocalTime(4, 5, 6);
        startDate = new LocalDate(7, 8, 9);
        stopDate = new LocalDate(10, 11, 12);
    }

    private ChurchEvent unmarshalTmpEvent() throws Exception {
        JAXBContext context = JAXBContext.newInstance(ChurchEvent.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (ChurchEvent) unmarshaller.unmarshal(tmpFile);
    }

    @Test
    public void marshallStartTime() throws Exception {
        ChurchEvent churchEvent = unmarshalTmpEvent();
        assertEquals(startTime, churchEvent.getStartTime());
    }

    @Test
    public void marshallStopTime() throws Exception {
        ChurchEvent churchEvent = unmarshalTmpEvent();
        assertEquals(stopTime, churchEvent.getStopTime());
    }

    @Test
    public void marshallStartDate() throws Exception {
        ChurchEvent churchEvent = unmarshalTmpEvent();
        assertEquals(startDate, churchEvent.getStartDate());
    }

    @Test
    public void marshallStopDate() throws Exception {
        ChurchEvent churchEvent = unmarshalTmpEvent();
        assertEquals(stopDate, churchEvent.getStopDate());
    }

}
