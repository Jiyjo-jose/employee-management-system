package com.example.employeeManagement.controller;

import com.example.employeeManagement.contract.request.EmployeeRequest;
import com.example.employeeManagement.contract.response.EmployeeResponse;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class EmployeeController {
    private  final EmployeeService employeeService;


    @PostMapping("/addEmployee")
    public EmployeeResponse addEmployee(@Valid @RequestBody EmployeeRequest request){
        EmployeeResponse employee= employeeService.addEmployee(request);
        return employee;
    }

    @GetMapping("/getById/{id}")
    public Employee getById(@PathVariable Long id){
        return employeeService.getById(id);
    }
    @GetMapping("/getByDepartment/{department}")
    public List<EmployeeResponse> getByDepartment(@PathVariable String department){
        return  employeeService.getByDepartment(department);
    }
}
