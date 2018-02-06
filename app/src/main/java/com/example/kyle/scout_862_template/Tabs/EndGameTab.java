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

    }
}
