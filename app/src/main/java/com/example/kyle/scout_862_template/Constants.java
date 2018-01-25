package com.example.kyle.scout_862_template;

import android.content.SharedPreferences;

/**
 * Created by kyle on 9/14/17.
 */

public abstract class Constants {

    //Set the positive and negative text for the match database
    //Instead of 1/0 you could use TRUE/FALSE as the string values
    public static final String positiveBoolean = "1";
    public static final String negativeBoolean = "0";
    public static SharedPreferences preferences;
    public static int primaryColor;
    public static int accentColor;
    public static int textColor;
    public static String year;
    public static String databasePath;
    public static String picturePath;
    public static String blueAlliancePath;
}
