package com.movie.booking.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "theaters")
public class Theater {

    @Id
    private String id;

    @NotEmpty(message = "Theater name can't be empty!")
    private String theaterName;

    @NotEmpty(message = "Theater location can't be empty!")
    private String theaterLocation;
}
