package com.spring.Boot.Student_system_managment.service;

import com.spring.Boot.Student_system_managment.dto.CourseRequestDto;
import com.spring.Boot.Student_system_managment.dto.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto requestDto);
    List<CourseResponseDto> getAllCourse();
    CourseResponseDto getCourseById(Long id);
}
