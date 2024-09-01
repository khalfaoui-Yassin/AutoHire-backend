package com.codewithprojects.Car_Rentel_spring.repository;

import com.codewithprojects.Car_Rentel_spring.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
}
