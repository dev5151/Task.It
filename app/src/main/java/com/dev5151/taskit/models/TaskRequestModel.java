package com.dev5151.taskit.models;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskRequestModel {
    private String taskId;
    private String name;
    private Float rating;
    private String title;
    private String imgUrl;
    private String applicantUid;

    public TaskRequestModel() {
    }

    public TaskRequestModel(String taskId, String name, Float rating, String title, String imgUrl, String applicantUid) {
        this.taskId = taskId;
        this.name = name;
        this.rating = rating;
        this.title = title;
        this.imgUrl = imgUrl;
        this.applicantUid = applicantUid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getApplicantUid() {
        return applicantUid;
    }

    public void setApplicantUid(String applicantUid) {
        this.applicantUid = applicantUid;
    }
}
