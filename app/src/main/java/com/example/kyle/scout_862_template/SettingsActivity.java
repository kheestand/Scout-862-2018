package com.example.kyle.scout_862_template;

import android.os.Bundle;

import es.dmoral.toasty.Toasty;

/**
 * Created by kyle on 6/27/17.
 */

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.app_preferences);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Toasty.info(this, getResources().getString(R.string.setting_exit_toast)).show();
    }
}
