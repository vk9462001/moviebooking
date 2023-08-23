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
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;

    @DBRef
    private Screening screening;

    @DBRef
    private ShowTime showTime;

    private String currentDate;

    private int noOfTickets;

    private double totalPrice;

    @NotEmpty(message = "Username can't be empty!")
    private String username;
}
