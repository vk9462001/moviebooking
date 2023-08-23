package com.movie.booking.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "screenings")
public class Screening {

    @Id
    private String id;

    @NotEmpty(message = "Movie name can't be empty!")
    private String movieName;

    @NotEmpty(message = "Screening date can't be empty!")
    private String screeningDate;

    @DBRef
    private List<ShowTime> showTime;

    private double price;

    @DBRef
    private Theater theater;
}
