package com.example.kyle.scout_862_template.Scout862;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;

/**
 * Created by kyle on 6/2/17.
 */
public class MatchDatabase extends ExcelWorkbook {
    private static int MAX_MATCHES = 100;
    private int matchNumber = 1;

    public MatchDatabase(File excelFile) {
        super(excelFile.getParent(), excelFile.getName(), true);
    }

    public void add(String data, int xPosition) {
        super.setCellValueAtIndex(data, matchNumber, xPosition);
    }

    public void add(int data, int xPosition) {
        super.setCellValueAtIndex(data, matchNumber, xPosition);
    }

    public void add(float data, int xPosition) {
        super.setCellValueAtIndex(data, matchNumber, xPosition);
    }

    public void add(Boolean data, int xPosition) {
        super.setCellValueAtIndex(data, matchNumber, xPosition);
    }

    /**
     * NOTE: If you are using this to return an int, it will return 0.0 instead of 0.
     * To return the number with no decimal place, use the getInt method due to all
     * numeric cells in the POI library being returned with decimals
     *
     * @param xPosition
     * @return
     */
    public String get(int xPosition) {
        return super.getCellValue(matchNumber, xPosition).toString();
    }

    public int getInt(int xPosition) {
        String value = super.getCellValue(matchNumber, xPosition).toString();
        if (value.equals(""))
            return 0;
        else {
            // You need to parse the int from a double
            Double valueAsDouble = Double.valueOf(value);
            return valueAsDouble.intValue();
        }
    }

    public Double getDouble(int xPosition) {
        String value = super.getCellValue(matchNumber, xPosition).toString();
        if (value.equals(""))
            return 0.0;
        return Double.parseDouble(value);
    }

    public boolean getBool(int xPosition) {
        String value = super.getCellValue(matchNumber, xPosition).toString();
        if (value.equals(""))
            return false;
        return Boolean.parseBoolean(value);
    }

    public void importMatchSheet(boolean xls) throws IOException, InvalidFormatException {
        if (xls)
            super.openHSSFWorkbook();
        else
            super.openXSSFWorkbook();
    }

    public void writeMatchToSheet() throws IOException {
        super.saveWorkbook();
    }

    public int getMaxMatches() {
        return MAX_MATCHES;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getAllianceColor() {
        return super.getCellValue(0, 0).toString();
    }
}
