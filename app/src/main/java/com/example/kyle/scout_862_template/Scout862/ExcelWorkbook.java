package com.example.kyle.scout_862_template.Scout862;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by khees on 11/10/2017.
 * <p>
 * Goal: create a wrapper class for the poi library that is easy to use.
 * This class should contain code that will make common tasks easier to do
 */
public class ExcelWorkbook {

    //Global workbook field
    private Workbook wb;
    //Global current sheet field
    private Sheet currentSheet;
    //Global path field
    private String path;
    //Global workBookName field
    private String workbookName;
    //Global file extension field
    private String extension;

    public ExcelWorkbook(String path, String workbookName, boolean extension) {
        this.path = path;
        if (extension)
            this.workbookName = workbookName.substring(0, workbookName.indexOf("."));
        else
            this.workbookName = workbookName;
    }

    /**
     * Creates a new .xls excel workbook. Do not include the .xls in the file name
     */
    public void openHSSFWorkbook() throws IOException, InvalidFormatException {
        extension = ".xls";
        File file = new File(path + "/" + workbookName + extension);
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            wb = WorkbookFactory.create(inputStream);
            currentSheet = wb.getSheetAt(0);
        } else {
            wb = new HSSFWorkbook();
            wb.createSheet();
            FileOutputStream fileOut = new FileOutputStream(path + "/" + workbookName + extension);
            wb.write(fileOut);
            fileOut.close();
            currentSheet = wb.getSheetAt(0);
        }
    }

    /**
     * Creates a new .xlsx excel workbook. Do not include the .xlsx in the file name
     */
    public void openXSSFWorkbook() throws IOException, InvalidFormatException {
        extension = ".xlsx";
        File file = new File(path + "/" + workbookName + extension);
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            wb = WorkbookFactory.create(inputStream);
            currentSheet = wb.getSheetAt(0);
        } else {
            wb = new HSSFWorkbook();
            wb.createSheet();
            FileOutputStream fileOut = new FileOutputStream(path + "/" + workbookName + extension);
            wb.write(fileOut);
            fileOut.close();
            currentSheet = wb.getSheetAt(0);
        }
    }

    /**
     * Safely creates a new sheet in the workbook. If a workbook is not found,
     * it will throw a ExcelError exception. The new sheet will also become the current
     * sheet. If setAsCurrent is true, the new sheet will be the current sheet.
     *
     * @param sheetName
     * @param setAsCurrent
     */
    public void createNewSheet(String sheetName, boolean setAsCurrent) {
        String safeName = WorkbookUtil.createSafeSheetName(sheetName);
        Sheet newSheet = wb.createSheet(safeName);
        if (setAsCurrent)
            currentSheet = newSheet;
    }

    /**
     * Will write a 2D int array to the currentSheet object. Rows start at the 0th
     * index. User can chose to have a column offset by entering a number grater than 1.
     * Columns also start at the 0th index.
     * <p>
     * If you do not need an offset, columnOffset = 0
     *
     * @param array
     * @param row
     * @param columnOffset
     */
    public void arrayToRow(int[] array, int row, int columnOffset) {
        Row newRow = getRow(row);
        for (int index = columnOffset; index < array.length + columnOffset; index++) {
            Cell newCell = newRow.createCell(index, CellType.NUMERIC);
            newCell.setCellValue(array[index - columnOffset]);
        }
    }

    // Same as above but will do a string array instead of an int
    public void arrayToRow(String[] array, int row, int columnOffset) {
        Row newRow = getRow(row);
        for (int index = columnOffset; index < array.length + columnOffset; index++) {
            Cell newCell = newRow.createCell(index, CellType.STRING);
            newCell.setCellValue(array[index - columnOffset]);
        }
    }

    // Same as above but will do a boolean array instead of an int
    public void arrayToRow(boolean[] array, int row, int columnOffset) {
        Row newRow = getRow(row);
        for (int index = columnOffset; index < array.length + columnOffset; index++) {
            Cell newCell = newRow.createCell(index, CellType.BOOLEAN);
            newCell.setCellValue(array[index - columnOffset]);
        }
    }

    /**
     * Will write a 2D int array to the currentSheet objects. Rows start at the 0th
     * index. User can chose to have a column offset by entering a number grater than 1.
     * Columns also start at the 0th index.
     * <p>
     * If you do not need an offset, columnOffset = 0
     *
     * @param array
     * @param rowOffset
     * @param column
     */
    public void arrayToColumn(int[] array, int rowOffset, int column) {
        for (int index = rowOffset; index < array.length + rowOffset; index++) {
            Row rowAtIndex = getRow(index);
            Cell newCell = rowAtIndex.createCell(column, CellType.NUMERIC);
            newCell.setCellValue(array[index - rowOffset]);
        }
    }

    // Same as above but will do a string array instead of an int
    public void arrayToColumn(String[] array, int rowOffset, int column) {
        for (int index = rowOffset; index < array.length + rowOffset; index++) {
            Row rowAtIndex = getRow(index);
            Cell newCell = rowAtIndex.createCell(column, CellType.STRING);
            newCell.setCellValue(array[index - rowOffset]);
        }
    }

    // Same as above but will do a boolean array instead of an int
    public void arrayToColumn(boolean[] array, int rowOffset, int column) {
        for (int index = rowOffset; index < array.length + rowOffset; index++) {
            Row rowAtIndex = getRow(index);
            Cell newCell = rowAtIndex.createCell(column, CellType.BOOLEAN);
            newCell.setCellValue(array[index - rowOffset]);
        }
    }

    /**
     * Returns the row as list of Objects
     *
     * @param rowIndex
     * @return
     */
    public ArrayList<Object> rowToArrayList(int rowIndex) {
        Row row = getRow(rowIndex);
        ArrayList<Object> list = new ArrayList<Object>();
        for (Object object : row) {
            list.add(object);
        }
        return list;
    }

    /**
     * Creates a new cell and set the value to that cell to the value parameter
     *
     * @param value
     * @param rowIndex
     * @param cellIndex
     */
    public void setCellValueAtIndex(int value, int rowIndex, int cellIndex) {
        Row row = getRow(rowIndex);
        Cell cell = row.createCell(cellIndex, CellType.NUMERIC);
        cell.setCellValue(value);
    }

    // Same as above but for a double
    public void setCellValueAtIndex(double value, int rowIndex, int cellIndex) {
        Row row = getRow(rowIndex);
        Cell cell = row.createCell(cellIndex, CellType.NUMERIC);
        cell.setCellValue(value);

    }

    // Same as above but for a string
    public void setCellValueAtIndex(String value, int rowIndex, int cellIndex) {
        Row row = getRow(rowIndex);
        Cell cell = row.createCell(cellIndex, CellType.STRING);
        cell.setCellValue(value);
    }

    // Same as above but for a boolean
    public void setCellValueAtIndex(boolean value, int rowIndex, int cellIndex) {
        Row row = getRow(rowIndex);
        Cell cell = row.createCell(cellIndex, CellType.BOOLEAN);
        cell.setCellValue(value);
    }

    /**
     * will return a cell value if it is a String, int or bool. If the cell is null, it
     * will create the cell as a blank cell using CREATE_NULL_AS_BLANK cell policy.
     *
     * @param rowIndex
     * @param cellIndex
     * @return can return an int, boolean, string or will return a blank string if no
     * value is present
     */
    public Object getCellValue(int rowIndex, int cellIndex) {
        Row sheetRow = getRow(rowIndex);
        Cell cell = sheetRow.getCell(cellIndex, Row.MissingCellPolicy
                .CREATE_NULL_AS_BLANK);
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                return "";
        }
    }

    /**
     * Will change the global field currentSheet to a different sheet with the name
     *
     * @param sheetName
     */
    public void setCurrentSheet(String sheetName) {
        currentSheet = wb.getSheet(sheetName);
    }

    /**
     * returns the field currentSheet
     *
     * @return
     */
    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    /**
     * Will change the global field currentSheet to a different sheet with the index
     *
     * @param sheetIndex
     */
    public void setCurrentSheet(int sheetIndex) {
        currentSheet = wb.getSheetAt(sheetIndex);
    }

    /**
     * Ok but for real, do I have to explain this method?
     */
    public void saveWorkbook() throws IOException {
        FileOutputStream fos = new FileOutputStream(path + "/" + workbookName + extension);
        wb.write(fos);
        fos.close();
    }

    /**
     * Checks if the wb global object is null. If it is, it will throw an excel error
     *
     * @throws ExcelError
     */
    private void workbookExist() throws ExcelError {
        if (wb == null) {
            throw new ExcelError("Excel Workbook not found!");
        }
    }

    /**
     * this will return the row if it exists from the currentSheet. If it does not exist,
     * then it will return a blank row
     */
    private Row getRow(int rowIndex) {
        Row row = currentSheet.getRow(rowIndex);
        if (row == null)
            return currentSheet.createRow(rowIndex);
        else
            return row;
    }
}
