package com.example.kyle.scout_862_template.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.kyle.scout_862_template.MatchScouting;
import com.example.kyle.scout_862_template.R;
import com.example.kyle.scout_862_template.Scout862.CycleData;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kyle on 6/3/17.
 */

public class TeleopTab extends Fragment implements TabInterface {
    MatchDatabase matchDatabase = MatchScouting.matchDatabase;
    @BindView(R.id.tele_Switch_Add)
    Button teleSwitchAdd;
    @BindView(R.id.tele_Switch_Subtract)
    Button teleSwitchSubtract;
    @BindView(R.id.tele_Switch_Value_Counter)
    TextView teleSwitchValueCounter;
    @BindView(R.id.tele_Scale_Add)
    Button teleScaleAdd;
    @BindView(R.id.tele_Scale_Subtract)
    Button teleScaleSubtract;
    @BindView(R.id.tele_Scale_Value_Counter)
    TextView teleScaleValueCounter;
    @BindView(R.id.tele_Opp_Switch_Add)
    Button teleOppSwitchAdd;
    @BindView(R.id.tele_Opp_Switch_Subtract)
    Button teleOppSwitchSubtract;
    @BindView(R.id.tele_Opp_Switch_Value_Counter)
    TextView teleOppSwitchValueCounter;
    @BindView(R.id.tele_Exchange_Add)
    Button teleExchangeAdd;
    @BindView(R.id.tele_Exchange_Subtract)
    Button teleExchangeSubtract;
    @BindView(R.id.tele_Exchange_Label)
    TextView teleExchangeLabel;
    @BindView(R.id.exchange_Value_Counter)
    TextView teleExchangeCounter;
    int teleSwitchScore = 0;
    int teleScaleScore = 0;
    int teleExchangeScore = 0;
    int teleOppSwitchScore = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teleop_tab, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void readTab() {
        teleSwitchValueCounter.setText(matchDatabase.get(13));
        teleOppSwitchValueCounter.setText(matchDatabase.get(14));
        teleScaleValueCounter.setText(matchDatabase.get(15));
        teleExchangeCounter.setText(matchDatabase.get(16));
    }

    @Override
    public void writeTab() {
        matchDatabase.add(teleSwitchScore, 13);
        matchDatabase.add(teleOppSwitchScore, 14);
        matchDatabase.add(teleScaleScore, 15);
        matchDatabase.add(teleExchangeScore, 16);
    }

    @OnClick(R.id.tele_Switch_Add)
    public void addToTeleSwitch() {
        if (teleSwitchScore < 60) {
            teleSwitchScore = teleSwitchScore + 1;
            teleSwitchValueCounter.setText(String.valueOf(teleSwitchScore));

        }
    }

    @OnClick(R.id.tele_Switch_Subtract)
    public void subtractFromTeleSwitch() {
        if (teleSwitchScore > 0) {
            teleSwitchScore = teleSwitchScore - 1;
            teleSwitchValueCounter.setText(String.valueOf(teleSwitchScore));

        }
    }

    @OnClick(R.id.tele_Scale_Add)
    public void addToTeleScale() {
        if (teleScaleScore < 60) {
            teleScaleScore = teleScaleScore + 1;
            teleScaleValueCounter.setText(String.valueOf(teleScaleScore));

        }
    }

    @OnClick(R.id.tele_Scale_Subtract)
    public void subtractFromTeleScale() {
        if (teleScaleScore > 0) {
            teleScaleScore = teleScaleScore - 1;
            teleScaleValueCounter.setText(String.valueOf(teleScaleScore));

        }
    }

    @OnClick(R.id.tele_Opp_Switch_Add)
    public void addFromTeleOppSwitch() {
        if (teleOppSwitchScore < 60) {
            teleOppSwitchScore = teleOppSwitchScore + 1;
            teleOppSwitchValueCounter.setText(String.valueOf(teleOppSwitchScore));

        }
    }
    
    @OnClick(R.id.tele_Opp_Switch_Subtract)
    public void subtractFromTeleOppSwitch() {
        if (teleOppSwitchScore > 0) {
            teleOppSwitchScore = teleOppSwitchScore - 1;
            teleOppSwitchValueCounter.setText(String.valueOf(teleOppSwitchScore));

        }
    }

    @OnClick(R.id.tele_Exchange_Add)
    public void addToTeleExchange() {
        if (teleExchangeScore < 60) {
            teleExchangeScore = teleExchangeScore + 1;
            teleExchangeCounter.setText(String.valueOf(teleExchangeScore));

        }
    }

    @OnClick(R.id.tele_Exchange_Subtract)
    public void subtractFromTeleExchange() {
        if (teleExchangeScore > 0) {
            teleExchangeScore = teleExchangeScore - 1;
            teleExchangeCounter.setText(String.valueOf(teleExchangeScore));

        }
    }

}
