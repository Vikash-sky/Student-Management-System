package com.spring.Boot.Student_system_managment.repository;

import com.spring.Boot.Student_system_managment.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
