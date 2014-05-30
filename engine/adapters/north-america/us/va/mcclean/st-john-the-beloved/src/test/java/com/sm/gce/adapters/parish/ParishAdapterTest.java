package com.sm.gce.adapters.parish;

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

/**
 * this test validates that the ChurchDetail interface is properly populated by
 * the adapter
 * 
 * classes must inherit from AbstractParserTest for interfacing with the engine
 * 
 * TODO - fill out these unit tests until they are all passing. feel free to
 * tweak/modify as necessary to meet your particular parish's info
 */
public class ParishAdapterTest extends AbstractParserTest {

    // This constructor required
    public ParishAdapterTest() {
        // pass in the instance of the class to test
        super(new ParishAdapter());
    }

    @Test
    public void getName() throws Exception {
        // use getChurchDetail() inherited from AbstractParserTest which
        // caches the web requests
        assertEquals("Parish Name", getChurchDetail().getName());
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("URL", getChurchDetail().getUrl());
    }

    @Test
    public void getStreetAddress() throws Exception {
        assertEquals("Street Address", getChurchDetail().getStreetAddress());
    }

    @Test
    public void getCity() throws Exception {
        assertEquals("City", getChurchDetail().getCity());
    }

    @Test
    public void getCitySlug() throws Exception {
        assertEquals("<city-slug>", getChurchDetail().getCitySlug());
    }

    @Test
    public void getState() throws Exception {
        assertEquals("State", getChurchDetail().getState());
    }

    @Test
    public void getStateSlug() throws Exception {
        assertEquals("<state-slug>", getChurchDetail().getStateSlug());
    }

    @Test
    public void getZip() throws Exception {
        assertEquals("Zip", getChurchDetail().getZip());
    }

    @Test
    public void getCountry() throws Exception {
        assertEquals("US", getChurchDetail().getCountry());
    }

    @Test
    public void getPhone() throws Exception {
        assertEquals("<phone>", getChurchDetail().getPhone());
    }

    @Test
    public void getLat() throws Exception {
        assertEquals(0.0, getChurchDetail().getLat(), 0.0);
    }

    @Test
    public void getLon() throws Exception {
        assertEquals(0.0, getChurchDetail().getLat(), 0.0);
    }

    @Test
    public void saturdayVigilMassWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.VIGIL_MASS, Day.SAT);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void saturdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.SAT);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void sundayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.SUN);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void mondayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.MON);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void tuesdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.TUE);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void wednesdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.WED);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void thursdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.THU);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void fridayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.FRI);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void holyDayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(EventType.MASS,
                Day.HOLY);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void wednesdayConfessionsWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.CONFESSION, Day.WED);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void weeklyAdorationWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.ADORATION);
        // TODO
        assertEquals(-1, events.size());
    }

    @Test
    public void saturdayAdorationWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.ADORATION, Day.SAT);
        // TODO
        assertEquals(-1, events.size());
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

}
