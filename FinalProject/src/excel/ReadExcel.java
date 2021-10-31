package excel;

import People.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

    String name = new String(), family = new String(), Id = new String();
    int id, count = 0;
    Student s;
    ArrayList<Student> list = new ArrayList<Student>();

    public ArrayList readExcel(File file) {
        try {
            //File file = new File("students.xlsx");   
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            while (itr.hasNext()) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            if (count == 0) {
                                Id = cell.getStringCellValue();
                                count++;
                            } else if(count==1) {
                                name = cell.getStringCellValue();
                                count ++;
                            }else{
                                family=cell.getStringCellValue();
                                count++;
                            }
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            id = (int) cell.getNumericCellValue();
                            Id = Integer.toString(id);
                            count++;
                            break;
                        default:
                    }
                }
                s = new Student(name, family, Id, Id, Id);
                list.add(s);
                count=0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
