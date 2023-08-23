package com.movie.booking.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    @NotEmpty(message = "LoginId can't be empty!")
    private String loginId;

    @NotEmpty(message = "Password can't be empty!")
    private String password;
}
