package com.example.employeeManagement.serviceTest;

import com.example.employeeManagement.contract.request.EmployeeRequest;
import com.example.employeeManagement.contract.response.EmployeeResponse;
import com.example.employeeManagement.exception.DepartmentNotFoundException;
import com.example.employeeManagement.exception.EmailAlreadyExistsException;
import com.example.employeeManagement.exception.EmployeeNotFoundException;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.repository.EmployeeRepository;
import com.example.employeeManagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServiceTest {
    private ModelMapper modelMapper;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        modelMapper=new ModelMapper();
        employeeService=new EmployeeService(employeeRepository,modelMapper);
    }
    @Test
    void testAddEmployee() {
        EmployeeRequest request = new EmployeeRequest("jj", "department", "jj@gmail.com");
        Employee employee = modelMapper.map(request, Employee.class);
        EmployeeResponse expectedResponse = modelMapper.map(employee, EmployeeResponse.class);

        when(employeeRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(
                EmailAlreadyExistsException.class, () -> employeeService.addEmployee(request));
        when(employeeRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeResponse actualResponse = employeeService.addEmployee(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetById(){
        Employee sampleEmployee= new Employee(1L,null,null,null);
        Long id=1L;
        when (employeeRepository.findById(id)).thenReturn(Optional.of(sampleEmployee));
        Employee retrievedEmployee= employeeService.getById(id);
        assertEquals(sampleEmployee,retrievedEmployee);
    }
    @Test
    void testGetById_WhenEmployeeNotFound_ThrowsEmployeeNotFoundException() {

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getById(1L);
        });

            verify(employeeRepository, times(1)).findById(1L);
    }
    @Test
    void testGetByDepartment() {
        String department = "Development";
        Employee employeeOne = new Employee(1L, "jj", "jj@gmail.com", department);
        Employee employeeTwo = new Employee(1L, "jjj", "jjj@gmail.com", department);

        List<Employee> employees = Arrays.asList(employeeOne, employeeTwo);
        List<EmployeeResponse> expectedResponse =
                employees.stream()
                        .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                        .collect(Collectors.toList());

        when(employeeRepository.findByDepartment(department)).thenReturn(Collections.emptyList());
        assertThrows(
                DepartmentNotFoundException.class,
                () -> employeeService.getByDepartment(department));
        when(employeeRepository.findByDepartment(department)).thenReturn(employees);
        List<EmployeeResponse> actualResponse =
                employeeService.getByDepartment(department);
        assertEquals(expectedResponse, actualResponse);
    }


}
