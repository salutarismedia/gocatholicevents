package com.sm.gce.adapters.st.john.the.apostle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sm.gce.common.exceptions.ParseException;
import com.sm.gce.common.model.ChurchDetail;
import com.sm.gce.common.model.ChurchDetailProvider;
import com.sm.gce.common.model.ChurchEvent;
import com.sm.gce.common.model.MondayConfession;
import com.sm.gce.common.model.SaturdayConfession;
import com.sm.gce.common.model.SaturdayMass;
import com.sm.gce.common.model.SundayEfMass;
import com.sm.gce.common.model.SundayMass;
import com.sm.gce.common.model.VigilMass;
import com.sm.gce.common.model.WednesdayConfession;
import com.sm.gce.common.model.WeeklyAdoration;
import com.sm.gce.common.model.WeeklyMass;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.Language;
import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.WebHelper;

public class StJohnTheApostleAdapter extends LoggingObject implements
        ChurchDetailProvider {

    private static final String URL_HOME = "http://www.saintjohnleesburg.org/";
    private static final String URL_EVENTS = "https://www.google.com/calendar/htmlembed?mode=AGENDA&height=600&wkst=1&bgcolor=%23FFFFFF&src=bulletin@stjohnleesburg.com&color=%23060D5E&src=1jrk50etc40rfqnk5hb9v9im8c@group.calendar.google.com&color=%23BE6D00&src=prqh4a7t7eiegul4t76n327q7g@group.calendar.google.com&color=%230D7813&src=oj8cs3uv3l0fc73ub8o26240o4@group.calendar.google.com&color=%23A32929&ctz=America/New_York";
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
            churchDetail.setCitySlug("leesburg");
            churchDetail.setStateSlug("va");
            getContactInformation(churchDetail);
            getLocation(churchDetail);
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
            event = new SundayEfMass(10, 30);
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

    private void getEvents(ChurchDetail churchDetail) throws Exception {
        Elements eventTables = downloadEventTables();
        Elements dates = downloadDates();
        if (eventTables.size() == dates.size()) {
            for (int i = 0; i < dates.size(); i++) {
                LocalDate localDate = extractLocalDateFromGoogleDate(dates
                        .get(i));
                if (localDate != null) {
                    Elements events = eventTables.get(i).select(
                            ".event-eventInfo");
                    if (events != null && events.size() > 0) {
                        parseEvents(churchDetail, localDate, eventTables.get(i));
                    } else {
                        throw new RuntimeException(
                                "Could not find events in event table");
                    }
                } else {
                    throw new RuntimeException("Could not parse date of "
                            + dates.get(i));
                }
            }
        } else {
            throw new RuntimeException(
                    "Dates and events were not the same length.  Extracted "
                            + eventTables.size() + " events and "
                            + dates.size() + " dates");
        }

    }

    private LocalDate extractLocalDateFromGoogleDate(Element date) {
        LocalDate localDate = null;
        if (date != null) {
            String strDate = date.html();
            logger.debug("extracted date of " + strDate);
            // date time strings available at:
            // http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html
            DateTimeFormatter formatter = DateTimeFormat
                    .forPattern("EEE MMM dd, yyyy");
            localDate = formatter.parseLocalDate(strDate);
        }
        return localDate;
    }

    private void parseEvents(ChurchDetail churchDetail, LocalDate date,
            Element eventsTable) {
        Elements googleEvents = eventsTable.select(".event");
        for (Element googleEvent : googleEvents) {
            ChurchEvent event = new ChurchEvent();
            event.setStartDate(date);
            setEventTime(event, googleEvent);
            setEventName(event, googleEvent);
            if (validEvent(event)) {
                churchDetail.getEvents().add(event);
            }
        }
    }

    private boolean validEvent(ChurchEvent event) {
        // scrub out invalid events in the event list
        // (these are handled elsewhere)
        return !event.getName().toLowerCase().startsWith("mass -")
                && !event.getName().toLowerCase()
                        .startsWith("mass in the extra")
                && !event.getName().toLowerCase().startsWith("confessions - ")
                && !event.getName().toLowerCase().startsWith("spanish mass - ")
                && !event.getName().toLowerCase().startsWith("baptisms - ");
    }

    private void setEventName(ChurchEvent event, Element googleEvent) {
        if (event != null && googleEvent != null) {
            Elements spans = googleEvent.select("span");
            if (spans.size() == 1) {
                String title = spans.get(0).text();
                logger.debug("Extracted event title of " + title);
                event.setName(title);
            } else {
                throw new RuntimeException(
                        "Invalid span count on google event:  expected 1 but was "
                                + spans.size());
            }
        } else {
            throw new RuntimeException("invalid event for title extraction");
        }
    }

    private void setEventTime(ChurchEvent event, Element googleEvent) {
        if (event != null && googleEvent != null) {
            Elements eventTime = googleEvent.select(".event-time");
            String strTime = eventTime.text();
            logger.debug("extracted time of " + strTime);
            Pattern extendedTimePattern = Pattern
                    .compile("(.*?):(\\d\\d)(am|pm)");
            Matcher extendedTimeMatcher = extendedTimePattern.matcher(strTime);
            Pattern shortTimePattern = Pattern.compile("(.*?)(am|pm)");
            Matcher shortTimeMatcher = shortTimePattern.matcher(strTime);
            if (!strTime.equals("")) {
                LocalTime localTime = null;
                if (extendedTimeMatcher.matches()) {
                    localTime = parseExtendedTimeString(strTime,
                            extendedTimeMatcher);
                    event.setStartTime(localTime);
                } else if (shortTimeMatcher.matches()) {
                    localTime = parseShortTimeString(strTime, shortTimeMatcher);
                    event.setStartTime(localTime);
                } else {
                    logger.error("Could not parse time for " + strTime);
                }
            } else {
                logger.error("No time was found for " + eventTime);
            }
        } else {
            throw new RuntimeException("invalid event for time extraction");
        }
    }

    private LocalTime parseShortTimeString(String strTime, Matcher matcher) {
        String strHour = matcher.group(1);
        String strAmPm = matcher.group(2);
        Integer intHour = Integer.parseInt(strHour);
        if (strAmPm.equals("pm") && intHour != 12) {
            intHour += 12;
        }
        logger.debug("Converted " + strTime + " to " + intHour + ":00");
        return new LocalTime(intHour, 00);
    }

    private LocalTime parseExtendedTimeString(String strTime, Matcher matcher) {
        String strHour = matcher.group(1);
        String strMin = matcher.group(2);
        String strAmPm = matcher.group(3);
        Integer intHour = Integer.parseInt(strHour);
        Integer intMin = Integer.parseInt(strMin);
        if (strAmPm.equals("pm") && intHour != 12) {
            intHour += 12;
        }
        logger.debug("Converted " + strTime + " to " + intHour + ":" + intMin);
        return new LocalTime(intHour, intMin);
    }

    private Elements downloadEventTables() throws Exception {
        Document doc = Jsoup.connect(URL_EVENTS).get();
        Elements events = doc.select(".events");
        return events;
    }

    private Elements downloadDates() throws Exception {
        Document doc = Jsoup.connect(URL_EVENTS).get();
        Elements dates = doc.select(".date");
        return dates;
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
