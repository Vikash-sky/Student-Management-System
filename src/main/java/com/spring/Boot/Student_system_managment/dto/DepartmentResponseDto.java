package com.spring.Boot.Student_system_managment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDto {
    private Long id;
    private String departmentName;
    private List<StudentResponseDto> students;
}
