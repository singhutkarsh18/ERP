package com.ERP.ERPAPI.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class ImageModel {

    @Id
    @GeneratedValue
    private Integer id;
    private String imageName;
    private String username;

    public ImageModel() {
    }

    public ImageModel(String imageName, String username) {
        this.imageName = imageName;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
