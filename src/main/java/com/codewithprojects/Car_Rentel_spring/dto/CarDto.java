package com.codewithprojects.Car_Rentel_spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// تأكد من أن CarDto يمكنها الربط الصحيح لـ multipart/form-data
@Data
public class CarDto {
    private Long id;
    private String brand;
    private String color;
    private String name;
    private String type;
    private String transmission;
    private String description;
    private Long price;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime year;

    private MultipartFile image; // For handling the file upload
    private byte[] returnedImage; // For returning the image
}


