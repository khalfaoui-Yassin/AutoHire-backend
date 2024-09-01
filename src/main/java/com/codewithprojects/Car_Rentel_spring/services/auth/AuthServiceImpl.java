package com.codewithprojects.Car_Rentel_spring.services.auth;

import com.codewithprojects.Car_Rentel_spring.dto.SignupRequest;
import com.codewithprojects.Car_Rentel_spring.dto.UserDto;
import com.codewithprojects.Car_Rentel_spring.entity.User;
import com.codewithprojects.Car_Rentel_spring.enums.UserRole;
import com.codewithprojects.Car_Rentel_spring.repository.UserRepository;
import com.codewithprojects.Car_Rentel_spring.services.auth.AuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
@PostConstruct
    public void createAdminAccount(){
     User adminAccount =userRepository.findByUserRole(UserRole.ADMIN);
    if (adminAccount==null){
        User newAdminAccount = new User();
        newAdminAccount.setName("Admin");
        newAdminAccount.setEmail("admin@test.com");
        newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
        newAdminAccount.setUserRole(UserRole.ADMIN);
        userRepository.save(newAdminAccount);
        System.out.println("Admin account created successfuly");
    }
    }



    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User user= new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser=userRepository.save(user);
        UserDto userDto= new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
