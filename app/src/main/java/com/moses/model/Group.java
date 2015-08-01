package com.moses.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {
    private Integer id;
    private String name;
    private Integer creator;
    @SerializedName("image")
    private String imageURL;
    private StatusEnum status;
    private List<User> members;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getCreator() {
        return creator;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
