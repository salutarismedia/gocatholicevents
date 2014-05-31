package com.sm.gce.adapters.st.john.the.apostle;

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
 */
public class StJohnTheApostleAdapterTest extends AbstractParserTest {

    public StJohnTheApostleAdapterTest() {
        super(new StJohnTheApostleAdapter());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("St John the Apostle", getChurchDetail().getName());
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("http://www.saintjohnleesburg.org/", getChurchDetail()
                .getUrl());
    }

    @Test
    public void getStreetAddress() throws Exception {
        assertEquals("101 Oakcrest Manor Dr. NE", getChurchDetail()
                .getStreetAddress());
    }

    @Test
    public void getCity() throws Exception {
        assertEquals("Leesburg", getChurchDetail().getCity());
    }

    @Test
    public void getCitySlug() throws Exception {
        assertEquals("leesburg", getChurchDetail().getCitySlug());
    }

    @Test
    public void getState() throws Exception {
        assertEquals("VA", getChurchDetail().getState());
    }

    @Test
    public void getStateSlug() throws Exception {
        assertEquals("va", getChurchDetail().getStateSlug());
    }

    @Test
    public void getZip() throws Exception {
        assertEquals("20176", getChurchDetail().getZip());
    }

    @Test
    public void getCountry() throws Exception {
        assertEquals("US", getChurchDetail().getCountry());
    }

    @Test
    public void getPhone() throws Exception {
        assertEquals("703-777-1317", getChurchDetail().getPhone());
    }

    @Test
    public void getLat() throws Exception {
        assertEquals(39.120559, getChurchDetail().getLat(), 0.0);
    }

    @Test
    public void getLon() throws Exception {
        assertEquals(-77.561441, getChurchDetail().getLon(), 0.0);
    }

    @Test
    public void saturdayVigilMassWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.VIGIL_MASS, Day.SAT);
        assertEquals(1, events.size());
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
        events = getChurchDetail().getEvent(EventType.EF_MASS, Day.SUN);
        assertEquals(1, events.size());
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
    public void confessionsWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.CONFESSION);
        assertEquals(4, events.size());
    }

    @Test
    public void adorationWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvent(
                EventType.ADORATION);
        assertEquals(5, events.size());
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
    public void firstEventHasValidDate() throws Exception {
        ChurchEvent event = getChurchDetail().getEvent(EventType.OTHER).get(0);
        LocalDate date = new LocalDate(2000, 1, 1);
        assertTrue(event.getStartDate().isAfter(date));
    }

}
