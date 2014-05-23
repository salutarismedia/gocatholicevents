package com.sm.gce.common.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import com.sm.gce.util.LoggingObject;

public class ChurchDetailTest extends LoggingObject {

    private File tmpFile;
    private LocalDateTime updatedOn;

    @Before
    public void before() throws Exception {
        setTmpVariables();
        marshalTmpEvent();
    }

    private void marshalTmpEvent() throws Exception {
        ChurchDetail churchDetail = new ChurchDetail();
        churchDetail.setUpdatedOn(updatedOn);
        JAXBContext context = JAXBContext.newInstance(ChurchEvent.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(churchDetail, tmpFile);
    }

    private void setTmpVariables() throws IOException {
        tmpFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
        updatedOn = new LocalDateTime(1, 2, 3, 4, 5, 6);
    }

    private ChurchDetail unmarshalTmpEvent() throws Exception {
        JAXBContext context = JAXBContext.newInstance(ChurchEvent.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (ChurchDetail) unmarshaller.unmarshal(tmpFile);
    }

    @Test
    public void marshallUpdatedOn() throws Exception {
        ChurchDetail churchDetail = unmarshalTmpEvent();
        assertEquals(updatedOn, churchDetail.getUpdatedOn());
    }

}
