package com.sm.gce.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.sm.gce.common.model.enums.Day;
import com.sm.gce.common.model.enums.EventType;
import com.sm.gce.xml.adapters.LocalDateTimeAdapter;

/**
 * This model describes what information we want to glean from a particular
 * church
 */
@XmlRootElement
@Entity
@Table(name = "ChurchDetails")
public class ChurchDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "adapter_id")
    private Adapter adapter;
    private String name;
    private String nameSlug;
    private String streetAddress;
    private String city;
    private String citySlug;
    private String state;
    private String stateSlug;
    private String zip;
    private String country;
    private String description;
    private String phone;
    private String url;
    private String hours;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTimeAsString")
    private LocalDateTime updatedOn;
    private Double lat;
    private Double lon;

    @OneToMany(mappedBy = "churchDetail", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<ChurchEvent> events = new ArrayList<ChurchEvent>();

    public void refreshHibernateRelationships() {
        for (ChurchEvent churchEvent : getEvents()) {
            churchEvent.setChurchDetail(this);
            for (Tag tag : churchEvent.getTags()) {
                tag.setChurchEvent(churchEvent);
            }
        }
    }

    public List<ChurchEvent> getEvent(EventType event) {
        List<ChurchEvent> foundEvents = new ArrayList<ChurchEvent>();
        for (ChurchEvent churchEvent : events) {
            if (churchEvent.getEventType() != null) {
                if (churchEvent.getEventType().equals(event)) {
                    foundEvents.add(churchEvent);
                }
            }
        }
        return foundEvents;
    }

    public List<ChurchEvent> getEvent(EventType event, Day day) {
        List<ChurchEvent> foundEvents = new ArrayList<ChurchEvent>();
        for (ChurchEvent churchEvent : events) {
            if (churchEvent.getEventType() != null
                    && churchEvent.getDay() != null) {
                if (churchEvent.getEventType().equals(event)
                        && churchEvent.getDay().equals(day)) {
                    foundEvents.add(churchEvent);
                }
            }
        }
        return foundEvents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeAdapter.class)
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<ChurchEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ChurchEvent> events) {
        this.events = events;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getNameSlug() {
        return nameSlug;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public String getCitySlug() {
        return citySlug;
    }

    public void setCitySlug(String citySlug) {
        this.citySlug = citySlug;
    }

    public String getStateSlug() {
        return stateSlug;
    }

    public void setStateSlug(String stateSlug) {
        this.stateSlug = stateSlug;
    }

}
