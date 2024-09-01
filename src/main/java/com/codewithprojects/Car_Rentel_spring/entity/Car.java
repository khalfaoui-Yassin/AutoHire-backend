package com.codewithprojects.Car_Rentel_spring.entity;

import com.codewithprojects.Car_Rentel_spring.dto.CarDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name="cars")
public class Car {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String brand;
 private String color;
 private String name;
 private String type;
 private String transmission;
 private String description;
 private Long price;
 private LocalDateTime year;

 @Lob  // استخدم @Lob مع longblob في قاعدة البيانات
 @Column(name = "image",columnDefinition = "LONGBLOB")
 private byte[] image;

 public CarDto getCarDto() {
  CarDto carDto = new CarDto();
  carDto.setId(id);
  carDto.setName(name);
  carDto.setBrand(brand);
  carDto.setColor(color);
  carDto.setPrice(price);
  carDto.setDescription(description);
  carDto.setType(type);
  carDto.setTransmission(transmission);
  carDto.setYear(year);
  carDto.setReturnedImage(image);
  return carDto;
 }
}
