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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
void testAddEmployee() {
    EmployeeRequest request = new EmployeeRequest("aa","aa","j@email.com");

    EmployeeResponse mockedResponse = new EmployeeResponse(1L,"aa","aa","j@email.com");


    when(employeeService.addEmployee(request)).thenReturn(mockedResponse);

    EmployeeResponse response = employeeController.addEmployee(request);

     verify(employeeService, times(1)).addEmployee(request);

    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals("aa", response.getName());
    assertEquals("aa", response.getDepartment());
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
