package com.movie.booking.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotEmpty(message = "First Name can't be empty!")
    private String firstName;

    @NotEmpty(message = "Last Name can't be empty!")
    private String lastName;

    @NotEmpty(message = "Email can't be empty!")
    @Indexed(unique = true)
    private String email;

    @NotNull(message = "Contact Number cannot be null!")
    private long contactNumber;
}
