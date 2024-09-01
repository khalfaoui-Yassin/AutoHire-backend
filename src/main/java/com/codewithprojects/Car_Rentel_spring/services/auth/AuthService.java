package com.codewithprojects.Car_Rentel_spring.services.auth;

import com.codewithprojects.Car_Rentel_spring.dto.SignupRequest;
import com.codewithprojects.Car_Rentel_spring.dto.UserDto;
import org.springframework.stereotype.Repository;


public interface AuthService {
    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);



}
