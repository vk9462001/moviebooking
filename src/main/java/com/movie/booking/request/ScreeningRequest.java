package com.movie.booking.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningRequest {

    @Id
    private String id;

    @NotEmpty(message = "Movie name can't be empty!")
    private String movieName;

    @NotEmpty(message = "Screening date can't be empty!")
    private String screeningDate;

    private double price;

    @NotEmpty(message = "Theater id can't be empty!")
    private String theaterId;
}
