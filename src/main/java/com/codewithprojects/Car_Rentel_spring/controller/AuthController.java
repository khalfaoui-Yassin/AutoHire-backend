package com.codewithprojects.Car_Rentel_spring.controller;

import com.codewithprojects.Car_Rentel_spring.dto.AuthentificationRequest;
import com.codewithprojects.Car_Rentel_spring.dto.AuthentificationResponse;
import com.codewithprojects.Car_Rentel_spring.dto.SignupRequest;
import com.codewithprojects.Car_Rentel_spring.dto.UserDto;
import com.codewithprojects.Car_Rentel_spring.entity.User;
import com.codewithprojects.Car_Rentel_spring.repository.UserRepository;
import com.codewithprojects.Car_Rentel_spring.services.auth.AuthService;
import com.codewithprojects.Car_Rentel_spring.services.jwt.UserService;
import com.codewithprojects.Car_Rentel_spring.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private  final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private  final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("Customer already exists with this email", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdCustomerDto = authService.createCustomer(signupRequest);
        if (createdCustomerDto == null) {
            return new ResponseEntity<>("Customer not created, Come again later", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthentificationResponse createAuthenticationToken(@RequestBody AuthentificationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
            System.out.println("Authentication successful for user: " + authenticationRequest.getEmail());
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials for user: " + authenticationRequest.getEmail());
            throw new BadCredentialsException("Incorrect username or password.");
        }

       final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        System.out.println("User details loaded for user: " + userDetails.getUsername());

        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        AuthentificationResponse authenticationResponse = new AuthentificationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            System.out.println("JWT generated for user: " + userDetails.getUsername());
        } else {
            System.out.println("User not found in repository: " + userDetails.getUsername());
        }

        return authenticationResponse;
    }


}
