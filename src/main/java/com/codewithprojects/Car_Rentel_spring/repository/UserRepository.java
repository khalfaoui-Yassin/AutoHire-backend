package com.codewithprojects.Car_Rentel_spring.repository;

import com.codewithprojects.Car_Rentel_spring.entity.User;
import com.codewithprojects.Car_Rentel_spring.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


   Optional<User> findFirstByEmail(String email);

    User findByUserRole(UserRole userRole);
}
