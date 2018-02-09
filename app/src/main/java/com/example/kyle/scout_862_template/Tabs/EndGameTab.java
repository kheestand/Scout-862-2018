package com.example.kyle.scout_862_template.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.kyle.scout_862_template.MatchScouting;
import com.example.kyle.scout_862_template.R;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by kyle & ethan on 6/3/17.
 */

public class EndGameTab extends Fragment implements TabInterface {
    MatchDatabase matchDatabase = MatchScouting.matchDatabase;
    @BindView(R.id.robotEndGameButtons)
    RadioGroup robotEndGameButtons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_game_tab, container, false);
        ButterKnife.bind(this, view);
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

    }

    @Override
    public void writeTab() {
        switch(robotEndGameButtons.getCheckedRadioButtonId()){
            case R.id.supports_Two_Ramp://C#
                break;
            case R.id.supports_Two_Rung://C#
                break;
            case R.id.supports_One_Ramp://C#
                break;
            case R.id.supports_One_Rung://C#
                break;
            case R.id.climbs_On_Ramp://C#
                break;
            case R.id.climbs_On_Field_Rung://C#
                break;
            case R.id.climbs_Robot_Rung://C#
                break;
            case R.id.robot_Parks://C#
                break;
        }
    }

}
