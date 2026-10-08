package com.spring.Boot.Student_system_managment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressRequestDTO {
    @NotBlank(message = "City is required")
    private String city;
    private String state;
    private String country;
}
