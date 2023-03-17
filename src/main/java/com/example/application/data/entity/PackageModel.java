package com.example.application.data.entity;

import javax.persistence.Entity;

@Entity
public class PackageModel extends AbstractEntity {

    private Long packageid;
    private String namepackage;
    private String destiny;
    private String hotel;
    private String activities;
    
    public Long getPackageID() {
        return packageid;
    }
    public void setPackageID(Long packageID) {
        this.packageid = packageID;
    }
   
    public String getNamePackage() {
        return namepackage;
    }
    public void setNamePackage(String namePackage) {
        this.namepackage = namePackage;
    }
    public String getDestiny() {
        return destiny;
    }
    public void setDestiny(String destiny) {
        this.destiny = destiny;
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
    

}
