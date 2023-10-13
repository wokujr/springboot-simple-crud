package com.employee.crud;

import com.employee.crud.entity.Employee;
import com.employee.crud.repositoy.EmployeeRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class EmployeeExcelExporter {

    private EmployeeRepository employeeRepository;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Employee> employeeList;

    public EmployeeExcelExporter( List<Employee> employeeLists) {
        this.employeeList = employeeLists;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Employee");

    }

    //ROW Headers
    private void writeHeaderRow(){

        //First row is 0
        Row row = sheet.createRow(0);

        //First column is 0 and Write the First Column of 0
        Cell cell = row.createCell(0);
        cell.setCellValue("Employee ID");

        //next Row after Employee ID
        cell = row.createCell(1);
        cell.setCellValue("Employee firstName");

        cell = row.createCell(2);
        cell.setCellValue("Employee lastName");

        cell = row.createCell(3);
        cell.setCellValue("Employee Email");

    }

    // Write Data To Each Header
    private void writeDataRow(){
        //Because we can have many data rows
        int rowCount = 1;

        for (Employee employee : employeeList){

            Row row = sheet.createRow(rowCount);
            Cell cell = row.createCell(0); //For Employee id
            cell.setCellValue(employee.getId());

            cell = row.createCell(1);
            cell.setCellValue(employee.getFirstName());

            cell = row.createCell(2);
            cell.setCellValue(employee.getLastName());

            cell = row.createCell(3);
            cell.setCellValue(employee.getEmail());

            //increment the row count to get all data
            rowCount++;
        }
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
