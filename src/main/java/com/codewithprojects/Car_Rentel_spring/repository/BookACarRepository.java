package com.codewithprojects.Car_Rentel_spring.repository;

import com.codewithprojects.Car_Rentel_spring.entity.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookACarRepository extends JpaRepository<BookACar,Long> {
    List<BookACar> findAllByUserId(Long userId);
}
