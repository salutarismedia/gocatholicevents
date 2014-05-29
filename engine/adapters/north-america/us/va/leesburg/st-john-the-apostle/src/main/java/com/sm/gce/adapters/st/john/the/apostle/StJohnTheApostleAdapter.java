package com.sm.gce.adapters.st.john.the.apostle;

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
import com.sm.gce.common.model.MondayConfession;
import com.sm.gce.common.model.SaturdayConfession;
import com.sm.gce.common.model.SaturdayMass;
import com.sm.gce.common.model.SundayMass;
import com.sm.gce.common.model.VigilMass;
import com.sm.gce.common.model.WednesdayConfession;
import com.sm.gce.common.model.WeeklyAdoration;
import com.sm.gce.common.model.WeeklyMass;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.Language;
import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.WebHelper;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class StJohnTheApostleAdapter extends LoggingObject implements
        ChurchDetailProvider {

    private static final String URL_HOME = "http://www.saintjohnleesburg.org/";
    private static final String URL_RSS = "";
    private static final Pattern REGEX_STREET_ADDRESS = Pattern
            .compile("101 Oakcrest Manor Dr. NE");
    private static final Pattern REGEX_CITY_STATE_ZIP = Pattern
            .compile("Leesburg, VA 20176");
    private static final Pattern REGEX_PHONE = Pattern.compile("703-777-1317");
    private static final Pattern REGEX_SAT_VIGIL_MASS = Pattern
            .compile("Saturday: 5:30 \\(Church\\)");
    private static final Pattern REGEX_SAT_DAILY_MASS = Pattern
            .compile("Saturday, 9:00 \\(Church\\)");
    private static final Pattern REGEX_SUN_MASS = Pattern
            .compile(
                    "Sunday: 7:30, 9:00, 10:45, 12:30, 2:15 \\(Spanish\\), all in Church.*?Latin Extraordinary Form \\(Chapel\\): 10:30",
                    Pattern.DOTALL);
    private static final Pattern REGEX_DAILY_MASS = Pattern
            .compile("Daily Mass: Monday-Friday, 8:30 \\(Chapel\\) and noon \\(Church\\); Saturday, 9:00 \\(Church\\)");
    private static final Pattern REGEX_CONFESSION = Pattern
            .compile(
                    "Saturday, 4:00 \\(Church\\).*?Monday, 9:00 \\(Chapel\\), 12:30 \\(Church\\).*?Wednesdays during Lent, 6:30",
                    Pattern.DOTALL);
    private static final Pattern REGEX_ADORATION = Pattern
            .compile("After Monday morning mass until Friday morning mass \\(Chapel\\)");

    private WebHelper webHelper = new WebHelper();

    @Override
    public ChurchDetail getChurchDetail() throws ParseException {

        ChurchDetail churchDetail = new ChurchDetail();
        try {
            churchDetail.setUrl(URL_HOME);
            churchDetail.setName("St John the Apostle");
            churchDetail.setNameSlug("st-john-the-apostle");
            getContactInformation(churchDetail);
            getLocation(churchDetail);
            getMasses(churchDetail);
            getConfessions(churchDetail);
            getAdoration(churchDetail);
            // getEvents(churchDetail);
        } catch (Exception e) {
            String msg = "Parse error";
            logger.error(msg, e);
            throw new ParseException(msg, e);
        }

        return churchDetail;
    }

    private void getLocation(ChurchDetail churchDetail) {
        churchDetail.setLat(39.120559);
        churchDetail.setLon(-77.561441);
    }

    private void getAdoration(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_ADORATION)) {
            ChurchEvent event = new WeeklyAdoration(Day.MON, 9, 00);
            event.setStopTime(new LocalTime(23, 59));
            churchDetail.getEvents().add(event);

            addAllDayAdoration(churchDetail, Day.TUE);
            addAllDayAdoration(churchDetail, Day.WED);
            addAllDayAdoration(churchDetail, Day.THU);

            event = new WeeklyAdoration(Day.FRI, 00, 00);
            event.setStopTime(new LocalTime(8, 30));
            churchDetail.getEvents().add(event);

        }
    }

    private void addAllDayAdoration(ChurchDetail churchDetail, Day day) {
        ChurchEvent event = new WeeklyAdoration(day, 0, 00);
        event.setStopTime(new LocalTime(23, 59));
        churchDetail.getEvents().add(event);
    }

    private void getMasses(ChurchDetail churchDetail) throws Exception {
        getSaturdayVigilMass(churchDetail);
        getSaturdayDailyMass(churchDetail);
        getSundayMasses(churchDetail);
        getDailyMasses(churchDetail);
    }

    private void getConfessions(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_CONFESSION)) {
            ChurchEvent event = new SaturdayConfession(4, 00);
            event.setNote("(Church)");
            churchDetail.getEvents().add(event);
            event = new MondayConfession(9, 00);
            event.setNote("(Chapel)");
            churchDetail.getEvents().add(event);
            event = new MondayConfession(00, 30);
            event.setNote("(Church)");
            churchDetail.getEvents().add(event);
            event = new WednesdayConfession(18, 30);
            event.setNote("(During Lent)");
            churchDetail.getEvents().add(event);
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
        WeeklyMass weeklyMass = new WeeklyMass(day, 8, 30);
        weeklyMass.setNote("(Chapel)");
        churchDetail.getEvents().add(weeklyMass);
        weeklyMass = new WeeklyMass(day, 12, 00);
        weeklyMass.setNote("(Church)");
        churchDetail.getEvents().add(weeklyMass);
    }

    private void getSaturdayDailyMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_DAILY_MASS)) {
            SaturdayMass saturdayMass = new SaturdayMass(9, 00);
            saturdayMass.setNote("(Church)");
            churchDetail.getEvents().add(saturdayMass);
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
            ChurchEvent event = new SundayMass(14, 15);
            event.setLanguage(Language.SPANISH);
            churchDetail.getEvents().add(event);
            event = new WeeklyMass(Day.SUN);
            event.setNote("(Extraordinary Form)");
            event.setStartTime(new LocalTime(10, 30));
            event.setNote("(Chapel)");
            churchDetail.getEvents().add(event);
        } else {
            throw new ParseException("Could not extract sunday masses.");
        }
    }

    private void getSaturdayVigilMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_VIGIL_MASS)) {
            VigilMass vigilMass = new VigilMass(17, 30);
            vigilMass.setNote("(Church)");
            churchDetail.getEvents().add(vigilMass);
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
            churchDetail.setPhone("703-777-1317");
        } else {
            throw new ParseException("Could not extract phone.");
        }
    }

    private void getCountry(ChurchDetail churchDetail) {
        churchDetail.setCountry("US");
    }

    private void getCityStateAndZip(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_CITY_STATE_ZIP)) {
            churchDetail.setCity("Leesburg");
            churchDetail.setState("VA");
            churchDetail.setZip("20176");
        } else {
            throw new ParseException("Could not extract city, state and zip.");
        }
    }

    private void getStreetAddress(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_STREET_ADDRESS)) {
            churchDetail.setStreetAddress("101 Oakcrest Manor Dr. NE");
        } else {
            throw new ParseException("Could not extract street address.");
        }
    }
}
