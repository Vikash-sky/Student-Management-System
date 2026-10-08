package com.spring.Boot.Student_system_managment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequestDto {
    @NotBlank(message = "Department is required")
    @Size(max = 100,message = "Department name cannot exceed 100 character")
    private String departmentName;
}
