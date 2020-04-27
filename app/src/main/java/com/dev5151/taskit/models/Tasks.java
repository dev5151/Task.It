package com.dev5151.taskit.models;

public class Tasks {
    private String title;
    private String desc;
    private String item_price;
    private String service_amt;
    private Integer state;
    private String uid;
    private String till_date;
    private String till_time;
    private String creation_time;
    private String creation_date;
    private String imgUrl;

    public Tasks() {

    }

    public Tasks(String title, String desc, String item_price, String service_amt, Integer state, String uid, String till_date, String till_time, String creation_time, String creation_date,String imgUrl) {
        this.title = title;
        this.desc = desc;
        this.item_price = item_price;
        this.service_amt = service_amt;
        this.state = state;
        this.uid = uid;
        this.till_date = till_date;
        this.till_time = till_time;
        this.creation_time = creation_time;
        this.creation_date = creation_date;
        this.imgUrl=imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getService_amt() {
        return service_amt;
    }

    public void setService_amt(String service_amt) {
        this.service_amt = service_amt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTill_date() {
        return till_date;
    }

    public void setTill_date(String till_date) {
        this.till_date = till_date;
    }

    public String getTill_time() {
        return till_time;
    }

    public void setTill_time(String till_time) {
        this.till_time = till_time;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
