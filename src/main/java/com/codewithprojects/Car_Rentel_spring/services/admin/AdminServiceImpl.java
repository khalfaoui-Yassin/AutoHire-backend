package com.codewithprojects.Car_Rentel_spring.services.admin;

import com.codewithprojects.Car_Rentel_spring.dto.BookCarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDto;
import com.codewithprojects.Car_Rentel_spring.dto.CarDtoListDto;
import com.codewithprojects.Car_Rentel_spring.dto.SearchCarDto;
import com.codewithprojects.Car_Rentel_spring.entity.BookACar;
import com.codewithprojects.Car_Rentel_spring.entity.Car;
import com.codewithprojects.Car_Rentel_spring.enums.BookCarStatus;
import com.codewithprojects.Car_Rentel_spring.repository.BookACarRepository;
import com.codewithprojects.Car_Rentel_spring.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CarRepository carRepository;

    private final BookACarRepository bookACarRepository;


    @Override
    public boolean postCar(CarDto carDto) throws IOException {
        try {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setPrice(carDto.getPrice());
            car.setYear(carDto.getYear());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setTransmission(carDto.getTransmission());

            // تحويل الصورة إلى byte[]
            if (carDto.getImage() != null && !carDto.getImage().isEmpty()) {
                car.setImage(carDto.getImage().getBytes());
            }

            carRepository.save(car);
            return true;
        } catch (Exception e) {
            e.printStackTrace();  // قم بطباعة الاستثناءات للتعرف على الأخطاء
            return false;
        }
    }


    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);


    }

    @Override
    public CarDto getCarById(Long id) {
        Optional<Car> optionalCar=carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);    }



    @Override
    public boolean updateCar(Long carId, CarDto carDto) throws IOException {

        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()){
            Car existingCar = optionalCar.get();
            if (carDto.getImage()!=null)
                existingCar.setImage(carDto.getImage().getBytes());
            existingCar.setPrice(carDto.getPrice());
            existingCar.setYear(carDto.getYear());
            existingCar.setType(carDto.getType());
            existingCar.setDescription(carDto.getDescription());
            existingCar.setTransmission(carDto.getTransmission());
            existingCar.setColor(carDto.getColor());
            existingCar.setName(carDto.getName());
            existingCar.setBrand(carDto.getBrand());
            carRepository.save(existingCar);
            return true;


        }else {
            return false;

        }

    }

    @Override
    public List<BookCarDto> getBookings() {
        return bookACarRepository.findAll().stream().map(BookACar::getBookingCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<BookACar> optionalBookACar= bookACarRepository.findById(bookingId);
        if (optionalBookACar.isPresent()){
            BookACar existingBookACar=optionalBookACar.get();
            if (Objects.equals(status,"Approve"))
                existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
            else
                existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);
            bookACarRepository.save(existingBookACar);
            return true;
        }
        return false;
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

        Example<Car>carExample=Example.of(car,exampleMatcher);
        List<Car> carList = carRepository.findAll(carExample);
        CarDtoListDto carDtoListDto=new CarDtoListDto();
        carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));




        return carDtoListDto;
    }

}








