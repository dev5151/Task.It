package com.dev5151.taskit.models;

import java.util.List;

public class User {

    private String name, email, phone, profilePic, wallet;
    private int rating;
    private String location;
    private com.dev5151.taskit.models.LatLng latLng;
    private List<String> taskGiven;
    private List<String> taskDone;
    private List<TaskRequestModel> taskRequestList;
    private String token;
    private String uid;

    public User() {

    }

    public User(String name, String email, String phone, String profilePic, String wallet, int rating, String location, LatLng latLng, List<String> taskGiven, List<String> taskDone, List<TaskRequestModel> taskRequestList, String token, String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profilePic = profilePic;
        this.wallet = wallet;
        this.rating = rating;
        this.location = location;
        this.latLng = latLng;
        this.taskGiven = taskGiven;
        this.taskDone = taskDone;
        this.taskRequestList = taskRequestList;
        this.token = token;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public List<String> getTaskGiven() {
        return taskGiven;
    }

    public void setTaskGiven(List<String> taskGiven) {
        this.taskGiven = taskGiven;
    }

    public List<String> getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(List<String> taskDone) {
        this.taskDone = taskDone;
    }

    public List<TaskRequestModel> getTaskRequestList() {
        return taskRequestList;
    }

    public void setTaskRequestList(List<TaskRequestModel> taskRequestList) {
        this.taskRequestList = taskRequestList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
