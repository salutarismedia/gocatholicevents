package com.sm.gce.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;
import com.sm.gce.common.model.enums.Language;
import com.sm.gce.common.model.enums.RepeatType;
import com.sm.gce.xml.adapters.LocalDateAdapter;
import com.sm.gce.xml.adapters.LocalTimeAdapter;

/**
 * this models any event at the church e.g. mass, confession, adoration,
 * pro-life meeting, etc.
 */
@XmlRootElement
@Entity
@Table(name = "ChurchEvents")
public class ChurchEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "church_detail_id")
    private ChurchDetail churchDetail;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @OneToMany(mappedBy = "churchEvent", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tag> tags = new ArrayList<Tag>();
    // these components are used often enough separately to avoid
    // using a composite DateTime object
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateAsString")
    private LocalDate startDate;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateAsString")
    private LocalDate stopDate;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTimeAsString")
    private LocalTime startTime;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTimeAsString")
    private LocalTime stopTime;
    @Enumerated(EnumType.STRING)
    private RepeatType repeatType = RepeatType.NONE;
    private String url;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EventType eventType = EventType.OTHER;
    @Enumerated(EnumType.STRING)
    private Day day;
    @Enumerated(EnumType.STRING)
    private Language language = Language.ENGLISH;
    private String note;

    public ChurchEvent() {
        repeatType = RepeatType.NONE;
        eventType = EventType.OTHER;
    }

    public ChurchEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public ChurchEvent(EventType eventType, LocalTime startTime) {
        this.eventType = eventType;
        this.startTime = startTime;
    }

    public ChurchEvent(EventType eventType, Day day, LocalTime startTime) {
        this.eventType = eventType;
        this.day = day;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChurchDetail getChurchDetail() {
        return churchDetail;
    }

    public void setChurchDetail(ChurchDetail churchDetail) {
        this.churchDetail = churchDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RepeatType getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(RepeatType repeat) {
        this.repeatType = repeat;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }

    @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeAdapter.class)
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeAdapter.class)
    public LocalTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalTime stopTime) {
        this.stopTime = stopTime;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
