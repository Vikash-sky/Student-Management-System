package com.spring.Boot.Student_system_managment.specification;

import com.spring.Boot.Student_system_managment.Entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {
    public static Specification<Student> hasName(String name){
        if(name==null||name.isBlank()){
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("name"),name);
    }

    public static Specification<Student> hasEmail(String email){
        if(email==null||email.isBlank()){
            return null;
        }
        return (root,query,criteriaBuilder)->
                criteriaBuilder.equal(root.get("email"),email);
    }
    public static Specification<Student> hasCourse(String course){
        if(course==null||course.isBlank()){
            return null;
        }
        return (root,query,criteriaBuilder)->
                criteriaBuilder.equal(root.get("course"),course);
    }

}
