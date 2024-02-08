package com.example.employeeManagement.service;

import com.example.employeeManagement.contract.request.EmployeeRequest;
import com.example.employeeManagement.contract.response.EmployeeResponse;
import com.example.employeeManagement.exception.DepartmentNotFoundException;
import com.example.employeeManagement.exception.EmailAlreadyExistsException;
import com.example.employeeManagement.exception.EmployeeNotFoundException;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public  EmployeeResponse addEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("employee with this email already exist");
        }
        Employee employee = modelMapper.map(request,Employee.class);
        Employee savedEmployee=employeeRepository.save(employee);
        return modelMapper.map(savedEmployee,EmployeeResponse.class);
    }

    public Employee getById(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(
                        ()-> new EmployeeNotFoundException("employee not found")
                );
    }
    public List<EmployeeResponse> getByDepartment(String department) {
        List<Employee> employee= (List<Employee>) employeeRepository
                .findByDepartment(department);
        if (employee.isEmpty()) {
            throw new DepartmentNotFoundException("No employees found for department: " + department);
        }
        return employee.stream()
                .map(employee1 -> modelMapper.map(employee1, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

}
