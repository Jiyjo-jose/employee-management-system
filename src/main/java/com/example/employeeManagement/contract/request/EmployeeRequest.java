package com.example.employeeManagement.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeRequest {
    @NotBlank( message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Department cannot be blank")
    private String department;
    @NotBlank( message = "Email cannot be blank")
    @Email String email;
}
