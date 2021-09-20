package com.example.demoapplication.RestAPI;

import java.util.regex.Pattern;

public class AppConstants {

    public static String MAIN_URL = "http://dms.prometteur.in/index.php/API/";

    public static String tokan = "token";

    public static String USERID = "userid";
    public static String USERIDVAL = "useridval";
    public static String MOBNO = "mobno";
    public static String ISLOGIN = "islogin";
    public static String USERSESSION = "usersession";
    public static String USERPROFILEKEY = "userprofilekey";
    public static String USERFNAMEKEY = "userfnamekey";
    public static String USEREMAILKEY = "useremailkey";
    public static String ORGID = "org_id";
    public static String ORGLATLONG = "org_lat_long";
    public static String VEHICLENO = "vehicleno";
    public static String VEHICLEID = "vehicleid";

    public static String USER_STATUS = "user_status";  //0-available 1-in-transit  2-in-dwell

    //GPS_TRACKER
    public static String GT_MY_CURRENT_LOCATION_LATITUDE = "mycurrentltionlatitude";//String
    public static String GT_MY_CURRENT_LOCATION_LONGITUDE = "mycurrentltionlongitude";//String

    private static Pattern email_pattern = Pattern.compile
            ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            );


    public static boolean checkEmail(String email) {
        return AppConstants.email_pattern.matcher(email).matches();
    }
}
