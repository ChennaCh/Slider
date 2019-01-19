package com.fratelloinnotech.blooddonors.Beans;

public class Notifications {
    private String title;
    private String message;
    private String date;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Notifications() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Notifications(String title, String message, String date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

}
