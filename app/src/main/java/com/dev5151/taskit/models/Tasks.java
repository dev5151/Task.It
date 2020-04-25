package com.dev5151.taskit.models;

public class Tasks {
    private String title;
    private String description;
    private String basePrice;
    private String amount;
    private String location;
    private String unixTime;
    private Integer flag;
    private String creatorId;

    public Tasks(String title, String description, String basePrice, String amount, String location, String unixTime, String flag, String creatorId) {
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnixTime() {
        return unixTime;
    }

    public Tasks(Integer flag) {
        this.flag = flag;
    }

    public void setUnixTime(String unixTime) {
        this.unixTime = unixTime;
    }

    public Tasks(String title, String description, String basePrice, String amount, String location, String unixTime,Integer flag,String creatorId) {
        this.title = title;
        this.description = description;
        this.basePrice = basePrice;
        this.amount = amount;
        this.location = location;
        this.unixTime=unixTime;
        this.flag=flag;
        this.creatorId=creatorId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Tasks(){

    }
}
