package com.sm.gce.adapters.st.timothy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.sm.gce.common.exceptions.ParseException;
import com.sm.gce.common.model.ChurchDetail;
import com.sm.gce.common.model.ChurchDetailProvider;
import com.sm.gce.common.model.ChurchEvent;
import com.sm.gce.common.model.HolyDayMass;
import com.sm.gce.common.model.SaturdayConfession;
import com.sm.gce.common.model.SaturdayMass;
import com.sm.gce.common.model.SundayMass;
import com.sm.gce.common.model.VigilMass;
import com.sm.gce.common.model.WednesdayConfession;
import com.sm.gce.common.model.WeeklyMass;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;
import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.WebHelper;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class StTimothyChantillyVaUsaAdapter extends LoggingObject implements
        ChurchDetailProvider {

    private static final String URL_HOME = "http://www.sttimothyparish.org";
    private static final String URL_RSS = "http://www.sttimothyparish.org/feed/";
    private static final String URL_CONTACT = "http://sttimothyparish.org/about-us/contact-us/";
    private static final Pattern REGEX_STREET_ADDRESS = Pattern
            .compile("13807 Poplar Tree Road");
    private static final Pattern REGEX_CITY_STATE_ZIP = Pattern
            .compile("Chantilly, VA 20151");
    private static final Pattern REGEX_PHONE = Pattern
            .compile("General Information: 703-378-7646");
    private static final Pattern REGEX_SAT_VIGIL_MASS = Pattern
            .compile("Saturday<.*?5:30 PM");
    private static final Pattern REGEX_SAT_DAILY_MASS = Pattern
            .compile("Saturday<.*?9:00 AM");
    private static final Pattern REGEX_SUN_MASS = Pattern
            .compile("Sunday.*?7:30, 9:00, 10:45 AM.*12:30, 5:30 PM");
    private static final Pattern REGEX_DAILY_MASS = Pattern
            .compile("Monday.Friday.*6:15, 9:00 AM and 12 PM");
    private static final Pattern REGEX_HOLY_DAY_MASSES = Pattern
            .compile("Holy Days.*?Vigil at 7:30 PM.*?Holy Day at 6:15, 9:00 AM, Noon, 6:00 and 7:30 PM");
    private static final Pattern REGEX_WEDNESDAY_CONFESSION = Pattern
            .compile("Wednesday.*?11.11:45 AM...8:00 PM");
    private static final Pattern REGEX_SATURDAY_CONFESSION = Pattern
            .compile("Saturday.*?3:30.5:00 PM");
    private static final Pattern REGEX_WEEKDAY_ADORATION = Pattern
            .compile("Monday.Friday.*?7:00 AM to 8:30 PM");
    private static final Pattern REGEX_SATURDAY_ADORATION = Pattern
            .compile("Saturday.*?9:30 AM to 12:30 PM");

    private WebHelper webHelper = new WebHelper();

    @Override
    public ChurchDetail getChurchDetail() throws ParseException {

        ChurchDetail churchDetail = new ChurchDetail();
        try {
            churchDetail.setName("St. Timothy Catholic Church");
            churchDetail.setNameSlug("st-timothy");
            churchDetail.setLat(38.872481);
            churchDetail.setLon(-77.424497);
            getMasses(churchDetail);
            getConfessions(churchDetail);
            getAdoration(churchDetail);
            getEvents(churchDetail);
            getContactInformation(churchDetail);
            churchDetail.setUrl(URL_HOME);
        } catch (Exception e) {
            String msg = "Parse error";
            logger.error(msg, e);
            throw new ParseException(msg, e);
        }

        return churchDetail;
    }

    private void getAdoration(ChurchDetail churchDetail) throws Exception {
        getWeeklyAdoration(churchDetail);
        getSaturdayAdoration(churchDetail);
    }

    private void getSaturdayAdoration(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SATURDAY_ADORATION)) {
            ChurchEvent event = new ChurchEvent();
            event.setEventType(EventType.ADORATION);
            event.setStartTime(new LocalTime(9, 30));
            event.setStopTime(new LocalTime(12, 30));
            event.setDay(Day.SAT);
            churchDetail.getEvents().add(event);
        }
    }

    private void getWeeklyAdoration(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_WEEKDAY_ADORATION)) {
            addDailyAdoration(Day.MON, churchDetail);
            addDailyAdoration(Day.TUE, churchDetail);
            addDailyAdoration(Day.WED, churchDetail);
            addDailyAdoration(Day.THU, churchDetail);
            addDailyAdoration(Day.FRI, churchDetail);
        }
    }

    private void addDailyAdoration(Day day, ChurchDetail churchDetail) {
        ChurchEvent event = new ChurchEvent();
        event.setEventType(EventType.ADORATION);
        event.setStartTime(new LocalTime(7, 0));
        event.setStopTime(new LocalTime(20, 30));
        event.setDay(day);
        event.setNote("Usually open until 10 p.m. in the summer");
        churchDetail.getEvents().add(event);
    }

    private void getMasses(ChurchDetail churchDetail) throws Exception {
        getSaturdayVigilMass(churchDetail);
        getSaturdayDailyMass(churchDetail);
        getSundayMasses(churchDetail);
        getDailyMasses(churchDetail);
        getHolyDayMasses(churchDetail);
    }

    private void getConfessions(ChurchDetail churchDetail) throws Exception {
        getWednesdayConfessions(churchDetail);
        getSaturdayConfessions(churchDetail);
    }

    private void getSaturdayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SATURDAY_CONFESSION)) {
            ChurchEvent event = new SaturdayConfession(15, 30);
            event.setStopTime(new LocalTime(17, 00));
            churchDetail.getEvents().add(event);
        }
    }

    private void getWednesdayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_WEDNESDAY_CONFESSION)) {
            ChurchEvent event = new WednesdayConfession(11, 00);
            event.setStopTime(new LocalTime(11, 45));
            churchDetail.getEvents().add(event);
            event = new WednesdayConfession(20, 0);
            churchDetail.getEvents().add(event);
        }
    }

    private void getHolyDayMasses(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_HOLY_DAY_MASSES)) {
            ChurchEvent event = new HolyDayMass(19, 30);
            event.setNote("Vigil");
            churchDetail.getEvents().add(event);
            churchDetail.getEvents().add(new HolyDayMass(6, 15));
            churchDetail.getEvents().add(new HolyDayMass(9, 00));
            churchDetail.getEvents().add(new HolyDayMass(12, 00));
            churchDetail.getEvents().add(new HolyDayMass(18, 00));
            churchDetail.getEvents().add(new HolyDayMass(19, 30));
        }
    }

    private void getDailyMasses(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_DAILY_MASS)) {
            addDailyMasses(Day.MON, churchDetail);
            addDailyMasses(Day.TUE, churchDetail);
            addDailyMasses(Day.WED, churchDetail);
            addDailyMasses(Day.THU, churchDetail);
            addDailyMasses(Day.FRI, churchDetail);
        }
    }

    private void addDailyMasses(Day day, ChurchDetail churchDetail) {
        churchDetail.getEvents().add(new WeeklyMass(day, 6, 15));
        churchDetail.getEvents().add(new WeeklyMass(day, 9, 00));
        churchDetail.getEvents().add(new WeeklyMass(day, 12, 00));
    }

    private void getSaturdayDailyMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_DAILY_MASS)) {
            churchDetail.getEvents().add(new SaturdayMass(9, 00));
        } else {
            throw new ParseException("Could not extract saturday daily mass.");
        }
    }

    private void getSundayMasses(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SUN_MASS)) {
            churchDetail.getEvents().add(new SundayMass(7, 30));
            churchDetail.getEvents().add(new SundayMass(9, 00));
            churchDetail.getEvents().add(new SundayMass(10, 45));
            churchDetail.getEvents().add(new SundayMass(12, 30));
            churchDetail.getEvents().add(new SundayMass(17, 30));
        } else {
            throw new ParseException("Could not extract sunday masses.");
        }
    }

    private void getSaturdayVigilMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_VIGIL_MASS)) {
            churchDetail.getEvents().add(new VigilMass(17, 30));
        } else {
            throw new ParseException("Could not extract saturday vigil mass.");
        }
    }

    private void getEvents(ChurchDetail churchDetail)
            throws MalformedURLException, FeedException, IOException {
        SyndFeed feed = downloadRssFeed();
        logger.info("extracting rss values...");
        extractEvents(churchDetail, feed);
    }

    private void extractEvents(ChurchDetail churchDetail, SyndFeed feed) {
        List<SyndEntry> entries = feed.getEntries();
        if (entries != null) {
            for (SyndEntry entry : entries) {
                ChurchEvent event = convertEntryToEvent(entry);
                churchDetail.getEvents().add(event);
            }
        }
    }

    private ChurchEvent convertEntryToEvent(SyndEntry entry) {
        ChurchEvent event = null;
        if (entry != null) {
            event = new ChurchEvent();
            extractEventName(event, entry);
            extractEventDate(event, entry);
            extractEventUrl(event, entry);
            extractEventDescription(event, entry);
        }
        return event;
    }

    private void extractEventUrl(ChurchEvent event, SyndEntry entry) {
        event.setUrl(entry.getLink());
        logger.debug("extracted event url of " + event.getUrl());
    }

    private void extractEventDescription(ChurchEvent event, SyndEntry entry) {
        SyndContent content = entry.getDescription();
        if (content != null) {
            event.setDescription(content.getValue());
            logger.debug("extracted event description of "
                    + event.getDescription());
        }
    }

    private void extractEventName(ChurchEvent event, SyndEntry entry) {
        event.setName(entry.getTitle());
        logger.debug("extracted event name of " + event.getName());
    }

    private void extractEventDate(ChurchEvent event, SyndEntry entry) {
        DCModule module = (DCModule) entry.getModules().get(0);
        Date date = module.getDate();
        event.setStartDate(new LocalDate(date));
        event.setStartTime(new LocalTime(date));
        logger.debug("extracted event date of " + event.getStartDate() + " at "
                + event.getStartTime());
    }

    private SyndFeed downloadRssFeed() throws MalformedURLException,
            FeedException, IOException {
        URL feedUrl = new URL(URL_RSS);
        logger.info("parsing rss feed at " + URL_RSS);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
        logger.debug("downloaded rss feed of " + feed);
        return feed;
    }

    private void getContactInformation(ChurchDetail churchDetail)
            throws Exception {
        getStreetAddress(churchDetail);
        getCityStateAndZip(churchDetail);
        getCountry(churchDetail);
        getPhone(churchDetail);
    }

    private void getPhone(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_CONTACT, REGEX_PHONE)) {
            churchDetail.setPhone("703-378-7646");
        } else {
            throw new ParseException("Could not extract phone.");
        }
    }

    private void getCountry(ChurchDetail churchDetail) {
        // not available on the site
        churchDetail.setCountry("US");
    }

    private void getCityStateAndZip(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_CONTACT, REGEX_CITY_STATE_ZIP)) {
            churchDetail.setCity("Chantilly");
            churchDetail.setCitySlug("chantilly");
            churchDetail.setState("VA");
            churchDetail.setZip("20151");
        } else {
            throw new ParseException("Could not extract city, state and zip.");
        }
    }

    private void getStreetAddress(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_CONTACT, REGEX_STREET_ADDRESS)) {
            churchDetail.setStreetAddress("13807 Poplar Tree Road");
        } else {
            throw new ParseException("Could not extract street address.");
        }
    }
}
