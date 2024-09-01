package com.codewithprojects.Car_Rentel_spring.entity;

import com.codewithprojects.Car_Rentel_spring.dto.BookCarDto;
import com.codewithprojects.Car_Rentel_spring.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class BookACar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "from_date")
    private Date fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "to_date")
    private Date toDate;


    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Car car;

    public BookCarDto getBookingCarDto() {
        BookCarDto bookCarDto = new BookCarDto();
        bookCarDto.setId(id);
        bookCarDto.setDays(days);
        bookCarDto.setBookCarStatus(bookCarStatus);
        bookCarDto.setPrice(price);
        bookCarDto.setToDate(toDate);
        bookCarDto.setFromDate(fromDate);
        bookCarDto.setEmail(user.getEmail());
        bookCarDto.setUsername(user.getName());
        bookCarDto.setUserId(user.getId());
        bookCarDto.setCarId(car.getId());
        return bookCarDto;
    }
}
