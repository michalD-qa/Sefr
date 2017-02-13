package utils;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by AKonopka on 23.01.2017.
 */
public class Excel {

    /**
     * Get spread sheet from given path and sheet index
     *
     * @param workbookNameWitPath path to file
     * @param sheetIndex          index of spreadsheet
     * @return spreadsheet data
     * @throws IOException
     */
    public static XSSFSheet getSpreadSheet(String workbookNameWitPath, int sheetIndex) throws IOException {
        FileInputStream fis = new FileInputStream(new File(workbookNameWitPath));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheetAt(sheetIndex - 1);
        fis.close();
        return spreadsheet;
    }

    /**
     * Gets row from given spreadsheet
     *
     * @param spreadSheet
     * @param rowNr
     * @return
     */
    public static Map<String, String> getRow(XSSFSheet spreadSheet, int rowNr) {
        Map<String, String> rowData = new HashMap<>();
        char alphabet = 'A';
        Row r = spreadSheet.getRow(rowNr - 1);
        int lastColumn = Math.max(r.getLastCellNum(), Constant.NR_OF_COLUMNS);

        for (int cn = 0; cn < lastColumn; cn++) {
            Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
            if (c != null) {
                rowData.put(String.valueOf(alphabet), c.getStringCellValue());
            }
            alphabet++;
        }
        return rowData;
    }

    /**
     * Gets column from given spreadsheet
     * @param spreadSheet
     * @param column
     * @return
     */
    public static Map<Integer, String> getColumn(XSSFSheet spreadSheet, char column) {
        Map<Integer, String> columnData = new HashMap<>();
        int asciiColumn = (int) column;
        int rowStart = Math.min(0, spreadSheet.getFirstRowNum());
        int rowEnd = Math.max(Constant.NR_OF_ROWS, spreadSheet.getLastRowNum());

        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row r = spreadSheet.getRow(rowNum);
            if (r == null) {
                // This whole row is empty
                // Handle it as needed
                continue;
            }
            Cell c = r.getCell(asciiColumn - 65, Row.RETURN_BLANK_AS_NULL);
            if (c != null) {
                columnData.put(rowNum + 1, c.getStringCellValue());
            }
        }
        return columnData;
    }


}
