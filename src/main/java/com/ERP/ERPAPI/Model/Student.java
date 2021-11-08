package com.ERP.ERPAPI.Model;

import org.springframework.data.relational.core.sql.In;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Boolean Valid;
    private Integer OTP;


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

    public Student(String name, String username, String password, Boolean Valid, Integer OTP) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.Valid = Valid;
        this.OTP = OTP;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Boolean getValid() {
        return Valid;
    }

    public void setValid(Boolean valid) {
        Valid = valid;
    }
    public Integer getOTP() {
        return OTP;
    }

    public void setOTP(Integer OTP) {
        this.OTP = OTP;
    }
}
