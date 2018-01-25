package com.example.kyle.scout_862_template.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.example.kyle.scout_862_template.MatchScouting;
import com.example.kyle.scout_862_template.R;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kyle on 6/3/17.
 */

public class EndGameTab extends Fragment implements TabInterface {
    MatchDatabase matchDatabase = MatchScouting.matchDatabase;
    @BindView(R.id.cb_end_climb)
    Switch climbCheckbox;
    @BindView(R.id.cb_end_collectFuel)
    Switch collectFuelBox;
    @BindView(R.id.cb_end_stuck)
    Switch stuckOnFuelBox;
    @BindView(R.id.cb_end_def)
    Switch defenceCheckBox;
    @BindView(R.id.et_otherCpmments)
    EditText otherComments;

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
        climbCheckbox.setChecked(matchDatabase.getBool(20));
        collectFuelBox.setChecked(matchDatabase.getBool(23));
        stuckOnFuelBox.setChecked(matchDatabase.getBool(21));
        defenceCheckBox.setChecked(matchDatabase.getBool(22));
        otherComments.setText(matchDatabase.get(24));
    }

    @Override
    public void writeTab() {
        matchDatabase.add(climbCheckbox.isChecked(), 20);
        matchDatabase.add(collectFuelBox.isChecked(), 23);
        matchDatabase.add(stuckOnFuelBox.isChecked(), 21);
        matchDatabase.add(defenceCheckBox.isChecked(), 22);
        matchDatabase.add(otherComments.getText().toString(), 24);
    }
}
