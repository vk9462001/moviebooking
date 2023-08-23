package com.movie.booking.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotEmpty(message = "OTP can't be empty!")
    private String otp;

    @NotEmpty(message = "Password can't be empty!")
    private String newPassword;
}
