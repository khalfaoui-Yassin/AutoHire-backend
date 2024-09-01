package com.codewithprojects.Car_Rentel_spring.services.Customer;

import com.codewithprojects.Car_Rentel_spring.dto.BookCarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDtoListDto;
import com.codewithprojects.Car_Rentel_spring.dto.SearchCarDto;
import com.codewithprojects.Car_Rentel_spring.entity.BookACar;
import com.codewithprojects.Car_Rentel_spring.entity.Car;
import com.codewithprojects.Car_Rentel_spring.entity.User;
import com.codewithprojects.Car_Rentel_spring.enums.BookCarStatus;
import com.codewithprojects.Car_Rentel_spring.repository.BookACarRepository;
import com.codewithprojects.Car_Rentel_spring.repository.CarRepository;
import com.codewithprojects.Car_Rentel_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookCarDto bookCarDto) {
        // Check if the car and user exist
        Optional<Car> optionalCar = carRepository.findById(bookCarDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bookCarDto.getUserId());

        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            // Ensure that both fromDate and toDate are provided
            if (bookCarDto.getFromDate() == null || bookCarDto.getToDate() == null) {
                throw new IllegalArgumentException("Both fromDate and toDate must be provided");
            }

            // Calculate the difference in days between fromDate and toDate
            long diffInMilliSeconds = bookCarDto.getToDate().getTime() - bookCarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);

            // Create a new booking
            Car existingCar = optionalCar.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            bookACar.setDays(days);
            bookACar.setPrice(existingCar.getPrice() * days);

            // Log the dates for debugging purposes
            System.out.println("From Date: " + bookCarDto.getFromDate());
            System.out.println("To Date: " + bookCarDto.getToDate());

            // Save the booking to the database
            bookACarRepository.save(bookACar);
            return true;
        } else {
            // If the car or user is not found, log the error
            System.err.println("Car or User not found. Car ID: " + bookCarDto.getCarId() + ", User ID: " + bookCarDto.getUserId());
        }
        return false;
    }




    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public List<BookCarDto> getBookingsByUserId(Long userId) {
        List<BookCarDto> bookings = bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookingCarDto).collect(Collectors.toList());

        // تسجيل التواريخ
        for (BookCarDto booking : bookings) {
            System.out.println("Booking ID: " + booking.getId());
            System.out.println("From Date: " + booking.getFromDate());
            System.out.println("To Date: " + booking.getToDate());
        }

        return bookings;
    }


    @Override
    public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setTransmission(searchCarDto.getTransmission());
        car.setColor(searchCarDto.getColor());
        ExampleMatcher exampleMatcher =
                ExampleMatcher.matchingAll()
                        .withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("type",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("transmission",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("color",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample=Example.of(car,exampleMatcher);
        List<Car> carList = carRepository.findAll(carExample);
        CarDtoListDto carDtoListDto=new CarDtoListDto();
        carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));




        return carDtoListDto;
    }

}
