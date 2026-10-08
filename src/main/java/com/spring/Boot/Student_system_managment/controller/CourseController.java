package com.spring.Boot.Student_system_managment.controller;

import com.spring.Boot.Student_system_managment.dto.CourseRequestDto;
import com.spring.Boot.Student_system_managment.dto.CourseResponseDto;
import com.spring.Boot.Student_system_managment.payLoad.ApiResponse;
import com.spring.Boot.Student_system_managment.service.CourseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseServiceImpl courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDto>> createCourse(@Valid @RequestBody CourseRequestDto requestDto){
        CourseResponseDto response = courseService.createCourse(requestDto);
        ApiResponse<CourseResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Course created successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>> getAllCourse(){
        List<CourseResponseDto> response = courseService.getAllCourse();
        ApiResponse<List<CourseResponseDto>> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Course fetched successfully",
                response
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseById(@PathVariable Long id){
        CourseResponseDto response = courseService.getCourseById(id);
        ApiResponse<CourseResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Course exists with id:" + id,
                response
        );
        return ResponseEntity.ok(apiResponse);
    }
}
