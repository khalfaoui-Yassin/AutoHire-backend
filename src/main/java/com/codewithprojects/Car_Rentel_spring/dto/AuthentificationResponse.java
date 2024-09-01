package com.codewithprojects.Car_Rentel_spring.dto;

import com.codewithprojects.Car_Rentel_spring.enums.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class AuthentificationResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;
}
