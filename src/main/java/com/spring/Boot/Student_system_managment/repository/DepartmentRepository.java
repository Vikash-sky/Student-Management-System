package com.spring.Boot.Student_system_managment.repository;

import com.spring.Boot.Student_system_managment.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
