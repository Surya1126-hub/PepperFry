//package utilities;
//
//
//
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.*;
//
//public class ExcelWriter {
//
//    /**
//     * Append a single row of data into an existing Excel file.
//     * If the file or sheet does not exist, it will be created.
//     *
//     * @param fileName  Path to the Excel file
//     * @param sheetName Name of the sheet
//     * @param rowData   Array of objects representing one row
//     */
//    public static void appendRow(String fileName, String sheetName, Object[] rowData) throws IOException {
//        File file = new File(fileName);
//        Workbook workbook;
//        Sheet sheet;
//
//        if (file.exists()) {
//            FileInputStream fis = new FileInputStream(file);
//            workbook = new XSSFWorkbook(fis);
//            fis.close();
//        } else {
//            workbook = new XSSFWorkbook();
//        }
//
//        sheet = workbook.getSheet(sheetName);
//        if (sheet == null) {
//            sheet = workbook.createSheet(sheetName);
//        }
//
//        int lastRowNum = sheet.getLastRowNum();
//        Row row = sheet.createRow(lastRowNum + 1);
//
//        for (int i = 0; i < rowData.length; i++) {
//            Cell cell = row.createCell(i);
//            cell.setCellValue(String.valueOf(rowData[i]));
//        }
//
//        FileOutputStream fos = new FileOutputStream(fileName);
//        workbook.write(fos);
//        fos.close();
//        workbook.close();
//    }
//
//    /**
//     * Write multiple rows at once (overwrites existing sheet).
//     *
//     * @param fileName  Path to the Excel file
//     * @param sheetName Name of the sheet
//     * @param data      2D array of objects
//     */
//    public static void writeData(String fileName, String sheetName, Object[][] data) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet(sheetName);
//
//        for (int i = 0; i < data.length; i++) {
//            Row row = sheet.createRow(i);
//            for (int j = 0; j < data[i].length; j++) {
//                Cell cell = row.createCell(j);
//                cell.setCellValue(String.valueOf(data[i][j]));
//            }
//        }
//
//        FileOutputStream fos = new FileOutputStream(fileName);
//        workbook.write(fos);
//        fos.close();
//        workbook.close();
//    }
//}
//


package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelWriter {

    // Append a single row
    public static void appendRow(String fileName, String sheetName, Object[] rowData) throws IOException {
        File file = new File(fileName);
        Workbook workbook;
        Sheet sheet;

        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            fis.close();
        } else {
            workbook = new XSSFWorkbook();
        }

        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }

        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRowNum + 1);

        for (int i = 0; i < rowData.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(String.valueOf(rowData[i]));
        }

        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    // Overwrite entire sheet with new data
    public static void writeData(String fileName, String sheetName, Object[][] data) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(data[i][j]));
            }
        }

        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    // Clear a sheet before writing new data
    public static void clearSheet(String fileName, String sheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet(sheetName);
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
