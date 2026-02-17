package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    public static Object[][] getData(String fileName, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount - 1][colCount];
        DataFormatter formatter = new DataFormatter();

        for (int i = 0; i < rowCount - 1; i++) {
            XSSFRow row = sheet.getRow(i + 1);
            for (int j = 0; j < colCount; j++) {
                XSSFCell cell = row.getCell(j);
                data[i][j] = formatter.formatCellValue(cell);
            }
        }
        wb.close();
        fis.close();
        return data;
    }
}