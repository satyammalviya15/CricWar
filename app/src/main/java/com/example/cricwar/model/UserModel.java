package com.example.cricwar.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String phone;
    private String username;

    private String emailname;
    private Timestamp createdTimestamp;

    public UserModel() {
    }

    public UserModel(String phone, String username,String emailname ,Timestamp createdTimestamp) {
        this.phone = phone;
        this.username = username;
        this.emailname=emailname;
        this.createdTimestamp = createdTimestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getEmail() {
        return emailname;
    }

    public void setEmail(String emailname) {
        this.emailname = emailname;
    }
}

