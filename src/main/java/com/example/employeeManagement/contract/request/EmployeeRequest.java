package com.example.employeeManagement.contract.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Email;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor


public class EmployeeRequest {
    private String name;
    private String department;
    @Email String email;
}
