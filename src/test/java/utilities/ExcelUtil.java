package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class ExcelUtil {

   


public static void tableData(String path, String sheetName, String[] arr, int colNo) {
	
        if (arr == null || arr.length == 0) return;
        if (colNo < 1) throw new IllegalArgumentException("colNo must be >= 1 (1 = column A)");

        Workbook wb = null;
        FileOutputStream fos = null;

        try {
            File file = new File(path);

            if (file.exists()) {
                wb = new XSSFWorkbook(new FileInputStream(file));
            } else {
                wb = new XSSFWorkbook();
            }

            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                sheet = wb.createSheet(sheetName);
            }

            // 1-based colNo to 0-based index
            int colIndex = colNo - 1;

            // Create styles (bold for heading)
            CellStyle boldStyle = wb.createCellStyle();
            Font boldFont = wb.createFont();
            boldFont.setBold(true);
            boldStyle.setFont(boldFont);

            // Write from row 1 (index 0) downward in the selected column
            int rowIndex = 0;

            // A) Heading (bold)
            Row row0 = getOrCreateRow(sheet, rowIndex++);
            Cell c0 = getOrCreateCell(row0, colIndex);
            c0.setCellValue(arr[0] != null ? arr[0] : "");
            c0.setCellStyle(boldStyle);

            // B) Items
            for (int i = 1; i < arr.length; i++) {
                Row r = getOrCreateRow(sheet, rowIndex++);
                Cell c = getOrCreateCell(r, colIndex);
                c.setCellValue(arr[i] != null ? arr[i].trim() : "");
            }

            // C) One blank row for visual spacing
            Row blank = getOrCreateRow(sheet, rowIndex++);
            getOrCreateCell(blank, colIndex).setBlank();

            // Auto-size the column for neatness
            sheet.autoSizeColumn(colIndex);

            // Save
            fos = new FileOutputStream(file);
            wb.write(fos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (wb != null) try { wb.close(); } catch (IOException ignored) {}
            if (fos != null) try { fos.close(); } catch (IOException ignored) {}
        }
    }

    private static Row getOrCreateRow(Sheet sheet, int rowIndex) {
        Row r = sheet.getRow(rowIndex);
        return (r != null) ? r : sheet.createRow(rowIndex);
    }

    private static Cell getOrCreateCell(Row row, int colIndex) {
        Cell c = row.getCell(colIndex);
        return (c != null) ? c : row.createCell(colIndex);
    }

}
