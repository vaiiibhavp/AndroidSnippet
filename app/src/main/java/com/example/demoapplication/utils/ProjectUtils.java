package com.example.demoapplication.utils;

import android.Manifest;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.CountDownTimer;


import java.util.Locale;

public class ProjectUtils {

    public static String[] getPermissionArray() {
        String ReadFile = Manifest.permission.READ_EXTERNAL_STORAGE;
        String WriteFile = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String background_Location = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
        String CallPhone = Manifest.permission.CALL_PHONE;
        String[] perm;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            perm = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    /*background_Location,*/ ReadFile, WriteFile, CallPhone};
        } else {
            perm = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    background_Location, ReadFile, WriteFile, CallPhone};
        }
        return perm;
    }


    public static double getDoubleFromString(String value) {
        double d = 0;
        if (!value.trim().equals("NA") && !value.trim().equals("")) {
            d = Double.parseDouble(value);
        }
        return d;
    }


    public static void checkLanguage(Context context) {
        try {
            String lg = Locale.getDefault().getDisplayLanguage();
            if (!lg.equals("en") && !lg.equalsIgnoreCase("English")) {
                ProjectUtils.setLocale(context, "en");
            }
            lg = Resources.getSystem().getConfiguration().locale.getLanguage();
            if (!lg.equals("en") && !lg.equalsIgnoreCase("English")) {
                ProjectUtils.setLocale(context, "en");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setLocale(Context context, String languageCode) {
        try {
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
