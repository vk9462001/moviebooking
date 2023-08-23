package com.movie.booking.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {

    @Id
    private String id;

    @NotEmpty(message = "Title cannot be empty!")
    private String title;

    private String releaseDate;

    @NotEmpty(message = "Language cannot be empty!")
    private String originalLanguage;

    @NotEmpty(message = "Overview cannot be empty!")
    private String overview;

    @NotEmpty(message = "Category cannot be empty!")
    private String category;

    @NotEmpty(message = "Poster URL cannot be empty!")
    private String poster;

    @NotEmpty(message = "Backdrop Path URL cannot be empty!")
    private String backdropPath;
}
