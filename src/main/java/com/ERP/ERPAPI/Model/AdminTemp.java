package com.ERP.ERPAPI.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AdminTemp
{



    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Boolean valid;
    private Integer OTP;

    public AdminTemp() {
    }

    public AdminTemp( String name,String username, String password, Boolean valid, Integer OTP) {
        this.name=name;
        this.username = username;
        this.password = password;
        this.valid = valid;
        this.OTP = OTP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getOTP() {
        return OTP;
    }

    public void setOTP(Integer OTP) {
        this.OTP = OTP;
    }
}
