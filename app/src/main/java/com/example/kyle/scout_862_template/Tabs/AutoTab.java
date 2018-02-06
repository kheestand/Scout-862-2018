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
//// TODO: 2/3/18: create check box for opposite side switch--for further tele-op info...

    @BindView(R.id.interactive_field_picture_container)
    FrameLayout fieldContainer;
    @BindView(R.id.interactive_field_rotate_button)
    Button rotateFieldButton;
    @BindView(R.id.left_Start_Position)
    CheckBox leftStartPos;
    @BindView(R.id.middle_Start_Position)
    CheckBox centerStartPos;
    @BindView(R.id.right_Start_Position)
    CheckBox rightStartPos;
    @BindView(R.id.auto_Scale_Add)
    Button autoSwitchAdd;
    @BindView(R.id.auto_Scale_Subtract)
    Button autoScaleSubtract;
    @BindView(R.id.auto_Scale_Value_Counter)
    TextView autoScaleValueCounter;
    int autoScaleScore = 0;
    @BindView(R.id.auto_Switch_Add)
    Button autoScaleAdd;
    @BindView(R.id.auto_Switch_Subtract)
    Button autoSwitchSubtract;
    @BindView(R.id.auto_Switch_Value_Counter)
    TextView autoSwitchValueCounter;
    @BindView(R.id.autoline_cross_bool)
    CheckBox crossedAutoLine;
    int autoSwitchScore = 0;
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
        if(leftStartPos.isChecked())
            matchDatabase.add(0,10);
        else if (centerStartPos.isChecked())
            matchDatabase.add(1,10);
        else if (rightStartPos.isChecked())
            matchDatabase.add(2,10);
        else matchDatabase.add(-1,10);
    }

    @Override
    public void writeTab() {

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
        /**
         * if(condition)
         *      this
         *  else
         *      this
         */
        if (autoSwitchScore < 2) {
            autoSwitchScore = autoSwitchScore + 1;
            autoSwitchValueCounter.setText(autoSwitchScore);

        }
    }

    @OnClick(R.id.auto_Switch_Subtract)
    public void subtractFromAutoSwitch() {
        /**
         * if(condition)
         *      this
         *  else
         *      this
         */
        if (autoSwitchScore > 0) {
            autoSwitchScore = autoSwitchScore - 1;
            autoSwitchValueCounter.setText(autoSwitchScore);

        }
    }

    @OnClick(R.id.auto_Scale_Add)
    public void addToAutoScale() {
        /**
         * if(condition)
         *      this
         *  else
         *      this
         */
        if (autoScaleScore < 2) {
            autoScaleScore = autoScaleScore + 1;
            autoScaleValueCounter.setText(autoScaleScore);

        }
    }

    @OnClick(R.id.auto_Scale_Subtract)
    public void subtractFromAutoScale() {
        /**
         * if(condition)
         *      this
         *  else
         *      this
         */
        if (autoScaleScore < 0) {
            autoScaleScore = autoScaleScore - 1;
            autoScaleValueCounter.setText(autoScaleScore);

        }
    }

    @OnCheckedChanged(R.id.autoline_cross_bool)
    public void checkCrossedAutoLine() {
        if (crossedAutoLine.isChecked()){
            matchDatabase.add(1,9);

        }
        else matchDatabase.add(0,9);
    }

    @OnCheckedChanged(R.id.auto_Switch_LeftPos)
    public void setLeftSwitchStartPos() {
        if (autoSwitchRightPos.isChecked()){
            autoSwitchRightPos.setChecked(false);

        }
        else autoSwitchRightPos.setChecked(true);
        if (autoSwitchLeftPos.isChecked()){

        }
        else autoSwitchLeftPos.setChecked(true);
    }

    @OnCheckedChanged(R.id.auto_Switch_RightPos)
    public void setRightSwitchStartPos() {
        if (autoSwitchLeftPos.isChecked()){
            autoSwitchLeftPos.setChecked(false);

        }
        else autoSwitchLeftPos.setChecked(true);
        if (autoSwitchRightPos.isChecked()){

        }
        else autoSwitchRightPos.setChecked(true);
    }

    @OnCheckedChanged(R.id.auto_Scale_LeftPos)
    public void setLeftScaleStartPos() {
        if (autoScaleRightPos.isChecked()){
            autoScaleRightPos.setChecked(false);

        }
        else autoScaleRightPos.setChecked(true);
        if (autoScaleLeftPos.isChecked()){

        }
        else autoScaleLeftPos.setChecked(true);
    }

    @OnCheckedChanged(R.id.auto_Scale_RightPos)
    public void setRightScaleStartPos() {
        if (autoScaleLeftPos.isChecked()){
            autoScaleLeftPos.setChecked(false);

        }
        else autoScaleLeftPos.setChecked(true);
        if (autoScaleRightPos.isChecked()){

        }
        else autoScaleRightPos.setChecked(true);
    }

}
