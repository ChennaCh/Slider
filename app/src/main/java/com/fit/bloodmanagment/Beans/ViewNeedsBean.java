package com.fit.bloodmanagment.Beans;

/**
 * Created by admin on 10/23/2017.
 */

public class ViewNeedsBean {
    String nid;
    String nname;
    String nemail;
    String nmobile;
    String naddress;
    String nbloodgroup;
    String npurpose;
    String nduedate;
    String ncity;
    public ViewNeedsBean(String vneedid,String vneedname,String vneedemail,String vneedmobile,String vneedaddress,String vneedbloodgroup,String vneedpurpose,String vneeddate,String vneedcity){
        this.nid=vneedid;
        this.nname = vneedname;
        this.nemail = vneedemail;
        this.nmobile = vneedmobile;
        this.naddress = vneedaddress;
        this.nbloodgroup = vneedbloodgroup;
        this.npurpose = vneedpurpose;
        this.nduedate=vneeddate;
        this.ncity=vneedcity;

    }
    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getNemail() {
        return nemail;
    }

    public void setNemail(String nemail) {
        this.nemail = nemail;
    }

    public String getNmobile() {
        return nmobile;
    }

    public void setNmobile(String nmobile) {
        this.nmobile = nmobile;
    }

    public String getNaddress() {
        return naddress;
    }

    public void setNaddress(String naddress) {
        this.naddress = naddress;
    }

    public String getNbloodgroup() {
        return nbloodgroup;
    }

    public void setNbloodgroup(String nbloodgroup) {
        this.nbloodgroup = nbloodgroup;
    }

    public String getNpurpose() {return npurpose;}

    public void setNpurpose(String npurpose) {
        this.npurpose = npurpose;
    }

    public String getNduedate() {
        return nduedate;
    }

    public void setNduedate(String nduedate) {
        this.nduedate = nduedate;
    }

    public String getNcity() {
        return ncity;
    }

    public void setNcity(String ncity) {
        this.ncity = ncity;
    }

    //columns: id,name,email,mobile,address,bloodgroup,city,purpose,duedate

}
