package com.sm.gce.adapters.st.timothy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import com.sm.gce.common.model.ChurchEvent;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;
import com.sm.gce.common.test.AbstractParserTest;

/**
 * this test validates that the ChurchDetail interface is properly populated by
 * the adapter
 */
public class StTimothyChantillyVaUsaAdapterTest extends AbstractParserTest {

    public StTimothyChantillyVaUsaAdapterTest() {
        super(new StTimothyChantillyVaUsaAdapter());
    }

    @Test
    public void getLat() throws Exception {
        assertEquals(38.872481, getChurchDetail().getLat(), 5);
    }

    @Test
    public void getLon() throws Exception {
        assertEquals(-77.424497, getChurchDetail().getLon(), 5);
    }

    @Test
    public void getName() throws Exception {
        assertEquals("St. Timothy Catholic Church", getChurchDetail().getName());
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("http://www.sttimothyparish.org", getChurchDetail()
                .getUrl());
    }

    @Test
    public void getStreetAddress() throws Exception {
        assertEquals("13807 Poplar Tree Road", getChurchDetail()
                .getStreetAddress());
    }

    @Test
    public void getCity() throws Exception {
        assertEquals("Chantilly", getChurchDetail().getCity());
    }

    @Test
    public void getCitySlug() throws Exception {
        assertEquals("chantilly", getChurchDetail().getCitySlug());
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
        assertEquals("20151", getChurchDetail().getZip());
    }

    @Test
    public void getCountry() throws Exception {
        assertEquals("US", getChurchDetail().getCountry());
    }

    @Test
    public void getPhone() throws Exception {
        assertEquals("703-378-7646", getChurchDetail().getPhone());
    }

    @Test
    public void eventsWereDownloaded() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents();
        assertTrue(events.size() > 1);
    }

    @Test
    public void firstEventHasValidName() throws Exception {
        ChurchEvent event = getChurchDetail().getEvents(EventType.OTHER).get(0);
        assertNotNull(event.getName());
    }

    @Test
    public void firstEventHasValidUrl() throws Exception {
        ChurchEvent event = getChurchDetail().getEvents(EventType.OTHER).get(0);
        assertTrue(event.getUrl().startsWith("http"));
    }

    @Test
    public void firstEventHasDescription() throws Exception {
        ChurchEvent event = getChurchDetail().getEvents(EventType.OTHER).get(0);
        assertNotNull(event.getDescription());
    }

    @Test
    public void firstEventHasValidDate() throws Exception {
        ChurchEvent event = getChurchDetail().getEvents(EventType.OTHER).get(0);
        LocalDate date = new LocalDate(2000, 1, 1);
        assertTrue(event.getStartDate().isAfter(date));
    }

    @Test
    public void saturdayVigilMassWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(
                EventType.VIGIL_MASS, Day.SAT);
        assertEquals(1, events.size());
    }

    @Test
    public void saturdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.SAT);
        assertEquals(1, events.size());
    }

    @Test
    public void sundayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.SUN);
        assertEquals(5, events.size());
    }

    // TODO - add more tests for times?
    @Test
    public void sundayMassesHaveCorrectTime() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.SUN);
        assertEquals(new LocalTime(7, 30), events.get(0).getStartTime());
    }

    @Test
    public void mondayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.MON);
        assertEquals(3, events.size());
    }

    @Test
    public void tuesdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.TUE);
        assertEquals(3, events.size());
    }

    @Test
    public void wednesdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.WED);
        assertEquals(3, events.size());
    }

    @Test
    public void thursdayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.THU);
        assertEquals(3, events.size());
    }

    @Test
    public void fridayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.FRI);
        assertEquals(3, events.size());
    }

    @Test
    public void holyDayMassesWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(EventType.MASS,
                Day.HOLY);
        assertEquals(6, events.size());
    }

    @Test
    public void wednesdayConfessionsWereFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(
                EventType.CONFESSION, Day.WED);
        assertEquals(2, events.size());
    }

    @Test
    public void weeklyAdorationWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(
                EventType.ADORATION);
        assertEquals(6, events.size());
    }

    @Test
    public void saturdayAdorationWasFound() throws Exception {
        List<ChurchEvent> events = getChurchDetail().getEvents(
                EventType.ADORATION, Day.SAT);
        assertEquals(1, events.size());
    }

    @Test
    public void citySlug() throws Exception {
        assertEquals("chantilly", getChurchDetail().getCitySlug());
    }

    @Test
    public void nameSlug() throws Exception {
        assertEquals("st-timothy", getChurchDetail().getNameSlug());
    }
}
