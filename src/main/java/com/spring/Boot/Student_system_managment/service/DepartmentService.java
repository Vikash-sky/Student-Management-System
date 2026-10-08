package com.spring.Boot.Student_system_managment.service;

import com.spring.Boot.Student_system_managment.dto.DepartmentRequestDto;
import com.spring.Boot.Student_system_managment.dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto);
    List<DepartmentResponseDto> getAllDepartment();
    DepartmentResponseDto getDepartmentById(Long id);

}
