package com.sm.gce.adapters.st.john.the.beloved;

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
import com.sm.gce.common.model.MondayEfMass;
import com.sm.gce.common.model.MondayMass;
import com.sm.gce.common.model.SaturdayConfession;
import com.sm.gce.common.model.SaturdayMass;
import com.sm.gce.common.model.SundayEfMass;
import com.sm.gce.common.model.SundayMass;
import com.sm.gce.common.model.VigilMass;
import com.sm.gce.common.model.WeeklyMass;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.WebHelper;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class StJohnTheBelovedAdapter extends LoggingObject implements
        ChurchDetailProvider {

    private static final String URL_HOME = "http://www.stjohncatholicmclean.org/";
    private static final String URL_RSS = "";
    private static final Pattern REGEX_STREET_ADDRESS = Pattern
            .compile("6420 Linway Terrace");
    private static final Pattern REGEX_CITY_STATE_ZIP = Pattern
            .compile("McLean, VA 22101");
    private static final Pattern REGEX_PHONE = Pattern
            .compile("\\(703\\) 356-7916");
    private static final Pattern REGEX_SAT_VIGIL_MASS = Pattern
            .compile("SATURDAY:.*?5:00 p.m.");
    private static final Pattern REGEX_SAT_DAILY_MASS = Pattern
            .compile("SATURDAY:.*?8:15");
    private static final Pattern REGEX_SUN_MASS = Pattern
            .compile("SUNDAY:.*?7:30.*?9:00.*?10:30.*?Noon.*?\\(High Latin Mass\\)");
    private static final Pattern REGEX_MONDAY_MASS = Pattern.compile(
            "MONDAY:.*?6:30.*?9:00.*?7:30 p.m..*?\\(Low Latin Mass\\)",
            Pattern.DOTALL);
    private static final Pattern REGEX_TUE_THROUGH_FRI_MASS = Pattern
            .compile("TUESDAY - FRIDAY.*?6:30.*?9:00");
    private static final Pattern REGEX_HOLY_DAY_MASSES = Pattern.compile("");
    private static final Pattern REGEX_WEDNESDAY_CONFESSION = Pattern
            .compile("");
    private static final Pattern REGEX_SATURDAY_CONFESSION = Pattern
            .compile("");
    private static final Pattern REGEX_WEEKDAY_ADORATION = Pattern.compile("");
    private static final Pattern REGEX_SATURDAY_ADORATION = Pattern.compile("");

    private WebHelper webHelper = new WebHelper();

    @Override
    public ChurchDetail getChurchDetail() throws ParseException {

        ChurchDetail churchDetail = new ChurchDetail();
        try {
            churchDetail.setUrl(URL_HOME);
            churchDetail.setName("St John the Beloved");
            churchDetail.setNameSlug("st-john-the-beloved");
            churchDetail.setCitySlug("mcclean");
            churchDetail.setStateSlug("va");
            getLocation(churchDetail);
            getContactInformation(churchDetail);
            getMasses(churchDetail);
            // getConfessions(churchDetail);
            // getAdoration(churchDetail);
            // getEvents(churchDetail);
        } catch (Exception e) {
            String msg = "Parse error";
            logger.error(msg, e);
            throw new ParseException(msg, e);
        }

        return churchDetail;
    }

    private void getLocation(ChurchDetail churchDetail) {
        // these can be found on google maps by right clicking on
        // the point and selecting "what's here"
        churchDetail.setLat(38.924824);
        churchDetail.setLon(-77.161199);
    }

    private void getAdoration(ChurchDetail churchDetail) throws Exception {
        getWeeklyAdoration(churchDetail);
        getSaturdayAdoration(churchDetail);
    }

    private void getSaturdayAdoration(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SATURDAY_ADORATION)) {
            // ChurchEvent event = new ChurchEvent();
            // event.setEventType(EventType.ADORATION);
            // event.setStartTime(new LocalTime(9, 30));
            // event.setStopTime(new LocalTime(12, 30));
            // event.setDay(Day.SAT);
            // churchDetail.getEvents().add(event);
        }
    }

    private void getWeeklyAdoration(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_WEEKDAY_ADORATION)) {
            // addDailyAdoration(Day.MON, churchDetail);
            // addDailyAdoration(Day.TUE, churchDetail);
            // addDailyAdoration(Day.WED, churchDetail);
            // addDailyAdoration(Day.THU, churchDetail);
            // addDailyAdoration(Day.FRI, churchDetail);
        }
    }

    private void addDailyAdoration(Day day, ChurchDetail churchDetail) {
        // ChurchEvent event = new ChurchEvent();
        // event.setEventType(EventType.ADORATION);
        // event.setStartTime(new LocalTime(7, 0));
        // event.setStopTime(new LocalTime(20, 30));
        // event.setDay(day);
        // event.setNote("Usually open until 10 p.m. in the summer");
        // churchDetail.getEvents().add(event);
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
            // event.setStopTime(new LocalTime(17, 00));
            churchDetail.getEvents().add(event);
        }
    }

    private void getWednesdayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_WEDNESDAY_CONFESSION)) {
            // ChurchEvent event = new WednesdayConfession(11, 00);
            // event.setStopTime(new LocalTime(11, 45));
            // churchDetail.getEvents().add(event);
            // ...
        }
    }

    private void getHolyDayMasses(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_HOLY_DAY_MASSES)) {
            // ChurchEvent event = new HolyDayMass(19, 30);
            // event.setNote("Vigil");
            // churchDetail.getEvents().add(event);
            // churchDetail.getEvents().add(new HolyDayMass(6, 15));
            // churchDetail.getEvents().add(new HolyDayMass(9, 00));
            // ...
        }
    }

    private void getDailyMasses(ChurchDetail churchDetail) throws Exception {
        getMondayDailyMasses(churchDetail);
        getTueThroughFriMasses(churchDetail);
    }

    private void getTueThroughFriMasses(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_TUE_THROUGH_FRI_MASS)) {
            addDailyTueThroughFriMasses(Day.TUE, churchDetail);
            addDailyTueThroughFriMasses(Day.WED, churchDetail);
            addDailyTueThroughFriMasses(Day.THU, churchDetail);
            addDailyTueThroughFriMasses(Day.FRI, churchDetail);
        }
    }

    private void addDailyTueThroughFriMasses(Day day, ChurchDetail churchDetail) {
        churchDetail.getEvents().add(new WeeklyMass(day, 6, 30));
        churchDetail.getEvents().add(new WeeklyMass(day, 9, 00));
    }

    private void getMondayDailyMasses(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_MONDAY_MASS)) {
            churchDetail.getEvents().add(new MondayMass(6, 30));
            churchDetail.getEvents().add(new MondayMass(9, 00));
            ChurchEvent event = new MondayEfMass(19, 30);
            event.setNote("(Low Latin Mass)");
            churchDetail.getEvents().add(event);
        } else {
            throw new ParseException("Could not extract saturday daily mass.");
        }
    }

    private void addDailyMasses(Day day, ChurchDetail churchDetail) {
        // churchDetail.getEvents().add(new WeeklyMass(day, 6, 15));
        // churchDetail.getEvents().add(new WeeklyMass(day, 9, 00));
        // ...
    }

    private void getSaturdayDailyMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_DAILY_MASS)) {
            churchDetail.getEvents().add(new SaturdayMass(8, 15));
        } else {
            throw new ParseException("Could not extract saturday daily mass.");
        }
    }

    private void getSundayMasses(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SUN_MASS)) {
            churchDetail.getEvents().add(new SundayMass(7, 30));
            churchDetail.getEvents().add(new SundayMass(9, 00));
            churchDetail.getEvents().add(new SundayMass(10, 30));
            ChurchEvent event = new SundayEfMass(12, 00);
            event.setNote("(High Latin Mass)");
            churchDetail.getEvents().add(event);
        } else {
            throw new ParseException("Could not extract sunday masses.");
        }
    }

    private void getSaturdayVigilMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_VIGIL_MASS)) {
            churchDetail.getEvents().add(new VigilMass(17, 00));
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
        if (webHelper.matches(URL_HOME, REGEX_PHONE)) {
            churchDetail.setPhone("703-356-7916");
        } else {
            throw new ParseException("Could not extract phone.");
        }
    }

    private void getCountry(ChurchDetail churchDetail) {
        // usually not available on the site, so we'll hardcode this one
        churchDetail.setCountry("US");
    }

    private void getCityStateAndZip(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_CITY_STATE_ZIP)) {
            churchDetail.setCity("McClean");
            churchDetail.setState("VA");
            churchDetail.setZip("22101");
        } else {
            throw new ParseException("Could not extract city, state and zip.");
        }
    }

    private void getStreetAddress(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_STREET_ADDRESS)) {
            churchDetail.setStreetAddress("6420 Linway Terrace");
        } else {
            throw new ParseException("Could not extract street address.");
        }
    }
}
