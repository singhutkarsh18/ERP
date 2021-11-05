package com.ERP.ERPAPI.Model;

public class StudentDTO {

    private String username;
    private String password;

    public StudentDTO() {
    }

    public StudentDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
