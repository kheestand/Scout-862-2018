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
        if(matchDatabase.getInt(17) == 1)
            robotEndGameButtons.check(R.id.climbs_On_Field_Rung);
        else if(matchDatabase.getInt(18) == 1)
            robotEndGameButtons.check(R.id.climbs_Robot_Rung);
        else if(matchDatabase.getInt(19) == 1)
            robotEndGameButtons.check(R.id.climbs_On_Ramp);
        else if(matchDatabase.getInt(20) == 1)
            robotEndGameButtons.check(R.id.supports_One_Ramp);
        else if(matchDatabase.getInt(21) == 1)
            robotEndGameButtons.check(R.id.supports_Two_Ramp);
        else if(matchDatabase.getInt(22) == 1)
            robotEndGameButtons.check(R.id.supports_One_Rung);
        else if(matchDatabase.getInt(23) == 1)
            robotEndGameButtons.check(R.id.supports_Two_Rung);
        else if (matchDatabase.getInt(24) == 1)
            robotEndGameButtons.check(R.id.robot_Parks);
        else
            robotEndGameButtons.clearCheck();
    }

    @Override
    public void writeTab() {
        switch(robotEndGameButtons.getCheckedRadioButtonId()){
            case R.id.climbs_On_Field_Rung: matchDatabase.add(1,17);
                break;
            case R.id.climbs_Robot_Rung: matchDatabase.add(1,18);
                break;
            case R.id.climbs_On_Ramp: matchDatabase.add(1,19);
                break;
            case R.id.supports_One_Ramp: matchDatabase.add(1,20);
                break;
            case R.id.supports_Two_Ramp: matchDatabase.add(1,21);
                break;
            case R.id.supports_One_Rung: matchDatabase.add(1,22);
                break;
            case R.id.supports_Two_Rung: matchDatabase.add(1,23);
                break;
            case R.id.robot_Parks: matchDatabase.add(1, 24);
                break;
            default:
                matchDatabase.add(0,17);
                matchDatabase.add(0,18);
                matchDatabase.add(0,19);
                matchDatabase.add(0,20);
                matchDatabase.add(0,21);
                matchDatabase.add(0,22);
                matchDatabase.add(0,23);
                matchDatabase.add(0,24);
        }
    }

}
