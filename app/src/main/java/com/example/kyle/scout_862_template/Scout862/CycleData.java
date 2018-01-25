package com.example.kyle.scout_862_template.Scout862;

/**
 * Created by kyle on 8/24/17.
 */

public class CycleData {

    public static final int MAX_CYCLES = 50;
    public int[][] cycleArray;
    private int[] maxCyclesForMatch;
    private int matchNumber;

    public CycleData(int numberOfMatches) {
        cycleArray = new int[MAX_CYCLES][numberOfMatches];
        maxCyclesForMatch = new int[numberOfMatches];
    }

    public void increaseMaxCycles(int newNumber) {
        if (newNumber > maxCyclesForMatch[matchNumber]) {
            maxCyclesForMatch[matchNumber] = maxCyclesForMatch[matchNumber] + 1;
        }
    }

    public void addData(int dataAdd, int cycleNumber) {
        cycleArray[cycleNumber][matchNumber] = dataAdd;
    }

    public int getData(int cycleNumber) {
        return cycleArray[cycleNumber][matchNumber];
    }

    public int addAll() {
        int sum = 0;
        for (int index = 1; index < MAX_CYCLES - 1; index++) {
            sum = sum + cycleArray[index][matchNumber];
        }
        return sum;
    }

    public String getAverageAsString() {
        int total = 0;
        for (int index = 1; index < MAX_CYCLES - 1; index++) {
            total = total + cycleArray[index][matchNumber];
        }
        double average = total / maxCyclesForMatch[matchNumber];
        return String.valueOf(average);
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int newMatchNumber) {
        matchNumber = newMatchNumber;
    }
}
