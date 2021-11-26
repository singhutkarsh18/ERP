package com.ERP.ERPAPI.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Announcement {

    @Id
    @GeneratedValue
    private Integer id;
    private String date;
    private String subject;
    @Column(name = "announcement",length =450)
    private String announcement;

    public Announcement(String announcement,String date,String subject) {
        this.announcement = announcement;
        this.date=date;
        this.subject=subject;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
