package com.example.employeeManagement.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
@AllArgsConstructor


public class EmployeeRequest {
    @NotBlank( message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Department cannot be blank")
    private String department;
    @NotBlank( message = "Email cannot be blank")
    @Email String email;
}
