package com.codewithprojects.Car_Rentel_spring.dto;

import lombok.Data;

@Data
public class AuthentificationRequest {
    private String email;
    private  String password;
}
