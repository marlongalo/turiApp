package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Entity
public class ClientModel extends AbstractEntity {

    private Long clientid;
    private String name;
    private String address;
    private String phone;
    @Email
    private String email;

    public Long getClientID() {
        return clientid;
    }
    public void setClientID(Long clientID) {
        this.clientid = clientID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
