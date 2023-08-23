package com.movie.booking.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String loginId;
    private long contactNumber;
    private String role;
}
