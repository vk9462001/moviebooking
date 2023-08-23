package com.movie.booking.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowTimeRequest {

    @Id
    private String id;

    @NotEmpty(message = "Screening time can't be empty!")
    private String screeningTime;

    private int totalTickets;

    @NotEmpty(message = "Screening id can't be empty!")
    private String screeningId;
}
