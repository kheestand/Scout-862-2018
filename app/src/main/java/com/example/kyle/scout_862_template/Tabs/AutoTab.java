package com.example.kyle.scout_862_template.Tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.kyle.scout_862_template.AutoCards.AutoCard;
import com.example.kyle.scout_862_template.AutoCards.AutoCardAdapter;
import com.example.kyle.scout_862_template.MatchScouting;
import com.example.kyle.scout_862_template.R;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;
import com.tooltip.Tooltip;

import org.apache.poi.hssf.util.HSSFColor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnLongClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by kyle on 6/3/17.
 */

public class AutoTab extends Fragment implements TabInterface {
    MatchDatabase matchDatabase = MatchScouting.matchDatabase;
    @BindView(R.id.auto_cards)
    RecyclerView autoCards;
    @BindView(R.id.interactive_field_rel)
    RelativeLayout fieldLayout;
    @BindView(R.id.scouterName)
    BootstrapEditText scouterNameBox;
    @BindView(R.id.red_left_button)
    BootstrapButton redLeftButton;
    @BindView(R.id.red_right_button)
    BootstrapButton redRightButton;
    @BindView(R.id.red_center_button)
    BootstrapButton redCenterButton;
    @BindView(R.id.blue_left_button)
    BootstrapButton blueLeftButton;
    @BindView(R.id.blue_right_button)
    BootstrapButton blueRightButton;
    @BindView(R.id.blue_center_button)
    BootstrapButton blueCenterButton;
    @BindView(R.id.auto_cross_line)
    CheckBox crossAutoLine;
    @BindView(R.id.blue_switch_left)
    CheckBox blueSwitchLeft;
    @BindView(R.id.blue_switch_right)
    CheckBox blueSwitchRight;
    @BindView(R.id.red_switch_left)
    CheckBox redSwitchLeft;
    @BindView(R.id.red_switch_right)
    CheckBox redSwitchRight;
    @BindView(R.id.scale_1)
    CheckBox scaleOne;
    @BindView(R.id.scale_2)
    CheckBox scaleTwo;
    int startingPosition;
    int[][] closeFarMatrix = {
            {6,4,5,7},
            {4,6,7,5},
            {6,6,7,7},
            {6,4,7,5},
            {4,6,5,7},
            {6,6,7,7}
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_tab, container, false);
        ButterKnife.bind(this, view);
        ArrayList<AutoCard> cardViews = new ArrayList<>();
        autoCards.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        autoCards.setLayoutManager(linearLayoutManager);
        AutoCard cubeTwo = new AutoCard(2, 9, 8, matchDatabase);
        AutoCard cubeThree = new AutoCard(3, 11, 10, matchDatabase);
        AutoCard cubeFour = new AutoCard(4, 13, 12, matchDatabase);
        cardViews.add(cubeTwo);
        cardViews.add(cubeThree);
        cardViews.add(cubeFour);
        AutoCardAdapter autoCardAdapter = new AutoCardAdapter(cardViews);
        autoCards.setAdapter(autoCardAdapter);
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
        scouterNameBox.setText(String.valueOf(matchDatabase.get(1)));
        autoCards.getAdapter().notifyItemChanged(0);
        autoCards.getAdapter().notifyItemChanged(1);
        autoCards.getAdapter().notifyItemChanged(2);
        if(matchDatabase.getInt(3) == 1)
            crossAutoLine.setChecked(true);
        else
            crossAutoLine.setChecked(false);
        startingPosition = matchDatabase.getInt(24);
        setStartingPositionToolTip();
        setBoxesChecked();
    }

    @Override
    public void writeTab() {
        matchDatabase.add(scouterNameBox.getText().toString(), 1);
        if(crossAutoLine.isChecked())
            matchDatabase.add(1,3);
        else
            matchDatabase.add(0,3);
        matchDatabase.add(startingPosition,24);
    }

    @OnClick(R.id.auto_rotate_field)
    public void rotateField(){
        float rotation;
        if(fieldLayout.getRotation() == 180f) {
            rotation = 0f;
        }
        else{
            rotation = 180f;
        }

        fieldLayout.setRotation(rotation);
        blueCenterButton.setRotation(rotation);
        blueLeftButton.setRotation(rotation);
        blueRightButton.setRotation(rotation);
        redCenterButton.setRotation(rotation);
        redLeftButton.setRotation(rotation);
        redRightButton.setRotation(rotation);
    }

    @OnClick(R.id.blue_right_button)
    public void blueButtonRight() {
        if (matchDatabase.getAllianceColor().toLowerCase().contains("red")) {
            Toasty.warning(this.getContext(), "Red can not start from Blue position!").show();
        } else {
            startingPosition = 2;
            Toasty.info(this.getContext(), "Starting position set!").show();
        }
    }

    @OnClick(R.id.blue_left_button)
    public void blueButtonLeft() {
        if (matchDatabase.getAllianceColor().toLowerCase().contains("red")) {
            Toasty.warning(this.getContext(), "Red can not start from Blue position!").show();
        } else {
            startingPosition = 1;
            Toasty.info(this.getContext(), "Starting position set!").show();
        }
    }

    @OnClick(R.id.blue_center_button)
    public void blueButtonCenter() {
        if (matchDatabase.getAllianceColor().toLowerCase().contains("red")) {
            Toasty.warning(this.getContext(), "Red can not start from Blue position!").show();
        } else {
            startingPosition = 3;
            Toasty.info(this.getContext(), "Starting position set!").show();
        }
    }

    @OnClick(R.id.red_right_button)
    public void redButtonRight() {
        if (matchDatabase.getAllianceColor().toLowerCase().contains("blue")) {
            Toasty.warning(this.getContext(), "Blue can not start from Red position!").show();
        } else {
            startingPosition = 5;
            Toasty.info(this.getContext(), "Starting position set!").show();
        }
    }

    @OnClick(R.id.red_left_button)
    public void redButtonLeft() {
        if (matchDatabase.getAllianceColor().toLowerCase().contains("blue")) {
            Toasty.warning(this.getContext(), "Blue can not start from Red position!").show();
        } else {
            startingPosition = 4;
            Toasty.info(this.getContext(), "Starting position set!").show();
        }
    }

    @OnClick(R.id.red_center_button)
    public void redButtonCenter() {
        if (matchDatabase.getAllianceColor().toLowerCase().contains("blue")) {
            Toasty.warning(this.getContext(), "Blue can not start from Red position!").show();
        } else {
            startingPosition = 6;
            Toasty.info(this.getContext(), "Starting position set!").show();
        }
    }

    public void setStartingPositionToolTip(){
        Tooltip tooltip;
        System.out.println("Start pos: " + matchDatabase.getInt(24));
        String text = "Starting position";
        switch (startingPosition){
            case 1: tooltip = new Tooltip.Builder(blueLeftButton)
                    .setText(text)
                    .setCancelable(true)
                    .setBackgroundColor(Color.BLUE)
                    .show();
                break;
            case 2: tooltip = new Tooltip.Builder(blueRightButton)
                    .setText(text)
                    .setCancelable(true)
                    .setBackgroundColor(Color.BLUE)
                    .show();
                break;
            case 3: tooltip = new Tooltip.Builder(blueCenterButton)
                    .setText(text)
                    .setCancelable(true)
                    .setBackgroundColor(Color.BLUE)
                    .show();
                break;
            case 4: tooltip = new Tooltip.Builder(redLeftButton)
                    .setText(text)
                    .setCancelable(true)
                    .setBackgroundColor(Color.RED)
                    .show();
                break;
            case 5:tooltip = new Tooltip.Builder(redRightButton)
                    .setText(text)
                    .setCancelable(true)
                    .setBackgroundColor(Color.RED)
                    .show();
                break;
            case 6:tooltip = new Tooltip.Builder(redCenterButton)
                    .setText(text)
                    .setCancelable(true)
                    .setBackgroundColor(Color.RED)
                    .show();
                break;

            default:tooltip = new Tooltip.Builder(fieldLayout)
                    .setText("Set a position for the " + matchDatabase.getAllianceColor() + " robot! (Click me to dismiss!)")
                    .setDismissOnClick(true)
                    .show();
        }
    }

    @OnCheckedChanged(R.id.blue_switch_left)
    public void setBlueSwitchLeft() {
        if(startingPosition != 0 && blueSwitchLeft.isChecked()) {
            int colValue = closeFarMatrix[startingPosition - 1][0];
            matchDatabase.add(1, colValue);
        } else{
            matchDatabase.add(0, 4);
            matchDatabase.add(0, 6);
        }
    }

    @OnCheckedChanged(R.id.blue_switch_right)
    public void setBlueSwitchRight() {
        if (startingPosition != 0 && blueSwitchRight.isChecked()) {
            int colValue = closeFarMatrix[startingPosition - 1][1];
            matchDatabase.add(1, colValue);
        }
        else {
            matchDatabase.add(0, 4);
            matchDatabase.add(0, 6);
        }
    }

    @OnCheckedChanged(R.id.red_switch_left)
    public void setRedSwitchLeft() {
        if (startingPosition != 0 && redSwitchLeft.isChecked()) {
            int colValue = closeFarMatrix[startingPosition - 1][0];
            matchDatabase.add(1, colValue);
        }
        else{
            matchDatabase.add(0, 4);
            matchDatabase.add(0, 6);
        }
    }

    @OnCheckedChanged(R.id.red_switch_right)
    public void setRedSwitchRight() {
        if (startingPosition != 0 && redSwitchRight.isChecked()) {
            int colValue = closeFarMatrix[startingPosition - 1][1];
            matchDatabase.add(1, colValue);
        }
        else {
            matchDatabase.add(0, 4);
            matchDatabase.add(0, 6);
        }
    }

    @OnCheckedChanged(R.id.scale_1)
    public void setScaleOne() {
        if (startingPosition != 0 && scaleOne.isChecked()) {
            int colValue = closeFarMatrix[startingPosition - 1][2];
            matchDatabase.add(1, colValue);
        }
        else{
            matchDatabase.add(0, 5);
            matchDatabase.add(0, 7);
        }
    }

    @OnCheckedChanged(R.id.scale_2)
    public void setScaleTwo() {
        if (startingPosition != 0 && scaleTwo.isChecked()) {
            int colValue = closeFarMatrix[startingPosition - 1][3];
            matchDatabase.add(1, colValue);
        }
        else {
            matchDatabase.add(0, 5);
            matchDatabase.add(0, 7);
        }
    }

    public void setBoxesChecked() {
        blueSwitchRight.setChecked(false);
        blueSwitchLeft.setChecked(false);
        blueSwitchRight.setChecked(false);
        redSwitchRight.setChecked(false);
        redSwitchLeft.setChecked(false);
        redSwitchRight.setChecked(false);
        scaleOne.setChecked(false);
        scaleTwo.setChecked(false);
        if (startingPosition != 0) {
            int[] row = closeFarMatrix[startingPosition - 1];
            int[] databaseRow = {matchDatabase.getInt(row[0]), matchDatabase.getInt(row[1]), matchDatabase.getInt(row[2]), matchDatabase.getInt(row[3])};
            for(int num: databaseRow){
                System.out.print(num + " ");
            }
            System.out.print("\n");
            int columnWithValue = getColumnWithValue(databaseRow, 1);
            if (startingPosition <= 3) {
                if (columnWithValue == 0)
                    blueSwitchLeft.setChecked(true);
                else if (columnWithValue == 1)
                    blueSwitchRight.setChecked(true);
                else if (columnWithValue == 2)
                    scaleOne.setChecked(true);
                else if (columnWithValue == 3)
                    scaleTwo.setChecked(true);
            } else {
                if (columnWithValue == 0)
                    redSwitchLeft.setChecked(true);
                else if (columnWithValue == 1)
                    redSwitchRight.setChecked(true);
                else if (columnWithValue == 2)
                    scaleOne.setChecked(true);
                else if (columnWithValue == 3)
                    scaleTwo.setChecked(true);
            }
        }
    }

    public int getColumnWithValue(int[] array, int value){
        int counter = 0;
        for(int number : array){
            if(number == value)
                return counter;
            else
                counter++;
        }
        return 0;
    }
}
