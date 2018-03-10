package com.example.kyle.scout_862_template.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    @BindView(R.id.endGameCommentBox)
    EditText commentsBox;
    @BindView(R.id.robot_Dead)
    CheckBox robotDead;
    @BindView(R.id.climb_on_rung)
    RadioButton hangs;
    @BindView(R.id.climb_while_lifting)
    RadioButton climbWhileLift;
    @BindView(R.id.rampbot_1)
    RadioButton rampbotOne;
    @BindView(R.id.rampbot_2)
    RadioButton rampbotTwo;
    @BindView(R.id.parks)
    RadioButton park;

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
        robotEndGameButtons.clearCheck();
        if(matchDatabase.getInt(2) == 1)
            robotDead.setChecked(true);
        else
            robotDead.setChecked(false);
        commentsBox.setText(String.valueOf(matchDatabase.get(23)));

        if(matchDatabase.getInt(18) == 1)
            hangs.setChecked(true);
        else if(matchDatabase.getInt(19) == 1)
            climbWhileLift.setChecked(true);
        else if(matchDatabase.getInt(20) == 1)
            rampbotOne.setChecked(true);
        else if(matchDatabase.getInt(21) == 1)
            rampbotTwo.setChecked(true);
        else if(matchDatabase.getInt(22) == 1)
            park.setChecked(true);
    }


    @Override
    public void writeTab() {
        matchDatabase.add(0,18);
        matchDatabase.add(0,19);
        matchDatabase.add(0,20);
        matchDatabase.add(0,21);
        matchDatabase.add(0,22);

        if(robotDead.isChecked())
            matchDatabase.add(1,2);
        else
            matchDatabase.add(0,2);
        matchDatabase.add(commentsBox.getText().toString(), 23);
        switch (robotEndGameButtons.getCheckedRadioButtonId()){
            case R.id.climb_on_rung: matchDatabase.add(1,18);
                break;
            case R.id.climb_while_lifting: matchDatabase.add(1,19);
                break;
            case R.id.rampbot_1: matchDatabase.add(1,20);
                break;
            case R.id.rampbot_2: matchDatabase.add(1,21);
                break;
            case R.id.parks: matchDatabase.add(1,22);
                break;
        }
    }

}
