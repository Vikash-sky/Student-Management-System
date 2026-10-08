package com.spring.Boot.Student_system_managment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class StudentRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min=3,max=30,message = "Name must be between 2 and 30 character")
    private String name;
    @NotBlank(message = "course is required")
    @Email(message = "pleas enter a valid email")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min=8,message = "password must contain at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&!=+]).{8,}$",
            message = "pleas enter a strong password e.g.  A@8&h1!hA%"
    )
    private String password;
    private AddressRequestDTO address;

    @NotNull(message = "Department id is required")
    private Long departmentId;
    @NotEmpty(message = "course is required")
    private List<Long> courseIds;

    private Long version;

}
