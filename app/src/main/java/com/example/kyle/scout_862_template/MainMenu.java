package com.example.kyle.scout_862_template;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import es.dmoral.toasty.Toasty;

public class MainMenu extends AppCompatActivity {

    static File selectedFileFromPicker;
    @BindView(R.id.goToPitSheet)
    BootstrapThumbnail choicePit;
    @BindView(R.id.goToMatchSheet)
    BootstrapThumbnail choiceMatches;
    @BindView(R.id.goToSettings)
    BootstrapThumbnail choiceSettings;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setConstants();
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setColors();
    }

    @OnClick(R.id.goToMatchSheet)
    public void matchSheetPress() {
        Intent activityTransfer = new Intent(MainMenu.this, MatchScouting.class);
        launchFileExplorer(activityTransfer);
    }

    @OnClick(R.id.goToPitSheet)
    public void pitSheetPress() {
        Intent activityTransfer = new Intent(MainMenu.this, PitScouting.class);
        launchFileExplorer(activityTransfer);
    }

    @OnClick(R.id.goToSettings)
    public void settingsPress() {
        Intent activityTransfer = new Intent(MainMenu.this, SettingsActivity.class);
        MainMenu.this.startActivity(activityTransfer);
    }

    @OnLongClick(R.id.goToSettings)
    public boolean ImportSettings() {
        return true;
    }

    @OnLongClick(R.id.goToMatchSheet)
    public boolean pitSheetPressNoSheet() {
        Intent activityTransfer = new Intent(MainMenu.this, MatchScouting.class);
        Toasty.error(this, "Preceding in demo mode. No data will be written").show();
        MainMenu.this.startActivity(activityTransfer);
        return true;
    }

    private void setConstants() {
        Constants.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Constants.year = Constants.preferences.getString("scout862CompetitionYear", "2000");
        Constants.primaryColor = Constants.preferences.getInt(getResources().getString(R.string.primary_color_key), 000000);
        Constants.accentColor = Constants.preferences.getInt(getResources().getString(R.string.accent_color_key), 000000);
        Constants.textColor = Constants.preferences.getInt(getResources().getString(R.string.app_font_color_key), 000000);
        Constants.databasePath = Environment.getExternalStorageDirectory().getPath() + Constants.preferences.getString(getResources().getString(R.string.database_path_prefs_key), "");
        Constants.databasePath = Constants.databasePath.replace("#YEAR#", Constants.year);
        Constants.picturePath = Environment.getExternalStorageDirectory().getPath() + Constants.preferences.getString(getResources().getString(R.string.picture_path_prefs_key), "");
        Constants.picturePath = Constants.picturePath.replace("#YEAR#", Constants.year);
        Constants.blueAlliancePath = Environment.getExternalStorageDirectory().getPath() + Constants.preferences.getString(getResources().getString(R.string.blue_alliance_database_key), "");
        Constants.blueAlliancePath = Constants.blueAlliancePath.replace("#YEAR#", Constants.year);
    }

    private void launchFileExplorer(final Intent nextActivity) {
        File databaseDir = new File(Constants.databasePath);
        File pictureDir = new File(Constants.picturePath);
        File blueAllianceDir = new File(Constants.blueAlliancePath);
        if (!databaseDir.exists())
            databaseDir.mkdirs();
        if (!pictureDir.exists())
            pictureDir.mkdirs();
        if (!blueAllianceDir.exists())
            blueAllianceDir.mkdirs();

        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = databaseDir;
        properties.error_dir = new File("/");
        properties.extensions = null;
        FilePickerDialog dialog = new FilePickerDialog(MainMenu.this, properties);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                selectedFileFromPicker = new File(files[0]);
                MainMenu.this.startActivity(nextActivity);
            }
        });
        dialog.show();
    }

    private void setColors() {
        toolbar.setTitleTextColor(Constants.textColor);
        toolbar.setBackgroundColor(Constants.primaryColor);
    }
}
