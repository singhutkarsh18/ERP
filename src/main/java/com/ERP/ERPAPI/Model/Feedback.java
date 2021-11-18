package com.ERP.ERPAPI.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Feedback

{

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private Integer sub1;
    private Integer sub2;
    private Integer sub3;
    private Integer sub4;
    private Integer sub5;
    private Integer sub6;

    public Feedback() {
    }

    public Feedback(Integer sub1,String username, Integer sub2, Integer sub3, Integer sub4, Integer sub5, Integer sub6) {
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
        this.sub4 = sub4;
        this.sub5 = sub5;
        this.sub6 = sub6;
        this.username=username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSub1() {
        return sub1;
    }

    public void setSub1(Integer sub1) {
        this.sub1 = sub1;
    }

    public Integer getSub2() {
        return sub2;
    }

    public void setSub2(Integer sub2) {
        this.sub2 = sub2;
    }

    public Integer getSub3() {
        return sub3;
    }

    public void setSub3(Integer sub3) {
        this.sub3 = sub3;
    }

    public Integer getSub4() {
        return sub4;
    }

    public void setSub4(Integer sub4) {
        this.sub4 = sub4;
    }

    public Integer getSub5() {
        return sub5;
    }

    public void setSub5(Integer sub5) {
        this.sub5 = sub5;
    }

    public Integer getSub6() {
        return sub6;
    }

    public void setSub6(Integer sub6) {
        this.sub6 = sub6;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
