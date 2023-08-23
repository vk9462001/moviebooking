package com.movie.booking.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    @Id
    private String id;

    @NotEmpty(message = "Screening id can't be empty!")
    private String screeningId;

    @NotEmpty(message = "Show time can't be empty!")
    private String showTimeId;

    private int noOfTickets;

    @NotEmpty(message = "Username can't be empty!")
    private String username;
}
