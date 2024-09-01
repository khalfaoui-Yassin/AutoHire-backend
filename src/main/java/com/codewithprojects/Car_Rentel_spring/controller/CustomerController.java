package com.codewithprojects.Car_Rentel_spring.controller;

import com.codewithprojects.Car_Rentel_spring.dto.BookCarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDto;
import com.codewithprojects.Car_Rentel_spring.dto.SearchCarDto;
import com.codewithprojects.Car_Rentel_spring.services.Customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
@GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars(){
        List<CarDto> carDtoList = customerService.getAllCars();
        return ResponseEntity.ok(carDtoList);
    }

    @PostMapping("/car/book/{carId}")
    public ResponseEntity<Void> bookACar(@PathVariable Long carId, @RequestBody BookCarDto bookCarDto) {
        boolean success = customerService.bookACar(bookCarDto);
        if (success) return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarByID(@PathVariable Long carId){
    CarDto carDto = customerService.getCarById(carId);
    if (carDto==null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(carDto);
    }
@GetMapping("/car/bookings/{userId}")
    public ResponseEntity<List<BookCarDto>>getBookingsByUserId(@PathVariable Long userId){
    return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }

    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
        return ResponseEntity.ok(customerService.searchCar(searchCarDto));
    }


}
