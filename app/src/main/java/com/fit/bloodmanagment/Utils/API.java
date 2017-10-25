package com.fit.bloodmanagment.Utils;

/**
 * Created by HOME on 10/7/2017.
 */

public class API {

    public static String bburl = "http://www.fratelloinnotech.com/saveworld/getbloodbanks.php";
    public static String registerUrl="http://www.fratelloinnotech.com/saveworld/newdonor.php";
//    http://www.fratelloinnotech.com/saveworld/newdonor.php
//    method: post
//    columns: fullname,email,password,mobile,gender,address,bloodgroup,age,city
//    result: success/failure
public static String addbloodneedUrl="http://www.fratelloinnotech.com/saveworld/addbloodneed.php";
// name,email,mobile,address,bloodgroup,city,purpose,duedate
//    login/all donors
public static String getalldonorsurl="http://www.fratelloinnotech.com/saveworld/getdonors.php";
//    method: post
//    columns: fullname,email,password,mobile,gender,address,bloodgroup,age,city
//    result: records

//    getallrecords:
//
   public static String getbloodneedsurl="http://www.fratelloinnotech.com/saveworld/getbloodneeds.php";
//    method: post
//    columns: id,name,email,mobile,address,bloodgroup,city,purpose,duedate
//    result: records
}
