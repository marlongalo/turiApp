package com.example.application.data.entity;

import javax.persistence.Entity;

@Entity
public class PackageModel extends AbstractEntity {

    private Integer packageID;
    private String image;
    private String namePackage;
    private String destiny;
    private Integer duration;
    private String hotel;
    private String activities;
    private Integer price;

    public Integer getPackageID() {
        return packageID;
    }
    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getNamePackage() {
        return namePackage;
    }
    public void setNamePackage(String namePackage) {
        this.namePackage = namePackage;
    }
    public String getDestiny() {
        return destiny;
    }
    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public String getHotel() {
        return hotel;
    }
    public void setHotel(String hotel) {
        this.hotel = hotel;
    }
    public String getActivities() {
        return activities;
    }
    public void setActivities(String activities) {
        this.activities = activities;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }

}
