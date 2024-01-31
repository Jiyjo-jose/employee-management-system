package com.example.employeeManagement.controllerTest;


import com.example.employeeManagement.contract.request.EmployeeRequest;
import com.example.employeeManagement.contract.response.EmployeeResponse;
import com.example.employeeManagement.controller.EmployeeController;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ControllerTest {
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public  void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEmployee(){
        EmployeeRequest employeeRequest = new EmployeeRequest(null,null,null);
        EmployeeResponse employeeResponse = new EmployeeResponse(1L,null,null,null);
        when(employeeService.addEmployee(employeeRequest))
                .thenReturn(employeeResponse);
    }
    @Test
    void testGetById(){
        Employee sampleEmployee = new Employee(1L,null,null,null);
        when(employeeService.getById(anyLong())).thenReturn(sampleEmployee);
        Employee result = employeeController.getById(1L);
        assertEquals(sampleEmployee,result);
    }

    @Test
    void testGetByDepartment(){
        String testDepartment= "testDepartment";
        List<EmployeeResponse> testResponses = new ArrayList<>();
        when(employeeService.getByDepartment(anyString())).thenReturn(testResponses);
        List<EmployeeResponse> result = employeeController.getByDepartment(testDepartment);
        assertEquals(testResponses,result);
    }

}
