package com.fit.bloodmanagment.Beans;

/**
 * Created by admin on 10/4/2017.
 */

public class BloodbankBean {

    String id,gname,gpassword,gmobile,gemail,gbranch,ggender,gqualification,gabout;

    public BloodbankBean(String gid, String gtname, String gtpassword, String gtmobile, String gtemail,
                          String gtbranch, String gtgender, String gtqualification, String gtabount){
        this.id = gid;
        this.gname = gtname;
        this.gpassword = gtpassword;
        this.gmobile = gtmobile;
        this.gemail = gtemail;
        this.gbranch = gtbranch;
        this.ggender = gtgender;
        this.gqualification = gtqualification;
        this.gabout = gtabount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGbranch() {
        return gbranch;
    }

    public void setGbranch(String gbranch) {
        this.gbranch = gbranch;
    }

    public String getGgender() {
        return ggender;
    }

    public void setGgender(String ggender) {
        this.ggender = ggender;
    }

    public String getGqualification() {
        return gqualification;
    }

    public void setGqualification(String gqualification) {
        this.gqualification = gqualification;
    }

    public String getGabout() {
        return gabout;
    }

    public void setGabout(String gabout) {
        this.gabout = gabout;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGpassword() {
        return gpassword;
    }

    public void setGpassword(String gpassword) {
        this.gpassword = gpassword;
    }

    public String getGmobile() {
        return gmobile;
    }

    public void setGmobile(String gmobile) {
        this.gmobile = gmobile;
    }

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

}
