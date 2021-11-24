package com.ERP.ERPAPI.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TeacherDetails")
public class TeacherDetails {

    @Id
    private Integer teacherId;
    private String name;
    private String department;
    private String dob;
    private String username;
    private String category;
    private String city;
    private String district;
    private String ug;
    private String pg;
    private String phd;
    private String specialization;
    private String mob;
    private String gender;
    private String address;
    private String state;
    private Integer pincode;
    private String ugYear;
    private String pgYear;
    private String phdYear;
    private String profilepic;

    public TeacherDetails(Integer teacherId,String name, String department, String dob, String username, String category, String city, String district, String ug, String pg, String phd, String specialization, String mob, String gender, String address, String state, Integer pincode, String ugYear, String pgYear, String phdYear) {
        this.teacherId = teacherId;
        this.name=name;
        this.department = department;
        this.dob = dob;
        this.username = username;
        this.category = category;
        this.city = city;
        this.district = district;
        this.ug = ug;
        this.pg = pg;
        this.phd = phd;
        this.specialization = specialization;
        this.mob = mob;
        this.gender = gender;
        this.address = address;
        this.state = state;
        this.pincode = pincode;
        this.ugYear = ugYear;
        this.pgYear = pgYear;
        this.phdYear = phdYear;
    }

    public TeacherDetails() {
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUg() {
        return ug;
    }

    public void setUg(String ug) {
        this.ug = ug;
    }

    public String getPg() {
        return pg;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public String getPhd() {
        return phd;
    }

    public void setPhd(String phd) {
        this.phd = phd;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getUgYear() {
        return ugYear;
    }

    public void setUgYear(String ugYear) {
        this.ugYear = ugYear;
    }

    public String getPgYear() {
        return pgYear;
    }

    public void setPgYear(String pgYear) {
        this.pgYear = pgYear;
    }

    public String getPhdYear() {
        return phdYear;
    }

    public void setPhdYear(String phdYear) {
        this.phdYear = phdYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
