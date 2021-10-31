package com.ERP.ERPAPI.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Admin {



    @Id
    @GeneratedValue
    private Integer id;

    private String email;
    private String password;


    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admin() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id.equals(admin.id) && email.equals(admin.email) && password.equals(admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
