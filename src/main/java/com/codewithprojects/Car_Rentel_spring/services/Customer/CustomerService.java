package com.codewithprojects.Car_Rentel_spring.services.Customer;

import com.codewithprojects.Car_Rentel_spring.dto.BookCarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDtoListDto;
import com.codewithprojects.Car_Rentel_spring.dto.SearchCarDto;

import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    boolean bookACar(BookCarDto bookCarDto);

    CarDto getCarById(Long carId);

    List <BookCarDto> getBookingsByUserId(Long userId);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);

}
