package com.spring.Boot.Student_system_managment.controller;

import com.spring.Boot.Student_system_managment.dto.DepartmentRequestDto;
import com.spring.Boot.Student_system_managment.dto.DepartmentResponseDto;
import com.spring.Boot.Student_system_managment.payLoad.ApiResponse;
import com.spring.Boot.Student_system_managment.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> createDepartment(@Valid @RequestBody DepartmentRequestDto requestDto){
        DepartmentResponseDto response = departmentService.createDepartment(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Department created successfully",
                response
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getAllDepartment(){
        List<DepartmentResponseDto> response = departmentService.getAllDepartment();
        ApiResponse<List<DepartmentResponseDto>> apiResponse =new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Department fetched successfully",
                response
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>>  getDepartmentById(@Valid @PathVariable Long id) {
        DepartmentResponseDto response = departmentService.getDepartmentById(id);
        ApiResponse<DepartmentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Department exists with id:" + id,
                response
        );
    return ResponseEntity.ok(apiResponse);
    }

}
