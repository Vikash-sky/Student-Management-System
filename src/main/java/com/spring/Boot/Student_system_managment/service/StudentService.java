package com.spring.Boot.Student_system_managment.service;


import com.spring.Boot.Student_system_managment.dto.StudentProjectionDto;
import com.spring.Boot.Student_system_managment.dto.StudentRequestDto;
import com.spring.Boot.Student_system_managment.dto.StudentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface StudentService {

    StudentResponseDto saveStudent(StudentRequestDto dto);
    List<StudentResponseDto> getAllStudent();
    StudentResponseDto getStudentById(Long id);
    void deleteStudent(Long id);
    StudentResponseDto updateStudent(Long id,StudentRequestDto dto);
    StudentResponseDto patchStudent(Long id,StudentRequestDto dto);
    StudentResponseDto getStudentByEmail(String email);
    Long getStudentCountByCourse(String courseName);
    void deleteStudentByEmail(String email);
     List<StudentResponseDto> getStudentByNameAndCourse(String name,String course);
     StudentResponseDto getStudentByEmailNative(String email);
    StudentResponseDto getStudentByIdNative(Long id);
    List<StudentResponseDto> getAllStudentsSortedByName();
    Page<StudentResponseDto> getStudent(Pageable pageable);
    List<StudentProjectionDto> getStudentProjection();

    List<StudentResponseDto> searchStudents(String name,String email,String course);
    List<StudentResponseDto> getAllStudentCustom();

    List<StudentResponseDto> getStudentByDepartmentCustom(String departmentName);
}
