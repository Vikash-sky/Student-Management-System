package com.spring.Boot.Student_system_managment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    @NotBlank(message = "Course Name is required")
    private String courseName;
    @NotBlank(message = "Duration is required")
    private String duration;
    @NotNull(message = "Fees is required")
    private Double fees;
    @NotBlank(message = "Instructor Name is required")
    private String instructorName;
}
