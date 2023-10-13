package com.employee.crud;

import com.employee.crud.entity.Employee;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class EmployeeExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Employee> EmployeeLists;

    public EmployeeExcelExporter(List<Employee> employeeLists) {
        this.EmployeeLists = employeeLists;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Employee");

    }

    private void writeHeaderRow(){
        Row row = sheet.createRow(0); //First row is 0
        Cell cell = row.createCell(0); //first column is 0
        cell.setCellValue("Employee ID");
    }
    private void writeDataRow(){

    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRow();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
