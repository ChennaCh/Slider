package com.fit.bloodmanagment.Beans;

public class ViewBloodCamp {
    private String organisername;
    private String venuename;
    private String address;
    private String email;
    private String mobile;
    private String city;
    private String date;
    private String time;
    private String descript;

    public ViewBloodCamp(String organisername, String venuename, String address, String email, String mobile, String city, String date, String time, String descript) {
        this.organisername = organisername;
        this.venuename = venuename;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.date = date;
        this.time = time;
        this.descript = descript;
    }

    public ViewBloodCamp() {

    }

    public String getOrganisername() {
        return organisername;
    }

    public void setOrganisername(String organisername) {
        this.organisername = organisername;
    }

    public String getVenuename() {
        return venuename;
    }

    public void setVenuename(String venuename) {
        this.venuename = venuename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
