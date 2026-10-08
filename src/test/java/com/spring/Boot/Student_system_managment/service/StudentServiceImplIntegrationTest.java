package com.spring.Boot.Student_system_managment.service;

import com.spring.Boot.Student_system_managment.Entity.Address;
import com.spring.Boot.Student_system_managment.Entity.Course;
import com.spring.Boot.Student_system_managment.Entity.Department;
import com.spring.Boot.Student_system_managment.Entity.Student;
import com.spring.Boot.Student_system_managment.dto.StudentResponseDto;
import com.spring.Boot.Student_system_managment.exception.StudentNotFoundException;
import com.spring.Boot.Student_system_managment.repository.CourseRepository;
import com.spring.Boot.Student_system_managment.repository.DepartmentRepository;
import com.spring.Boot.Student_system_managment.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentServiceImplIntegrationTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private StudentService studentService;

    @Test
    void shouldReturnStudentById(){
        //Arrange
        Department department = new Department();
        department.setDepartmentName("Computer Science");
        department = departmentRepository.save(department);

        Course course = new Course();
        course.setCourseName("Spring boot");
        course.setFees(12000.0);
        course.setInstructorName("Vikash");
        course.setDuration("6 months");
        course = courseRepository.save(course);

        Address address = new Address();
        address.setCity("East-Champaran");
        address.setState("Bihar");
        address.setCountry("India");

        Student student = new Student();
        student.setName("Vikash Singh Rajput");
        student.setEmail("vikash@gmail.com");
        student.setPassword("vikash@122");
        student.setAddress(address);
        student.setDepartment(department);
        student.setCourses(List.of(course));

        Student saveStudent = studentRepository.save(student);

        //Act
        StudentResponseDto result = studentService.getStudentById(saveStudent.getId());


        //Assert

        assertEquals(saveStudent.getId(),result.getId());
        assertEquals("Vikash Singh Rajput",result.getName());
        assertEquals("vikash@gmail.com",result.getEmail());
        assertEquals("East-Champaran",result.getAddress().getCity());
        assertEquals("Bihar",result.getAddress().getState());
        assertEquals("India",result.getAddress().getCountry());
        assertEquals("Computer Science",result.getDepartmentName());
        assertEquals(1,result.getCourses().size());
        assertEquals("Spring boot",result.getCourses().get(0).getCourseName());

    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound(){
        assertThrows(StudentNotFoundException.class,()->
                studentService.getStudentById(99999L));
    }
}
