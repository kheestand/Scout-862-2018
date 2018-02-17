package com.example.kyle.scout_862_template.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.kyle.scout_862_template.MatchScouting;
import com.example.kyle.scout_862_template.R;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by kyle on 6/3/17.
 */

public class AutoTab extends Fragment implements TabInterface {
    MatchDatabase matchDatabase = MatchScouting.matchDatabase;

    @BindView(R.id.interactive_field_picture_container)
    FrameLayout fieldContainer;
    @BindView(R.id.interactive_field_rotate_button)
    BootstrapButton rotateFieldButton;
    @BindView(R.id.left_Start_Position)
    CheckBox leftStartPos;
    @BindView(R.id.middle_Start_Position)
    CheckBox centerStartPos;
    @BindView(R.id.right_Start_Position)
    CheckBox rightStartPos;
    @BindView(R.id.auto_Scale_Add)
    BootstrapButton autoScaleAdd;
    @BindView(R.id.auto_Scale_Subtract)
    BootstrapButton autoScaleSubtract;
    @BindView(R.id.auto_Scale_Value_Counter)
    BootstrapLabel autoScaleValueCounter;
    int autoScaleScore = 1;
    @BindView(R.id.auto_Switch_Add)
    BootstrapButton autoSwitchAdd;
    @BindView(R.id.auto_Switch_Subtract)
    BootstrapButton autoSwitchSubtract;
    @BindView(R.id.auto_Switch_Value_Counter)
    BootstrapLabel autoSwitchValueCounter;
    @BindView(R.id.autoline_cross_bool)
    CheckBox crossedAutoLine;
    int autoSwitchScore = 1;
    @BindView(R.id.auto_Switch_LeftPos)
    CheckBox autoSwitchLeftPos;
    @BindView(R.id.auto_Switch_RightPos)
    CheckBox autoSwitchRightPos;
    @BindView(R.id.auto_Scale_LeftPos)
    CheckBox autoScaleLeftPos;
    @BindView(R.id.auto_Scale_RightPos)
    CheckBox autoScaleRightPos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_tab, container, false);
        ButterKnife.bind(this, view);
        autoSwitchValueCounter.setText(String.valueOf(autoSwitchScore));
        autoScaleValueCounter.setText(String.valueOf(autoScaleScore));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        writeTab();
    }

    @Override
    public void onStart() {
        super.onStart();
        readTab();
    }

    @Override
    public void readTab() {
        switch (matchDatabase.getInt(4)){
            case 1:
                leftStartPos.setChecked(true);
                centerStartPos.setChecked(false);
                rightStartPos.setChecked(false);
                break;
            case 2:
                leftStartPos.setChecked(false);
                centerStartPos.setChecked(true);
                rightStartPos.setChecked(false);
                break;
            case 3:
                leftStartPos.setChecked(false);
                centerStartPos.setChecked(false);
                rightStartPos.setChecked(true);
                break;
            default:
                leftStartPos.setChecked(false);
                centerStartPos.setChecked(false);
                rightStartPos.setChecked(false);
        }

        switch (matchDatabase.getInt(3)){
            case 0: crossedAutoLine.setChecked(false);
                break;
            case 1: crossedAutoLine.setChecked(true);
                break;
            default: crossedAutoLine.setChecked(false);
        }

        readSwitchValue();
        readScaleValue();
    }

    @Override
    public void writeTab() {
        if(leftStartPos.isChecked())
            matchDatabase.add(1,4);
        else if (centerStartPos.isChecked())
            matchDatabase.add(2,4);
        else if (rightStartPos.isChecked())
            matchDatabase.add(3,4);
        else
            matchDatabase.add(0,4);

        if(crossedAutoLine.isChecked())
            matchDatabase.add(1, 3);
        else
            matchDatabase.add(0, 3);

        writeSwitchValue();
        writeScaleValue();
    }

    /**
     * Game specific things
     */

    @OnClick(R.id.interactive_field_rotate_button)
    public void flipField() {
        if (fieldContainer.getRotation() == 180)
            fieldContainer.setRotation(0);
        else
            fieldContainer.setRotation(180);
    }

    @OnClick(R.id.auto_Switch_Add)
    public void addToAutoSwitch() {
        writeSwitchValue();
        if (autoSwitchScore < 2) {
            autoSwitchScore = autoSwitchScore + 1;
            autoScaleScore = autoScaleScore + 1;
            autoSwitchValueCounter.setText(String.valueOf(autoSwitchScore));
        }
        readSwitchValue();
    }

    @OnClick(R.id.auto_Switch_Subtract)
    public void subtractFromAutoSwitch() {
        writeSwitchValue();
        if (autoSwitchScore > 1) {
            autoSwitchScore = autoSwitchScore - 1;
            autoScaleScore = autoScaleScore - 1;
            autoSwitchValueCounter.setText(String.valueOf(autoSwitchScore));
        }
        readSwitchValue();
    }

    @OnClick(R.id.auto_Scale_Add)
    public void addToAutoScale() {
        writeScaleValue();
        if (autoScaleScore < 2) {
            autoScaleScore = autoScaleScore + 1;
            autoSwitchScore = autoSwitchScore + 1;
            autoScaleValueCounter.setText(String.valueOf(autoScaleScore));
        }
        readScaleValue();
    }

    @OnClick(R.id.auto_Scale_Subtract)
    public void subtractFromAutoScale() {
        writeScaleValue();
        if (autoScaleScore > 1) {
            autoScaleScore = autoScaleScore - 1;
            autoSwitchScore = autoSwitchScore - 1;
            autoScaleValueCounter.setText(String.valueOf(autoScaleScore));
        }
        readScaleValue();
    }

    /**
     * Check box constraints.
     * We only want one check box checked at one time
     */
    @OnCheckedChanged(R.id.auto_Switch_LeftPos)
    public void checkChLeftSwitch() {
        if(autoSwitchRightPos.isChecked()) {
            autoSwitchRightPos.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.auto_Switch_RightPos)
    public void checkChRightSwitch() {
        if (autoSwitchLeftPos.isChecked()) {
            autoSwitchLeftPos.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.auto_Scale_LeftPos)
    public void checkChLeftScale() {
        if(autoScaleRightPos.isChecked()) {
            autoScaleRightPos.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.auto_Scale_RightPos)
    public void checkChRightScale() {
        if(autoScaleLeftPos.isChecked()) {
            autoScaleLeftPos.setChecked(false);
        }
    }

    private void writeScaleValue() {
        if(autoScaleScore == 1 ){
            if(autoScaleRightPos.isChecked()) {
                matchDatabase.add(1,8);
            }
            else if(autoScaleLeftPos.isChecked()) {
                matchDatabase.add(1,7);
            }
            else {
                matchDatabase.add(0,8);
                matchDatabase.add(0,7);
            }
        }

        if(autoScaleScore == 2 ){
            if(autoScaleRightPos.isChecked()) {
                matchDatabase.add(1,12);
            }
            else if(autoScaleLeftPos.isChecked()) {
                matchDatabase.add(1,11);
            }
            else {
                matchDatabase.add(0,12);
                matchDatabase.add(0,11);
            }
        }
    }

    private void readScaleValue() {
        if(autoScaleScore == 1) {
            if(matchDatabase.getInt(8) == 1) {
                autoScaleLeftPos.setChecked(false);
                autoScaleRightPos.setChecked(true);
            }
            else autoScaleRightPos.setChecked(false);
            if(matchDatabase.getInt(7) == 1) {
                autoScaleRightPos.setChecked(false);
                autoScaleLeftPos.setChecked(true);
            }
            else autoScaleLeftPos.setChecked(false);
        }
        else if(autoScaleScore == 2) {
            if(matchDatabase.getInt(12) == 1) {
                autoScaleLeftPos.setChecked(false);
                autoScaleRightPos.setChecked(true);
            }
            else autoScaleRightPos.setChecked(false);
            if(matchDatabase.getInt(11) == 1) {
                autoScaleRightPos.setChecked(false);
                autoScaleLeftPos.setChecked(true);
            }
            else autoScaleLeftPos.setChecked(false);
        }
    }

    private void writeSwitchValue() {
        if(autoSwitchScore == 1 ){
            if(autoSwitchRightPos.isChecked()) {
                matchDatabase.add(1,6);
            }
            else if(autoSwitchLeftPos.isChecked()) {
                matchDatabase.add(1,5);
            }
            else {
                matchDatabase.add(0,6);
                matchDatabase.add(0,5);
            }
        }

        if(autoSwitchScore == 2 ){
            if(autoSwitchRightPos.isChecked()) {
                matchDatabase.add(1,10);
            }
            else if(autoSwitchLeftPos.isChecked()) {
                matchDatabase.add(1,9);
            }
            else {
                matchDatabase.add(0,10);
                matchDatabase.add(0,9);
            }
        }
    }

    private void readSwitchValue() {
        if(autoSwitchScore == 1) {
            if(matchDatabase.getInt(6) == 1) {
                autoSwitchLeftPos.setChecked(false);
                autoSwitchRightPos.setChecked(true);
            }
            else autoSwitchRightPos.setChecked(false);
            if(matchDatabase.getInt(5) == 1) {
                autoSwitchRightPos.setChecked(false);
                autoSwitchLeftPos.setChecked(true);
            }
            else autoSwitchLeftPos.setChecked(false);
        }
        else if(autoSwitchScore == 2) {
            if(matchDatabase.getInt(10) == 1) {
                autoSwitchLeftPos.setChecked(false);
                autoSwitchRightPos.setChecked(true);
            }
            else autoSwitchRightPos.setChecked(false);
            if(matchDatabase.getInt(9) == 1) {
                autoSwitchRightPos.setChecked(false);
                autoSwitchLeftPos.setChecked(true);
            }
            else autoSwitchLeftPos.setChecked(false);
        }
    }
}
