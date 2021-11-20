package com.ERP.ERPAPI.Model;

public class AttendanceDTO {

    private String username;
    private String name;
    private Integer studentNo;

    public AttendanceDTO(String username, String name, Integer studentNo) {
        this.username = username;
        this.name = name;
        this.studentNo = studentNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(Integer studentNo) {
        this.studentNo = studentNo;
    }
}
