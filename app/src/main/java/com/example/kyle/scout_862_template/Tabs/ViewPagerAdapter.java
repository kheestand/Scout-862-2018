package com.example.kyle.scout_862_template.Tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kyle on 6/3/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public AutoTab autoFragment = new AutoTab();
    public TeleopTab teleopFragment = new TeleopTab();
    public EndGameTab endGameFragment = new EndGameTab();
    int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return autoFragment;
            case 1:
                return teleopFragment;
            case 2:
                return endGameFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
