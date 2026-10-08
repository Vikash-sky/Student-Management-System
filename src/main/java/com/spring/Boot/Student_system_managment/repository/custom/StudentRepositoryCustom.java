package com.spring.Boot.Student_system_managment.repository.custom;

import com.spring.Boot.Student_system_managment.Entity.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> findStudentCustom();
    List<Student> findStudentByDepartment(String departmentName);
}
