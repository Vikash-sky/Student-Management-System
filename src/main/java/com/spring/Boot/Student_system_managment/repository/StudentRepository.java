package com.spring.Boot.Student_system_managment.repository;

import com.spring.Boot.Student_system_managment.Entity.Student;
import com.spring.Boot.Student_system_managment.Projection.StudentProjection;
import com.spring.Boot.Student_system_managment.dto.StudentProjectionDto;
import com.spring.Boot.Student_system_managment.repository.custom.StudentRepositoryCustom;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student>, StudentRepositoryCustom {
boolean existsByEmail(String email);
Optional<Student> findByEmail(String email);
@Query("""
       SELECT COUNT(s)
       FROM Student s
       JOIN s.courses c
       where LOWER(c.courseName) = LOWER(:courseName)
""")
    Long countStudentByCourse(@Param("courseName")String courseName);
    @Modifying
    @Query("DELETE FROM Student s WHERE s.email = :email")
    void deleteByEmail(@Param("email") String email);

    @Query("""
    SELECT s FROM Student s
    JOIN s.courses c
    WHERE s.name = :name
    AND LOWER(c.courseName) = LOWER(:courseName)
    """)
    List<Student> findByNameAndCourse(
            @Param("name") String name,
            @Param("courseName") String courseName
    );

@Query(value = "SELECT * FROM students WHERE email=:email",
nativeQuery = true
)
    Optional<Student> findStudentByEmailNative(@Param("email")String email);

    @Query(value = "SELECT * FROM students WHERE id=:id",
            nativeQuery = true
    )
    Optional<Student> findStudentByIdNative(@Param("id")Long id);
    List<StudentProjection> findBy();

    @Query("""
             SELECT new com.spring.Boot.Student_system_managment
            .dto.StudentProjectionDto(
                      s.name,
                      s.email
               )
                         FROM Student s
          """)
    List<StudentProjectionDto> getStudentProjection();
//    @Query("""
//            SELECT DISTINCT s
//            FROM Student s
//            LEFT JOIN FETCH s.courses
//            LEFT JOIN FETCH s.department
//            LEFT JOIN FETCH s.address
//            """)
//    List<Student> findAllWithDetails();

    @EntityGraph(attributePaths = {"address","department","courses"})
    @Query("SELECT s FROM Student s")
    List<Student> findAllWithDetails();
}
