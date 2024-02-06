package com.example.employeeManagement.serviceTest;

import com.example.employeeManagement.contract.request.EmployeeRequest;
import com.example.employeeManagement.contract.response.EmployeeResponse;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.repository.EmployeeRepository;
import com.example.employeeManagement.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    void testAddEmployee(){
        EmployeeRequest employeeRequest = new EmployeeRequest(null,null,null);
        Employee savedEmployee= new Employee(1L,null,null,null);
        EmployeeResponse expectedResponse= modelMapper.map(savedEmployee,EmployeeResponse.class);
        when(employeeRepository.save(any())).thenReturn(savedEmployee);
        EmployeeResponse actualResponse=employeeService.addEmployee(employeeRequest);
        assertEquals(expectedResponse,actualResponse);
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
    void testGetById_WhenEmployeeNotFound_ThrowsEntityNotFoundException() {

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            employeeService.getById(1L);
        });

            verify(employeeRepository, times(1)).findById(1L);
    }


    @Test
    void testGetByDepartment(){
        String department= "DEPARTMENT";
        List<Employee> sampleEmployee= Collections.singletonList(new Employee(1L,null,null,null));
        when(employeeRepository.findByDepartment(department.valueOf(department))).thenReturn(Optional.of(sampleEmployee));
        List<EmployeeResponse>employeeResponses=employeeService.getByDepartment(department);
        assertEquals(0,employeeResponses.size());
    }
}
