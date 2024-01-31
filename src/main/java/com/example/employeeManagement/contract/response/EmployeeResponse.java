package com.example.employeeManagement.contract.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeResponse {
    private Long id;
    private String name;
    private String department;
    private String email;
}
