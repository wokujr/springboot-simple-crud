package com.employee.crud.controller;

import com.employee.crud.EmployeeExcelExporter;
import com.employee.crud.entity.Employee;
import com.employee.crud.service.EmployeeService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Could not find employee with id " + employee);
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        //in case user passed in id into JSON... set id to 0
        employee.setId(0);
        //save in database
        Employee saveEmployee = employeeService.save(employee);
        return saveEmployee;
    }

    //add Mapping for PUT request to Update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        Employee updateEmployee = employeeService.save(employee);
        return updateEmployee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Employee employee = employeeService.findById(employeeId);
        if(employee == null){
            throw new RuntimeException("Could not find employee with id " + employeeId);
        }
        employeeService.deleteById(employeeId);
        return "Deleted employee with id " + employeeId;
    }

    @GetMapping("/employees/export")
    public void exportEmployee(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employee.xlsx";

        response.setHeader(headerKey, headerValue);

        List<Employee> employeeList = employeeService.findAll();

        EmployeeExcelExporter exporter = new EmployeeExcelExporter(employeeList);
        exporter.export(response);

    }

}



























