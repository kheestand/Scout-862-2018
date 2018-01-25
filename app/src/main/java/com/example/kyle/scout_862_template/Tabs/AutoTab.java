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

    @BindView(R.id.interactive_field_colorbox_1)
    CheckBox colorCheckboxOne;
    @BindView(R.id.interactive_field_colorbox_2)
    CheckBox colorCheckboxTwo;
    @BindView(R.id.interactive_field_colorbox_3)
    CheckBox colorCheckboxThree;
    @BindView(R.id.interactive_field_colorbox_4)
    CheckBox colorCheckboxFour;
    @BindView(R.id.interactive_field_colorbox_5)
    CheckBox colorCheckboxFive;
    @BindView(R.id.interactive_field_colorbox_6)
    CheckBox colorCheckboxSix;

    @BindView(R.id.interactive_field_picture_container)
    FrameLayout fieldContainer;
    @BindView(R.id.interactive_field_color_layer)
    RelativeLayout colorLayer;
    @BindView(R.id.interactive_field_rotate_button)
    Button rotateFieldButton;
    @BindView(R.id.interactive_field_randomize)
    Button showHideColorLayer;


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

    @OnCheckedChanged(R.id.interactive_field_colorbox_1)
    public void changeCheckboxOne() {
        if (colorCheckboxOne.isChecked())
            colorCheckboxTwo.setChecked(false);
        else
            colorCheckboxTwo.setChecked(true);
    }

    @OnCheckedChanged(R.id.interactive_field_colorbox_2)
    public void changeCheckboxTwo() {
        if (colorCheckboxTwo.isChecked())
            colorCheckboxOne.setChecked(false);
        else
            colorCheckboxOne.setChecked(true);
    }

    @OnCheckedChanged(R.id.interactive_field_colorbox_3)
    public void changeCheckboxThree() {
        if (colorCheckboxThree.isChecked())
            colorCheckboxFour.setChecked(false);
        else
            colorCheckboxFour.setChecked(true);
    }

    @OnCheckedChanged(R.id.interactive_field_colorbox_4)
    public void changeCheckboxFour() {
        if (colorCheckboxFour.isChecked())
            colorCheckboxThree.setChecked(false);
        else
            colorCheckboxThree.setChecked(true);
    }

    @OnCheckedChanged(R.id.interactive_field_colorbox_5)
    public void changeCheckboxFive() {
        if (colorCheckboxFive.isChecked())
            colorCheckboxSix.setChecked(false);
        else
            colorCheckboxSix.setChecked(true);
    }

    @OnCheckedChanged(R.id.interactive_field_colorbox_6)
    public void changeCheckboxSix() {
        if (colorCheckboxSix.isChecked())
            colorCheckboxFive.setChecked(false);
        else
            colorCheckboxFive.setChecked(true);
    }
}
