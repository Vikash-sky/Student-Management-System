package com.spring.Boot.Student_system_managment.service;

import com.spring.Boot.Student_system_managment.Entity.Address;
import com.spring.Boot.Student_system_managment.Entity.Department;
import com.spring.Boot.Student_system_managment.Entity.Student;
import com.spring.Boot.Student_system_managment.dto.StudentResponseDto;
import com.spring.Boot.Student_system_managment.exception.StudentNotFoundException;
import com.spring.Boot.Student_system_managment.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplUnitTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldReturnStudentById() {
        //Arrange
        Student student = new Student();
        student.setId(1L);
        student.setName("Vikash singh");
        student.setEmail("VikashSingh@gmail.com");

        Address address = new Address();
        address.setCity("Champaran");
        address.setState("Bihar");
        address.setCountry("Bharat");
        student.setAddress(address);

        Department department = new Department();
        department.setDepartmentName("CSE");
        student.setDepartment(department);
        student.setCourses(List.of());
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //act
        StudentResponseDto result = studentService.getStudentById(10L);

        //Assert
        assertEquals(1L, result.getId());
        assertEquals("Vikash singh", result.getName());
        assertEquals("VikashSingh@gmail.com", result.getEmail());
//        verify(studentRepository,times(1)).findById(1L);
//
//        verify(studentRepository,never()).deleteById(1L);
    }
        @Test
        void shouldThrowExceptionWhenRepositoryFails(){
        //Arrange
            when(studentRepository.findById(1L)).thenThrow(new RuntimeException());

            //Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,()->
                    studentService.getStudentById(1L)
            );
        }
        @Test
    void shouldThrowExceptionWhenStudentNotFound(){
        //Arrange
            when(studentRepository.findById(99L)).thenReturn(Optional.empty());

            //Act
            assertThrows(
                    StudentNotFoundException.class,()->
                            studentService.getStudentById(99L)
            );
        }

}
