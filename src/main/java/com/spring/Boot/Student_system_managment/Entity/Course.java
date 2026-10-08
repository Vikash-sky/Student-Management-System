package com.spring.Boot.Student_system_managment.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String courseName;
    private String duration;
    private  Double fees;
    private String instructorName;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
}
