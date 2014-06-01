package com.sm.gce.adapters.st.john.the.beloved;

import java.util.regex.Pattern;

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
import com.sm.gce.common.model.WednesdayConfession;
import com.sm.gce.common.model.WeeklyMass;
import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;
import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.WebHelper;

public class StJohnTheBelovedAdapter extends LoggingObject implements
        ChurchDetailProvider {

    private static final String URL_HOME = "http://www.stjohncatholicmclean.org/";
    private static final String URL_EVENTS = "http://www.stjohncatholicmclean.org/events/default.html";
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
    private static final Pattern REGEX_WEDNESDAY_CONFESSION = Pattern
            .compile("WEDNESDAY:.*?6:30 .*? 7:15");
    private static final Pattern REGEX_SATURDAY_CONFESSION = Pattern
            .compile("SATURDAY:.*?After 8:15 .*? Mass.*?3:30 .*? to 4:30");
    private static final Pattern REGEX_FIRST_FRIDAY_CONFESSION = Pattern
            .compile("FIRST FRIDAY.*?After 9:00 .*? Mass");
    private static final Pattern REGEX_WEDNESDAY_ADORATION = Pattern
            .compile("WEDNESDAY:.*?10:00 .*? 7:30 .*?followed by.*?Miraculous Medal Novena.*?concluding with.*?Benediction");
    private static final Pattern REGEX_FIRST_FRIDAY_ADORATION = Pattern
            .compile("FIRST FRIDAY:.*?10:00 .*? to 8:00");
    private static final Pattern REGEX_FIRST_SATURDAY_ADORATION = Pattern
            .compile("FIRST SATURDAY:.*?Eucharistic Adoration and Benediction.*?following the.*?8:15 .*? Mass");

    private static final Pattern REGEX_FIRST_FRIDAY_DEVOTION = Pattern
            .compile(
                    "First Friday Devotions begin after the 9:00 AM Mass on First Friday with Exposition of the Blessed Sacrament. Please come spend an hour in the presence of Our Eucharistic Lord. A sign-up sheet is in the vestibule to assure adorers are present every hour",
                    Pattern.DOTALL);
    private static final Pattern REGEX_FIRST_SATURDAY_DEVOTION = Pattern
            .compile(
                    "First Saturday, the 8:15 AM Mass will be followed by exposition of.*?the Blessed Sacrament, devotions and Benediction. Come and pray.*?for peace and Respect for Life through Our Lady of Fatima",
                    Pattern.DOTALL);
    private static final Pattern REGEX_ST_JOSEPH_SOCIETY = Pattern
            .compile(
                    "Father Pollard leads a group of dads who gather on the third.*?Friday of every month to pray the rosary, down a drink, eat .*?some pizza and then listen to a short presentation on a",
                    Pattern.DOTALL);

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
        // these can be found on google maps by right clicking on
        // the point and selecting "what's here"
        churchDetail.setLat(38.924824);
        churchDetail.setLon(-77.161199);
    }

    private void getAdoration(ChurchDetail churchDetail) throws Exception {
        getWedAdoration(churchDetail);
        getFirstFriAdoration(churchDetail);
        getFirstSatAdoration(churchDetail);
    }

    private void getFirstSatAdoration(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_FIRST_SATURDAY_ADORATION)) {
            ChurchEvent event = new ChurchEvent();
            event.setEventType(EventType.ADORATION);
            event.setStartTime(new LocalTime(8, 45));
            event.setDay(Day.FIRST_SAT);
            event.setNote("following the 8:15 a.m. Mass");
            churchDetail.getEvents().add(event);
        }
    }

    private void getFirstFriAdoration(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_FIRST_FRIDAY_ADORATION)) {
            ChurchEvent event = new ChurchEvent();
            event.setEventType(EventType.ADORATION);
            event.setStartTime(new LocalTime(10, 00));
            event.setStopTime(new LocalTime(20, 00));
            event.setDay(Day.FIRST_FRI);
            churchDetail.getEvents().add(event);
        }
    }

    private void getWedAdoration(ChurchDetail churchDetail) throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_WEDNESDAY_ADORATION)) {
            ChurchEvent event = new ChurchEvent();
            event.setEventType(EventType.ADORATION);
            event.setStartTime(new LocalTime(10, 00));
            event.setStopTime(new LocalTime(19, 30));
            event.setDay(Day.WED);
            event.setNote("followed by Miraculous Medal Novena concluding with Benediction");
            churchDetail.getEvents().add(event);
        }
    }

    private void getMasses(ChurchDetail churchDetail) throws Exception {
        getSaturdayVigilMass(churchDetail);
        getSaturdayDailyMass(churchDetail);
        getSundayMasses(churchDetail);
        getDailyMasses(churchDetail);
    }

    private void getConfessions(ChurchDetail churchDetail) throws Exception {
        getWednesdayConfessions(churchDetail);
        getSaturdayConfessions(churchDetail);
        getFirstFridayConfessions(churchDetail);
    }

    private void getFirstFridayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_FIRST_FRIDAY_CONFESSION)) {
            ChurchEvent event = new ChurchEvent(EventType.CONFESSION);
            event.setDay(Day.FIRST_FRI);
            event.setStartTime(new LocalTime(9, 30));
            churchDetail.getEvents().add(event);
        }
    }

    private void getSaturdayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_SATURDAY_CONFESSION)) {
            churchDetail.getEvents().add(new SaturdayConfession(8, 45));
            ChurchEvent event = new SaturdayConfession(15, 30);
            event.setStopTime(new LocalTime(16, 30));
            churchDetail.getEvents().add(event);
        }
    }

    private void getWednesdayConfessions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_WEDNESDAY_CONFESSION)) {
            ChurchEvent event = new WednesdayConfession(18, 30);
            event.setStopTime(new LocalTime(19, 15));
            churchDetail.getEvents().add(event);
        }
    }

    private void getDailyMasses(ChurchDetail churchDetail) throws Exception {
        getMondayDailyMasses(churchDetail);
        getTueThroughFriMasses(churchDetail);
    }

    private void getTueThroughFriMasses(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_TUE_THROUGH_FRI_MASS)) {
            addTueThroughFriMasses(Day.TUE, churchDetail);
            addTueThroughFriMasses(Day.WED, churchDetail);
            addTueThroughFriMasses(Day.THU, churchDetail);
            addTueThroughFriMasses(Day.FRI, churchDetail);
        }
    }

    private void addTueThroughFriMasses(Day day, ChurchDetail churchDetail) {
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

    private void getEvents(ChurchDetail churchDetail) throws Exception {
        getFirstFridayDevotions(churchDetail);
        getFirstSaturdayDevotions(churchDetail);
        getStJosephSociety(churchDetail);
        getParishEvents(churchDetail);
    }

    private void getParishEvents(ChurchDetail churchDetail) {
        // TODO Auto-generated method stub
        
    }

    private void getStJosephSociety(ChurchDetail churchDetail)
            throws ParseException, Exception {
        if (webHelper.matches(URL_HOME, REGEX_ST_JOSEPH_SOCIETY)) {
            ChurchEvent event = new ChurchEvent();
            event.setDay(Day.THIRD_FRI);
            event.setEventType(EventType.OTHER);
            event.setName("St. Joseph Society");
            event.setUrl(URL_EVENTS);
            event.setDescription("Father Pollard leads a group of dads who gather on the third Friday of every month to pray the rosary, down a drink, eat some pizza and then listen to a short presentation on a topic of mutual interest. If you would like more information and are interested in attending, please send an e-mail to Father");
            churchDetail.getEvents().add(event);
        } else {
            throw new ParseException("Could not extract phone.");
        }
    }

    private void getFirstSaturdayDevotions(ChurchDetail churchDetail)
            throws Exception {
        if (webHelper.matches(URL_HOME, REGEX_FIRST_SATURDAY_DEVOTION)) {
            ChurchEvent event = new ChurchEvent();
            event.setDay(Day.FIRST_SAT);
            event.setStartTime(new LocalTime(8, 45));
            event.setNote("First Saturday Devotions - Begins after the 8:15 AM mass");
            event.setEventType(EventType.ADORATION);
            event.setName("First Saturday Devotions");
            event.setDescription("First Saturday, the 8:15 AM Mass will be followed by exposition of the Blessed Sacrament, devotions and Benediction. Come and pray for peace and Respect for Life through Our Lady of Fatima.");
            event.setUrl(URL_EVENTS);
            churchDetail.getEvents().add(event);
        } else {
            throw new ParseException("Could not extract phone.");
        }
    }

    private void getFirstFridayDevotions(ChurchDetail churchDetail)
            throws ParseException, Exception {
        if (webHelper.matches(URL_HOME, REGEX_FIRST_FRIDAY_DEVOTION)) {
            ChurchEvent event = new ChurchEvent();
            event.setDay(Day.FIRST_FRI);
            event.setStartTime(new LocalTime(9, 30));
            event.setNote("First Friday Devotions - Begins after the 9 AM mass");
            event.setEventType(EventType.ADORATION);
            event.setName("First Friday Devotions");
            event.setDescription("First Friday Devotions begin after the 9:00 AM Mass on First Friday with Exposition of the Blessed Sacrament. Please come spend an hour in the presence of Our Eucharistic Lord. A sign-up sheet is in the vestibule to assure adorers are present every hour.");
            event.setUrl(URL_EVENTS);
            churchDetail.getEvents().add(event);
        } else {
            throw new ParseException("Could not extract phone.");
        }
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
