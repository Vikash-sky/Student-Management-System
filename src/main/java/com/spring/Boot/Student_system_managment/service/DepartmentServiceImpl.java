package com.spring.Boot.Student_system_managment.service;

import com.spring.Boot.Student_system_managment.Entity.Department;
import com.spring.Boot.Student_system_managment.dto.CourseResponseDto;
import com.spring.Boot.Student_system_managment.dto.DepartmentRequestDto;
import com.spring.Boot.Student_system_managment.dto.DepartmentResponseDto;
import com.spring.Boot.Student_system_managment.dto.StudentResponseDto;
import com.spring.Boot.Student_system_managment.exception.ResourcesNotFoundException;
import com.spring.Boot.Student_system_managment.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;
    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Department department = new Department();
        department.setDepartmentName(requestDto.getDepartmentName());
        Department savedDepartment = departmentRepository.save(department);
        return mapToResponseDto(savedDepartment);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartment() {
        return departmentRepository.findAll().
                stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(()->
                new ResourcesNotFoundException("Department not found with id: "+id));
        return mapToResponseDto(department);
    }

    private DepartmentResponseDto mapToResponseDto(Department department){
        DepartmentResponseDto dto = new DepartmentResponseDto();
        List<StudentResponseDto> students = department.getStudents()
                .stream()
                .map(student -> {
                    StudentResponseDto studentDto = new StudentResponseDto();
                    studentDto.setId(student.getId());
                    studentDto.setName(student.getName());
                    studentDto.setEmail(student.getEmail());
                    studentDto.setCourses(student.getCourses()
                            .stream()
                            .map(
                                    course ->{
                                        CourseResponseDto courseDto = new CourseResponseDto();
                                        courseDto.setId(course.getId());
                                        courseDto.setCourseName(course.getCourseName());
                                        return courseDto;
                                    }).toList()
                    );
                    return studentDto;
                })
                .toList();
        dto.setId(department.getId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setStudents(students);
        return dto;
    }
}
