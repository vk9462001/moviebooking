package com.movie.booking.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "showtimes")
public class ShowTime {

    @Id
    private String id;

    @NotEmpty(message = "Movie name can't be empty!")
    private String movieName;

    @NotEmpty(message = "Screening date can't be empty!")
    private String screeningDate;

    @NotEmpty(message = "Theater data can't be empty!")
    private String theater;

    @NotEmpty(message = "Screening time can't be empty!")
    private String screeningTime;

    private int totalTickets;
    private int bookedTickets;
    private int availableTickets;

    @NotEmpty(message = "Screening id can't be empty!")
    private String screeningId;
}
