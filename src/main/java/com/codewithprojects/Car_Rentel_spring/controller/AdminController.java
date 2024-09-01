package com.codewithprojects.Car_Rentel_spring.controller;

import com.codewithprojects.Car_Rentel_spring.dto.BookCarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDto;
import com.codewithprojects.Car_Rentel_spring.dto.SearchCarDto;
import com.codewithprojects.Car_Rentel_spring.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/car")
    public ResponseEntity<String> postCar(@ModelAttribute CarDto carDto) {
        try {
            boolean success = adminService.postCar(carDto);
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Car created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create car");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing car image");
        }
    }




    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars() {
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        adminService.deleteCar(id);
        return ResponseEntity.ok("Car deleted successfully");
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        CarDto carDto = adminService.getCarById(id);
        if (carDto != null) {
            return ResponseEntity.ok(carDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/car/{carId}")
    public ResponseEntity<Map<String, String>> updateCar(@PathVariable Long carId, @ModelAttribute CarDto carDto) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean success = adminService.updateCar(carId, carDto);
            if (success) {
                response.put("message", "Car updated successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Car not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IOException e) {
            response.put("message", "Error processing car image");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/car/bookings")
    public ResponseEntity<List<BookCarDto>> getBookings(){
        return ResponseEntity.ok(adminService.getBookings());
    }


    @GetMapping("/car/booking/{bookingId}/{status}")
    public ResponseEntity<?>changeBookingStatus(@PathVariable Long bookingId,@PathVariable String status){
        boolean success=adminService.changeBookingStatus(bookingId,status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
        return ResponseEntity.ok(adminService.searchCar(searchCarDto));
    }





}
