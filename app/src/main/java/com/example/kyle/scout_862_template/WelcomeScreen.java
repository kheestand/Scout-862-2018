package com.example.kyle.scout_862_template;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;

import es.dmoral.toasty.Toasty;
import gr.net.maroulis.library.EasySplashScreen;

public class WelcomeScreen extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int primaryColor = preferences.getInt(getResources().getString(R.string.primary_color_key), 000000);
        int accentColor = preferences.getInt(getResources().getString(R.string.accent_color_key), 000000);
        int textColor = preferences.getInt(getResources().getString(R.string.app_font_color_key), 000000);
        String timeOutFromPref = preferences.getString(getResources().getString(R.string.splash_timeout_key), "4000");

        if (!validTimeoutValue(timeOutFromPref)) {
            timeOutFromPref = "4000";
        }

        EasySplashScreen config = new EasySplashScreen(WelcomeScreen.this)
                .withFullScreen()
                .withTargetActivity(MainMenu.class)
                .withSplashTimeOut(Integer.parseInt(timeOutFromPref))
                .withBackgroundColor(primaryColor)
                .withHeaderText("")
                .withFooterText("Copyright 2017")
                .withBeforeLogoText(getResources().getString(R.string.app_name))
                .withLogo(R.drawable.logo)
                .withAfterLogoText("Lightning Robotics 2017-2018 Scouting App!");
        //change text color
        config.getHeaderTextView().setTextColor(accentColor);

        //finally create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);
    }

    private boolean validTimeoutValue(String input) {
        char[] inputArray = input.toCharArray();

        for (int index = 0; index < inputArray.length; index++) {
            if (inputArray[index] < 48 || inputArray[index] > 71) {
                Toasty.error(this, getResources().getString(R.string.invalid_timeout_time)).show();
                return false;
            }
        }
        return true;
    }

}
