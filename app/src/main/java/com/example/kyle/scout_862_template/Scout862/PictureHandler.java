package com.example.kyle.scout_862_template.Scout862;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import com.example.kyle.scout_862_template.R;

import java.io.File;

/**
 * Created by kyle on 8/19/17.
 */

public class PictureHandler {
    private final String ROBOT_PICTURE_PREFIX = "robot_";
    SharedPreferences preferences;
    private Context context;
    private String pictureDirectory;
    private String[] validFileExtensions = {".jpg", "jpeg", ".png", ".bmp", ".gif"};

    public PictureHandler(Context current) {
        this.context = current;
        preferences = PreferenceManager.getDefaultSharedPreferences(current);
        pictureDirectory = preferences.getString("scout862PicturePath", "");
    }

    public Bitmap getRobotPicture(String teamNum) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        for (int i = 0; i < validFileExtensions.length; i++) {
            String path = pictureDirectory + ROBOT_PICTURE_PREFIX + teamNum + validFileExtensions[i];
            File picture = new File(path);
            if (picture.exists())
                return BitmapFactory.decodeFile(path, options);
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_match_robot);
    }

    public Bitmap getRobotPicture(int teamNum) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        for (int i = 0; i < validFileExtensions.length; i++) {
            String path = pictureDirectory + "/" + ROBOT_PICTURE_PREFIX + teamNum + validFileExtensions[i];
            File picture = new File(path);
            if (picture.exists())
                return BitmapFactory.decodeFile(path, options);
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_match_robot);
    }


}
