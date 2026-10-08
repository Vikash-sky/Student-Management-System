package com.spring.Boot.Student_system_managment.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentResponseDto {
    private Long id;
    private String name;
    private String email;
    private AddressResponseDTO address;
    private String departmentName;
    private List<CourseResponseDto> courses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;
    private Long version;
}
