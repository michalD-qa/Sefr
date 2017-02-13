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


    private static final String BFC = "C:/Users/akonopka/Desktop/BFC.xlsx";

//    public static void main(String[] args) throws IOException {
//
//        XSSFSheet dmReporting = getSpreadSheet(BFC, Constant.DM_REPORTING_SHEET);
//        getChaptersForReport(dmReporting, "L010.007");
//    }

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
     * Gets corresponding chapters for specified report name from excel documentation file
     *
     * @param spreadSheet spreadsheet for reading chapters
     * @param reportName  name of report to read chapters
     * @return list of chapters corresponding to given report
     * @throws IOException
     */
    public static List<String> getChaptersForReport(XSSFSheet spreadSheet, String reportName) throws IOException {

        List<String> chaptersToCheck = new ArrayList<>();

        //get all reports name
        Map<String, String> reports = getRow(spreadSheet, Constant.REPORTS_ROW);

        //get all chapters name
        Map<Integer, String> chapters = getColumn(spreadSheet, Constant.CHAPTERS_COLUMN);

        String columnWithReport = null;
        //get column of report by Name
        for (String o : reports.keySet()) {
            if (reports.get(o).equals(reportName)) {
                columnWithReport = o;
            }
        }

        //get chapters for corresponding report
        Map<Integer, String> chapterForReport = null;
        if (columnWithReport != null) {
            chapterForReport = getColumn(spreadSheet, columnWithReport.charAt(0));

            for (int i : chapterForReport.keySet()) {
                if (chapterForReport.get(i).equals("Y")) chaptersToCheck.add(chapters.get(i));
            }
        }
        return chaptersToCheck;
    }

    private static Map<String, String> getRow(XSSFSheet spreadSheet, int rowNr) {
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

    private static Map<Integer, String> getColumn(XSSFSheet spreadSheet, char column) {
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
