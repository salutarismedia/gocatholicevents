package com.sm.gce.adapters.st.leo;

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
import com.sm.gce.common.model.FridayConfession;
import com.sm.gce.common.model.SaturdayConfession;
import com.sm.gce.common.model.SaturdayMass;
import com.sm.gce.common.model.SundayMass;
import com.sm.gce.common.model.VigilMass;
import com.sm.gce.common.model.WeeklyMass;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;
import com.sm.gce.common.model.enums.Language;
import com.sm.gce.common.model.enums.RepeatType;
import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.WebHelper;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class StLeoFairfaxVaUsaAdapter extends LoggingObject implements
        ChurchDetailProvider {

    private static final String URL_HOME = "http://www.stleofairfax.com";
    private static final String URL_SCHEDULES = "http://stleofairfax.com/news-events/schedules/";
    private static final String URL_NEWS_RSS = "http://stleofairfax.com/category/news/feed";
    private static final Pattern REGEX_STREET_ADDRESS = Pattern
            .compile("3700 Old Lee Highway");
    private static final Pattern REGEX_CITY_STATE_ZIP = Pattern
            .compile("Fairfax, Virginia 22030");
    private static final Pattern REGEX_PHONE = Pattern.compile("703-273-5369");
    private static final Pattern REGEX_SAT_VIGIL_MASS = Pattern
            .compile("Saturday:.*?5:00pm \\(Sunday anticipation");
    private static final Pattern REGEX_SAT_DAILY_MASS = Pattern
            .compile("Saturday:.*?9:00am only");
    private static final Pattern REGEX_SUN_MASS = Pattern.compile(
            "Sunday:.*? 7:30am, 9:00am, 11:00am,.*?1:00pm \\(en .*?5:00p",
            Pattern.DOTALL);
    private static final Pattern REGEX_DAILY_MASS = Pattern
            .compile("Monday - Friday:.*?6:15am, 9:00am");
    private static final Pattern REGEX_FRIDAY_CONFESSION = Pattern
            .compile("Friday:.*?7:00pm - 8:00pm");
    private static final Pattern REGEX_SATURDAY_CONFESSION = Pattern
            .compile("Saturday:.*?3:30pm- 4:30pm</p>");
    private static final Pattern REGEX_ADORATION = Pattern
            .compile(
                    "Adoration . Holy Hour.*?Fridays.*?8:00-9:00pm.*?First Saturdays.*?9:30-10:00am.*?following Saturday morning Mass",
                    Pattern.DOTALL);

    private WebHelper webHelper = new WebHelper();

    @Override
    public ChurchDetail getChurchDetail() throws ParseException {

        ChurchDetail churchDetail = new ChurchDetail();
        try {
            churchDetail.setName("St. Leo the Great");
            churchDetail.setNameSlug("st-leo-the-great");
            churchDetail.setUrl(URL_HOME);
            churchDetail.setLat(38.85403);
            churchDetail.setLon(-77.296582);
            getContactInformation(churchDetail);
            getMasses(churchDetail);
            getConfessions(churchDetail);
            getAdoration(churchDetail);
            getEvents(churchDetail);
        } catch (Exception e) {
            String msg = "Parse error";
            logger.error(msg, e);
            throw new ParseException(msg, e);
        }

        return churchDetail;
    }

    private void getAdoration(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_SCHEDULES, REGEX_ADORATION)) {
            ChurchEvent event = new ChurchEvent(EventType.ADORATION, Day.FRI,
                    new LocalTime(20, 00));
            event.setRepeatType(RepeatType.WEEKLY);
            churchDetail.getEvents().add(event);
            event = new ChurchEvent(EventType.ADORATION, Day.FIRST_SAT,
                    new LocalTime(20, 00));
            event.setRepeatType(RepeatType.MONTHLY);
            churchDetail.getEvents().add(event);
        }
    }

    private void getMasses(ChurchDetail churchDetail) throws Exception {
        getSaturdayVigilMass(churchDetail);
        getSaturdayDailyMass(churchDetail);
        getSundayMasses(churchDetail);
        getWeekdayMasses(churchDetail);
    }

    private void getConfessions(ChurchDetail churchDetail) throws Exception {
        getFridayConfessions(churchDetail);
        getSaturdayConfessions(churchDetail);
    }

    private void getSaturdayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SATURDAY_CONFESSION)) {
            ChurchEvent event = new SaturdayConfession(15, 30);
            event.setStopTime(new LocalTime(16, 30));
            churchDetail.getEvents().add(event);
        }
    }

    private void getFridayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_FRIDAY_CONFESSION)) {
            ChurchEvent event = new FridayConfession(19, 00);
            event.setStopTime(new LocalTime(20, 00));
            churchDetail.getEvents().add(event);
        }
    }

    private void getWeekdayMasses(ChurchDetail churchDetail) throws Exception {
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
            churchDetail.getEvents().add(new SundayMass(11, 00));
            churchDetail.getEvents().add(new SundayMass(17, 00));
            ChurchEvent event = new SundayMass(13, 00);
            event.setLanguage(Language.SPANISH);
            churchDetail.getEvents().add(event);
        } else {
            throw new ParseException("Could not extract sunday masses.");
        }
    }

    private void getSaturdayVigilMass(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SAT_VIGIL_MASS)) {
            ChurchEvent event = new VigilMass(17, 00);
            churchDetail.getEvents().add(event);
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
        URL feedUrl = new URL(URL_NEWS_RSS);
        logger.info("parsing rss feed at " + URL_NEWS_RSS);
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
            churchDetail.setPhone("703-273-5369");
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
            churchDetail.setCity("Fairfax");
            churchDetail.setCitySlug("fairfax");
            churchDetail.setState("VA");
            churchDetail.setZip("22033");
        } else {
            throw new ParseException("Could not extract city, state and zip.");
        }
    }

    private void getStreetAddress(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_STREET_ADDRESS)) {
            churchDetail.setStreetAddress("3700 Old Lee Highway");
        } else {
            throw new ParseException("Could not extract street address.");
        }
    }
}
