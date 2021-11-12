package com.ERP.ERPAPI.Model;

public class OTP {
    private int userOtp;
    private String username;

    public OTP(int userOtp, String username) {
        this.userOtp = userOtp;
        this.username = username;
    }

    public OTP() {
    }

    public int getUserOtp() {
        return userOtp;
    }

    public void setUserOtp(int userOtp) {
        this.userOtp = userOtp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
