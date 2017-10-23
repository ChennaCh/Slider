package com.fit.bloodmanagment.Beans;

/**
 * Created by admin on 10/22/2017.
 */

public class DonorBean {
    //columns: fullname,email,password,mobile,gender,address,bloodgroup,age,city
    String did;
    String dfullname;
    String demail;
    String dpassword;
    String dmobile;
    String dgender;
    String daddress;
    String dbloodgroup;
    String dage;
    String dcity;
    public DonorBean(String donorid,String donorname,String donoremail,String donorpassword,String donormobile,String donorgender,String donoraddress,String donorbloodgroup,
                     String donorage,String donorcity){
        this.did=donorid;
        this.dfullname = donorname;
        this.demail = donoremail;
        this.dpassword = donorpassword;
        this.dmobile = donormobile;
        this.dgender = donorgender;
        this.daddress = donoraddress;
        this.dbloodgroup = donorbloodgroup;
        this.dage=donorage;
        this.dcity=donorcity;

    }



    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDfullname() {
        return dfullname;
    }

    public void setDfullname(String dfullname) {
        this.dfullname = dfullname;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getDpassword() {
        return dpassword;
    }

    public void setDpassword(String dpassword) {
        this.dpassword = dpassword;
    }

    public String getDmobile() {
        return dmobile;
    }

    public void setDmobile(String dmobile) {
        this.dmobile = dmobile;
    }

    public String getDgender() {
        return dgender;
    }

    public void setDgender(String dgender) {
        this.dgender = dgender;
    }

    public String getDaddress() {
        return daddress;
    }

    public void setDaddress(String daddress) {
        this.daddress = daddress;
    }

    public String getDbloodgroup() {
        return dbloodgroup;
    }

    public void setDbloodgroup(String dbloodgroup) {
        this.dbloodgroup = dbloodgroup;
    }

    public String getDage() {
        return dage;
    }

    public void setDage(String dage) {
        this.dage = dage;
    }

    public String getDcity() {
        return dcity;
    }

    public void setDcity(String dcity) {
        this.dcity = dcity;
    }


}
