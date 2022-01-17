package com.example.bookingtrick.model;

public class User {

    private String name;
    private String Uid;
    private String email;
    private String objectID;

    public User(String name, String uid, String email, String objectID) {
        this.name = name;
        this.Uid = uid;
        this.email = email;
        this.objectID = objectID;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }
}
