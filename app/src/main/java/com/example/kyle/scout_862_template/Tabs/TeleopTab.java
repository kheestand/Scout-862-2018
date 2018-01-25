package com.example.kyle.scout_862_template.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.kyle.scout_862_template.MatchScouting;
import com.example.kyle.scout_862_template.R;
import com.example.kyle.scout_862_template.Scout862.CycleData;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kyle on 6/3/17.
 */

public class TeleopTab extends Fragment implements TabInterface {
    MatchDatabase matchDatabase = MatchScouting.matchDatabase;
    CycleData highGoalData = new CycleData(matchDatabase.getMaxMatches());
    CycleData lowGoalData = new CycleData(matchDatabase.getMaxMatches());
    CycleData gearData = new CycleData(matchDatabase.getMaxMatches());
    @BindView(R.id.nextCycleArrow)
    AwesomeTextView nextCycleArrow;
    @BindView(R.id.previousCycleArrow)
    AwesomeTextView previousCycleArrow;
    @BindView(R.id.cycleNumber)
    AwesomeTextView cycleNumberTextView;
    @BindView(R.id.teleopLowGoalScrollbar)
    SeekBar teleopLowGoalScrollBar;
    @BindView(R.id.teleopLowGoalSwitch)
    Switch teleopLowGoalSwitch;
    @BindView(R.id.teleopLowGoalValueLabel)
    BootstrapLabel teleopLowGoalLabel;
    @BindView(R.id.teleopHighGoalScrollbar)
    SeekBar teleopHighGoalScrollBar;
    @BindView(R.id.teleopHighGoalSwitch)
    Switch teleopHighGoalSwitch;
    @BindView(R.id.teleopHighGoalValueLabel)
    BootstrapLabel teleopHighGoalLabel;
    @BindView(R.id.teleopGearsDeliveredSwitch)
    Switch teleopGearsDeliveredSwitch;
    private boolean changeCycles = false;
    private int currentCycleCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teleop_tab, container, false);
        ButterKnife.bind(this, view);
        teleopHighGoalScrollBar.setMax(120);
        teleopLowGoalScrollBar.setMax(120);
        gearData.setMatchNumber(matchDatabase.getMatchNumber());
        lowGoalData.setMatchNumber(matchDatabase.getMatchNumber());
        highGoalData.setMatchNumber(matchDatabase.getMatchNumber());

        teleopHighGoalScrollBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                teleopHighGoalLabel.setMarkdownText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        teleopLowGoalScrollBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                teleopLowGoalLabel.setMarkdownText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    public void onPause() {
        super.onPause();
        writeTab();
    }

    public void onStart() {
        super.onStart();
        readTab();
    }

    @Override
    public void readTab() {
        int currentCycleCount = 1;
        cycleNumberTextView.setMarkdownText(String.valueOf(currentCycleCount));
        readCycle(currentCycleCount);

        if (gearData.getData(currentCycleCount) == 1)
            teleopGearsDeliveredSwitch.setChecked(true);
        else
            teleopGearsDeliveredSwitch.setChecked(false);

        teleopHighGoalSwitch.setChecked(matchDatabase.getBool(14));
        teleopLowGoalSwitch.setChecked(matchDatabase.getBool(17));

    }

    @Override
    public void writeTab() {
        highGoalData.addData(teleopHighGoalScrollBar.getProgress(), currentCycleCount);
        lowGoalData.addData(teleopLowGoalScrollBar.getProgress(), currentCycleCount);
        if (teleopGearsDeliveredSwitch.isChecked())
            gearData.addData(1, currentCycleCount);
        else
            gearData.addData(0, currentCycleCount);
        if (changeCycles) {
            writeCycle(currentCycleCount);
            matchDatabase.add(gearData.addAll(), 12);
            matchDatabase.add(lowGoalData.getAverageAsString(), 18);
            matchDatabase.add(highGoalData.getAverageAsString(), 15);
        }
        changeCycles = false;
    }

    @OnClick(R.id.nextCycleArrow)
    public void goToNextCycle() {
        if (currentCycleCount + 1 != CycleData.MAX_CYCLES) {
            writeCycle(currentCycleCount);
            readCycle(currentCycleCount + 1);
        }
    }

    @OnClick(R.id.previousCycleArrow)
    public void goToPreviousCycle() {
        if (currentCycleCount - 1 != 0) {
            writeCycle(currentCycleCount);
            readCycle(currentCycleCount - 1);
        }
    }

    public void readCycle(int newCycleNumber) {
        highGoalData.setMatchNumber(matchDatabase.getMatchNumber());
        lowGoalData.setMatchNumber(matchDatabase.getMatchNumber());
        gearData.setMatchNumber(matchDatabase.getMatchNumber());
        currentCycleCount = newCycleNumber;
        teleopHighGoalLabel.setMarkdownText(String.valueOf(highGoalData.getData(newCycleNumber)));
        teleopHighGoalScrollBar.setProgress(highGoalData.getData(newCycleNumber));
        teleopLowGoalLabel.setMarkdownText(String.valueOf(lowGoalData.getData(newCycleNumber)));
        teleopLowGoalScrollBar.setProgress(lowGoalData.getData(newCycleNumber));
        if (gearData.getData(newCycleNumber) == 1)
            teleopGearsDeliveredSwitch.setChecked(true);
        else
            teleopGearsDeliveredSwitch.setChecked(false);
        cycleNumberTextView.setMarkdownText(String.valueOf(newCycleNumber));
    }

    public void writeCycle(int newCycleNumber) {
        currentCycleCount = newCycleNumber;
        highGoalData.addData(teleopHighGoalScrollBar.getProgress(), currentCycleCount);
        highGoalData.increaseMaxCycles(newCycleNumber);
        lowGoalData.addData(teleopLowGoalScrollBar.getProgress(), currentCycleCount);
        lowGoalData.increaseMaxCycles(newCycleNumber);
        if (teleopGearsDeliveredSwitch.isChecked())
            gearData.addData(1, currentCycleCount);
        else
            gearData.addData(0, currentCycleCount);
        gearData.increaseMaxCycles(newCycleNumber);
        cycleNumberTextView.setMarkdownText(String.valueOf(newCycleNumber));
    }
}
