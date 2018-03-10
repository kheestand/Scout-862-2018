package com.example.kyle.scout_862_template.AutoCards;

import com.example.kyle.scout_862_template.Scout862.MatchDatabase;

/**
 * Created by kyle on 3/9/18.
 */

public class AutoCard {

    private String cardString;
    private int scaleX;
    private int switchX;
    private MatchDatabase matchDatabase;

    public AutoCard(int cubeNumber, int scaleX, int switchX, MatchDatabase matchDatabase) {
        this.cardString = "Cube " + cubeNumber;
        this.scaleX = scaleX;
        this.switchX = switchX;
        this.matchDatabase = matchDatabase;
    }

    public String getCardString() {
        return cardString;
    }

    public int getScaleX() {
        return scaleX;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public int getSwitchX() {
        return switchX;
    }

    public void setSwitchX(int switchX) {
        this.switchX = switchX;
    }

    public MatchDatabase getMatchDatabase() {
        return matchDatabase;
    }

    public void setMatchDatabase(MatchDatabase matchDatabase) {
        this.matchDatabase = matchDatabase;
    }
}
