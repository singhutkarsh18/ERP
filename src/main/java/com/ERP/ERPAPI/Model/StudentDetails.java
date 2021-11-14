package com.ERP.ERPAPI.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StudentDetails {

    @Id
    private Integer studentNo;
    private String name;
    private Long uniRollNo;
    private String course;
    private String branch;
    private String dob;
    private String sem;
    private String username;
    private Long mobNo;
    private String gender;
    private String category;
    private String father;
    private String mother;
    private Long fatherNo;
    private Long motherNo;
    private String address;
    private String district;
    private String city;
    private Integer pincode;
    private String state;
    private String cls;

    public StudentDetails(Integer studentNo, String name, Long uniRollNo, String course, String branch, String dob, String sem, String username, Long mobNo, String gender, String category, String father, String mother, Long fatherNo, Long motherNo, String address, String district, String city, Integer pincode,String state,String cls) {
        this.studentNo = studentNo;
        this.name = name;
        this.uniRollNo = uniRollNo;
        this.course = course;
        this.branch = branch;
        this.dob = dob;
        this.sem = sem;
        this.username = username;
        this.mobNo = mobNo;
        this.gender = gender;
        this.category = category;
        this.father = father;
        this.mother = mother;
        this.fatherNo = fatherNo;
        this.motherNo = motherNo;
        this.address = address;
        this.district = district;
        this.city = city;
        this.pincode = pincode;
        this.state=state;
        this.cls=cls;
    }

    public StudentDetails() {
    }

    public Integer getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(Integer studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUniRollNo() {
        return uniRollNo;
    }

    public void setUniRollNo(Long uniRollNo) {
        this.uniRollNo = uniRollNo;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getMobNo() {
        return mobNo;
    }

    public void setMobNo(Long mobNo) {
        this.mobNo = mobNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public Long getFatherNo() {
        return fatherNo;
    }

    public void setFatherNo(Long fatherNo) {
        this.fatherNo = fatherNo;
    }

    public Long getMotherNo() {
        return motherNo;
    }

    public void setMotherNo(Long motherNo) {
        this.motherNo = motherNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }
}
