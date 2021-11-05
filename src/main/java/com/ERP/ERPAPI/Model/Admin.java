package com.ERP.ERPAPI.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Admin {



    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
    private String valid;
    private Integer OTP;

    public Admin() {
    }

    public Admin( String username, String password, String valid, Integer OTP) {
        this.username = username;
        this.password = password;
        this.valid = valid;
        this.OTP = OTP;
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

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Integer getOTP() {
        return OTP;
    }

    public void setOTP(Integer OTP) {
        this.OTP = OTP;
    }
}
