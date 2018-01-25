package com.example.kyle.scout_862_template;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class PitScouting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouting);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int primaryColor = preferences.getInt(getResources().getString(R.string.primary_color_key), 000000);
        int accentColor = preferences.getInt(getResources().getString(R.string.accent_color_key), 000000);
        int textColor = preferences.getInt(getResources().getString(R.string.app_font_color_key), 000000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(primaryColor);
    }

}
