package com.spring.Boot.Student_system_managment.service;

import com.spring.Boot.Student_system_managment.Entity.Course;
import com.spring.Boot.Student_system_managment.dto.CourseRequestDto;
import com.spring.Boot.Student_system_managment.dto.CourseResponseDto;
import com.spring.Boot.Student_system_managment.exception.ResourcesNotFoundException;
import com.spring.Boot.Student_system_managment.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    @Override
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        Course course = new Course();
        course.setCourseName(requestDto.getCourseName());
        course.setDuration(requestDto.getDuration());
        course.setFees(requestDto.getFees());
        course.setInstructorName(requestDto.getInstructorName());
        Course savedCourse = courseRepository.save(course);
        return mapToResponseDto(savedCourse);
    }

    @Override
    public List<CourseResponseDto> getAllCourse() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()->
                new ResourcesNotFoundException("Course not found with id: "+id)
        );
        return mapToResponseDto(course);
    }

    private CourseResponseDto mapToResponseDto(Course course){
        CourseResponseDto responseDto = new CourseResponseDto();
        responseDto.setId(course.getId());
        responseDto.setCourseName(course.getCourseName());
        responseDto.setDuration(course.getDuration());
        responseDto.setFees(course.getFees());
        responseDto.setInstructorName(course.getInstructorName());
        return responseDto;
    }
}
