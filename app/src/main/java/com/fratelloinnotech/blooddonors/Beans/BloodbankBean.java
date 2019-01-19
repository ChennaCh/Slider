package com.fratelloinnotech.blooddonors.Beans;

/**
 * Created by admin on 10/4/2017.
 */

public class BloodbankBean {

    String bbid,bbname,bbmobile,bblandline,bbemail,bbaddress,bbcity;

    public BloodbankBean(String bankid, String bankname, String bankmoblie, String banklandline, String bankemail,
                          String bankaddress, String bankcity){
        this.bbid = bankid;
        this.bbname = bankname;
        this.bbmobile = bankmoblie;
        this.bblandline = banklandline;
        this.bbemail = bankemail;
        this.bbaddress = bankaddress;
        this.bbcity = bankcity;

    }

    public String getId() {
        return bbid;
    }

    public void setId(String Bid) {
        this.bbid = bbid;
    }

    public String getBname() {
        return bbname;
    }

    public void setBname(String bbname) {
        this.bbname = bbname;
    }

    public String getBmobile() {
        return bbmobile;
    }

    public void setBmobile(String bbmobile) {
        this.bbmobile = bbmobile;
    }

    public String getBlandline() {
        return bblandline;
    }

    public void setBlandline(String bblandline) {
        this.bblandline = bblandline;
    }

    public String getBemail() {
        return bbemail;
    }

    public void setEemil(String bbemail) {this.bbemail = bbemail; }

    public String getBaddress() {
        return bbaddress;
    }

    public void setBaddress(String bbaddress) {
        this.bbaddress = bbaddress;
    }

    public String getBcity() {
        return bbcity;
    }

    public void setBcity(String bbcity) {
        this.bbcity = bbcity;
    }

}
