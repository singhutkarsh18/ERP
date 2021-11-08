package com.ERP.ERPAPI.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Report {

    @Id
    @GeneratedValue
    private Integer id;
    private String user;
    private String problem;
    private String date;

    public Report(String user, String problem, String date) {
        this.user = user;
        this.problem = problem;
        this.date = date;
    }

    public Report() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
