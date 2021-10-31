package com.ERP.ERPAPI.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;
//    private Boolean enabled;
    private String password;
//    private String verification_code;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public Student(String name, String email, Boolean enabled, String password, String verification_code) {
        this.name = name;
        this.email = email;
//        this.enabled = enabled;
        this.password = password;
//        this.verification_code = verification_code;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Boolean getEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(Boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public String getVerification_code() {
//        return verification_code;
//    }
//
//    public void setVerification_code(String verification_code) {
//        this.verification_code = verification_code;
//    }
}
