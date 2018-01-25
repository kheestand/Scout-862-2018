package com.example.kyle.scout_862_template.Scout862;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kyle on 6/2/17.
 */
public class PitDatabase {
    private static int MAX_MATCHES = 2;
    private static int MAX_COLUMNS = 3;
    //The app root is the top most directory the app will work with
    private String appRoot;
    private String databasePath;
    private String picturePath;
    private String[][] mainDatabase;
    private File excelFile = null;
    private int teamIndex = 0;
    private HSSFSheet scoutingExcelSheet;
    private FileInputStream inputStream;
    private HSSFWorkbook hssfWorkbook;

    public PitDatabase(File excelFile) {
        this.excelFile = excelFile;
        this.databasePath = appRoot + "/Database/";
        this.picturePath = appRoot + "/Pictures/";
        mainDatabase = new String[MAX_COLUMNS][MAX_MATCHES];

        //create file objects
        File databaseDir = new File(databasePath);
        File pictureDir = new File(picturePath);
        //check if directories exist. If they do not, create them
        if (!databaseDir.exists())
            databaseDir.mkdir();
        if (!pictureDir.exists())
            pictureDir.mkdirs();
    }

    public void add(String data, int xPosition) {
        mainDatabase[teamIndex][xPosition] = data;
    }

    public void add(int data, int xPosition) {
        mainDatabase[teamIndex][xPosition] = Integer.toString(data);
    }

    public void add(float data, int xPosition) {
        mainDatabase[teamIndex][xPosition] = Float.toString(data);
    }

    public String get(int xPosition) {
        return mainDatabase[teamIndex][xPosition];
    }

    public void importMatchSheet(String databaseName) throws IOException {
        excelFile = new File(databasePath + databaseName);
        inputStream = new FileInputStream(excelFile);
        hssfWorkbook = new HSSFWorkbook(inputStream);
        scoutingExcelSheet = hssfWorkbook.getSheetAt(0);
        for (int rowIndex = 0; rowIndex < MAX_MATCHES - 1; rowIndex++) {
            Row currentRow = scoutingExcelSheet.getRow(rowIndex);
            if (currentRow == null)
                scoutingExcelSheet.createRow(rowIndex);
            else {
                for (int columnIndex = 0; columnIndex < MAX_COLUMNS - 1; columnIndex++) {
                    Cell cell = currentRow.getCell(columnIndex);
                    if (cell == null)
                        mainDatabase[rowIndex][columnIndex] = "0";
                    else {
                        switch (cell.getCellTypeEnum()) {
                            case NUMERIC:
                                mainDatabase[rowIndex][columnIndex] = Double.toString(cell
                                        .getNumericCellValue());
                                break;
                            case STRING:
                                mainDatabase[rowIndex][columnIndex] = cell.getStringCellValue();
                                break;
                            case BOOLEAN:
                                mainDatabase[rowIndex][columnIndex] = Boolean.toString(cell
                                        .getBooleanCellValue());
                                break;
                            default:
                                mainDatabase[rowIndex][columnIndex] = cell.getStringCellValue();
                        }
                    }
                }
            }
        }
    }

    public void writeMatchToSheet(int matchNumber) throws IOException {
        inputStream = new FileInputStream(excelFile);
        hssfWorkbook = new HSSFWorkbook(inputStream);
        scoutingExcelSheet = hssfWorkbook.getSheetAt(0);

        Row matchAtRow = scoutingExcelSheet.getRow(matchNumber);
        for (int columnIndex = 0; columnIndex < MAX_COLUMNS - 1; columnIndex++) {
            Cell cell = matchAtRow.getCell(columnIndex);
            if (cell != null)
                cell.setCellValue(mainDatabase[matchNumber][columnIndex]);
        }
        FileOutputStream fileWriter = new FileOutputStream(excelFile);
        hssfWorkbook.write(fileWriter);
        fileWriter.close();
    }

    public void getTeamRobotPicture(int teamNumber) {

    }

    public void setRobotTeamPicture(int teamNumber) //Need arg for the picture file
    {

    }
}
