package com.sm.gce.common.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "Adapters")
public class Adapter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(mappedBy = "adapter", cascade = CascadeType.ALL)
    private ChurchDetail churchDetail;
    private String name;
    private String path;
    private Boolean enabled;
    private String continent;
    private String country;
    private String state;
    private String city;
    
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTimeAsString")
    private LocalDateTime lastRunOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChurchDetail getChurchDetail() {
        return churchDetail;
    }

    public void setChurchDetail(ChurchDetail churchDetail) {
        this.churchDetail = churchDetail;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getLastRunOn() {
        return lastRunOn;
    }

    public void setLastRunOn(LocalDateTime lastRunOn) {
        this.lastRunOn = lastRunOn;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
