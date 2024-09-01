package com.codewithprojects.Car_Rentel_spring.dto;

import com.codewithprojects.Car_Rentel_spring.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BookCarDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date toDate;

    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;
    private Long carId;
    private Long userId;
    private String username;
    private String email;
}
