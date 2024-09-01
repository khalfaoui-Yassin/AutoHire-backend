package com.codewithprojects.Car_Rentel_spring.dto;

import com.codewithprojects.Car_Rentel_spring.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
