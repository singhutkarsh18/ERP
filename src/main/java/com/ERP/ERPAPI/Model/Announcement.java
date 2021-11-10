package com.ERP.ERPAPI.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Announcement {

    @Id
    @GeneratedValue
    private Integer id;
    private String announcement;

    public Announcement(String announcement) {
        this.announcement = announcement;
    }

    public Announcement() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}
