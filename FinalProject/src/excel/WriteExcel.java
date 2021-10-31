package excel;

import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {

    public void result(ArrayList<String> firstCol, ArrayList<Double> secondCol, String str, String mode) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Results");
        Object[][] Data = new Object[firstCol.size()][2];
        for (int i = 0; i < firstCol.size(); i++) {
            Data[i][0] = firstCol.get(i);
            Data[i][1] = secondCol.get(i);
        }
        int rowCount = 0;
        for (Object[] data : Data) {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;
            for (Object field : data) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }
        if (mode.equalsIgnoreCase("AzmoonBeAzmoon")) {
            try (FileOutputStream outputStream = new FileOutputStream("Resultsof " + str + "'s exams.xlsx")) {
                workbook.write(outputStream);
                File f = new File("Resultsof " + str + "'s exams.xlsx");
                f.setReadOnly();
                Desktop.getDesktop().open(f);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            try (FileOutputStream outputStream = new FileOutputStream("Resultsof " + str + " .xlsx")) {
                workbook.write(outputStream);
                File f = new File("Resultsof " + str + " .xlsx");
                f.setReadOnly();
                Desktop.getDesktop().open(f);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
