package com.codewithprojects.Car_Rentel_spring.services.admin;

import com.codewithprojects.Car_Rentel_spring.dto.BookCarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDtoListDto;
import com.codewithprojects.Car_Rentel_spring.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    boolean postCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();

    void deleteCar(Long id);

    CarDto getCarById(Long id);



    boolean updateCar(Long carId, CarDto carDto) throws IOException;

    List<BookCarDto> getBookings();

    boolean changeBookingStatus(Long bookingId,String status);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
