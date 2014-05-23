package com.sm.ce.adapters.parish;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.sm.ce.common.exceptions.ParseException;
import com.sm.ce.common.model.ChurchDetail;
import com.sm.ce.common.model.ChurchDetailProvider;
import com.sm.ce.common.model.ChurchEvent;
import com.sm.ce.common.model.SaturdayConfession;
import com.sm.ce.common.model.enums.Day;
import com.sm.ce.util.LoggingObject;
import com.sm.ce.util.WebHelper;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class ParishAdapter extends LoggingObject implements
        ChurchDetailProvider {

    // TODO - populate/add/delete from the URLs/patterns below as needed.
    // it's important that regex's are used to check against the html in
    // case of web site updates the unit tests will fail and alert a
    // developer that the parser should be updated
    private static final String URL_HOME = "http://www.<parish site>.com";
    private static final String URL_RSS = "";
    private static final Pattern REGEX_STREET_ADDRESS = Pattern
            .compile("<exact street address>");
    private static final Pattern REGEX_CITY_STATE_ZIP = Pattern
            .compile("<city, state zip>");
    private static final Pattern REGEX_PHONE = Pattern
            .compile("<phone number>");
    private static final Pattern REGEX_SAT_VIGIL_MASS = Pattern.compile("TODO");
    private static final Pattern REGEX_SAT_DAILY_MASS = Pattern.compile("");
    private static final Pattern REGEX_SUN_MASS = Pattern.compile("");
    private static final Pattern REGEX_DAILY_MASS = Pattern.compile("");
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
            churchDetail.setName("Parish Name");
            // getMasses(churchDetail);
            // getConfessions(churchDetail);
            // getAdoration(churchDetail);
            // getEvents(churchDetail);
            // getContactInformation(churchDetail);
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
        if (webHelper.matches(URL_HOME, REGEX_DAILY_MASS)) {
            addDailyMasses(Day.MON, churchDetail);
            addDailyMasses(Day.TUE, churchDetail);
            addDailyMasses(Day.WED, churchDetail);
            addDailyMasses(Day.THU, churchDetail);
            addDailyMasses(Day.FRI, churchDetail);
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
            // churchDetail.getEvents().add(new SaturdayMass(9, 00));
        } else {
            throw new ParseException("Could not extract saturday daily mass.");
        }
    }

    private void getSundayMasses(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SUN_MASS)) {
            // churchDetail.getEvents().add(new SundayMass(7, 30));
            // churchDetail.getEvents().add(new SundayMass(9, 00));
            // ...
        } else {
            throw new ParseException("Could not extract sunday masses.");
        }
    }

    private void getSaturdayVigilMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_VIGIL_MASS)) {
            // churchDetail.getEvents().add(new VigilMass(17, 30));
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
            // churchDetail.setPhone("TODO");
        } else {
            throw new ParseException("Could not extract phone.");
        }
    }

    private void getCountry(ChurchDetail churchDetail) {
        // not available on the site
        churchDetail.setCountry("US");
    }

    private void getCityStateAndZip(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_CITY_STATE_ZIP)) {
            // churchDetail.setCity("City");
            // churchDetail.setState("State");
            // churchDetail.setZip("Zip");
        } else {
            throw new ParseException("Could not extract city, state and zip.");
        }
    }

    private void getStreetAddress(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_STREET_ADDRESS)) {
            // churchDetail.setStreetAddress("Street Address");
        } else {
            throw new ParseException("Could not extract street address.");
        }
    }
}
