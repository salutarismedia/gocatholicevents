package com.sm.gce.adapters.st.leo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.sm.gce.common.model.ChurchEvent;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;
import com.sm.gce.common.test.AbstractParserTest;

public class StLeoFairfaxVaUsaAdapterTest extends AbstractParserTest {

    public StLeoFairfaxVaUsaAdapterTest() {
        super(new StLeoFairfaxVaUsaAdapter());
    }

    @Test
    public void getLat() throws Exception {
        assertEquals(38.85403, getChurchDetail().getLat(), 5);
    }

    @Test
    public void getLon() throws Exception {
        assertEquals(-77.296582, getChurchDetail().getLon(), 5);
    }

    @Test
    public void getName() throws Exception {
        assertEquals("St. Leo the Great", getChurchDetail().getName());
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("http://www.stleofairfax.com", getChurchDetail().getUrl());
    }

    @Test
    public void getStreetAddress() throws Exception {
        assertEquals("3700 Old Lee Highway", getChurchDetail()
                .getStreetAddress());
    }

    @Test
    public void getCity() throws Exception {
        assertEquals("Fairfax", getChurchDetail().getCity());
    }

    @Test
    public void getCitySlug() throws Exception {
        assertEquals("fairfax", getChurchDetail().getCitySlug());
    }
    
    @Test
    public void getState() throws Exception {
        assertEquals("VA", getChurchDetail().getState());
    }

    @Test
    public void getZip() throws Exception {
        assertEquals("22033", getChurchDetail().getZip());
    }

    @Test
    public void getCountry() throws Exception {
        assertEquals("US", getChurchDetail().getCountry());
    }

    @Test
    public void getPhone() throws Exception {
        assertEquals("703-273-5369", getChurchDetail().getPhone());
    }

    @Test
    public void saturdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.SAT);
        assertEquals(1, events.size());
    }

    @Test
    public void sundayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.SUN);
        assertEquals(5, events.size());
    }

    @Test
    public void mondayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.MON);
        assertEquals(2, events.size());
    }

    @Test
    public void tuesdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.TUE);
        assertEquals(2, events.size());
    }

    @Test
    public void wednesdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.WED);
        assertEquals(2, events.size());
    }

    @Test
    public void thursdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.THU);
        assertEquals(2, events.size());
    }

    @Test
    public void fridayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.FRI);
        assertEquals(2, events.size());
    }

    @Test
    public void fridayConfessionsWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.CONFESSION, Day.FRI);
        assertEquals(1, events.size());
    }

    @Test
    public void saturdayConfessionsWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.CONFESSION, Day.SAT);
        assertEquals(1, events.size());
    }

    @Test
    public void fridayAdorationWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.ADORATION, Day.FRI);
        assertEquals(1, events.size());
    }

    @Test
    public void firstSaturdayAdorationWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.ADORATION, Day.FIRST_SAT);
        assertEquals(1, events.size());
    }

    @Test
    public void eventsWereDownloaded() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents();
        assertTrue(events.size() > 1);
    }

    @Test
    public void firstEventHasValidName() throws Exception {
        ChurchEvent event = getChurchDetail().getEvent(EventType.OTHER).get(0);
        assertNotNull(event.getName());
    }

    @Test
    public void firstEventHasValidUrl() throws Exception {
        ChurchEvent event = getChurchDetail().getEvent(EventType.OTHER).get(0);
        assertTrue(event.getUrl().startsWith("http"));
    }

    @Test
    public void firstEventHasDescription() throws Exception {
        ChurchEvent event = getChurchDetail().getEvent(EventType.OTHER).get(0);
        assertNotNull(event.getDescription());
    }

    @Test
    public void firstEventHasValidDate() throws Exception {
        ChurchEvent event = getChurchDetail().getEvent(EventType.OTHER).get(0);
        LocalDate date = new LocalDate(2000, 1, 1);
        assertTrue(event.getStartDate().isAfter(date));
    }

    @Test
    public void citySlug() throws Exception {
        assertEquals("fairfax", getChurchDetail().getCitySlug());
    }

    @Test
    public void nameSlug() throws Exception {
        assertEquals("st-leo-the-great", getChurchDetail().getNameSlug());
    }
}
