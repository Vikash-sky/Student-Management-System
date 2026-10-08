package com.spring.Boot.Student_system_managment.repository.custom;

import com.spring.Boot.Student_system_managment.Entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Student> findStudentCustom() {
        TypedQuery<Student> query=entityManager.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    @Override
    public List<Student> findStudentByDepartment(String departmentName) {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.department.departmentName=:departmentName", Student.class);
       query.setParameter("departmentName",departmentName);
        return query.getResultList();
    }
}
