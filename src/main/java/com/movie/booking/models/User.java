package com.movie.booking.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

	@Id
	private String id;

	@NotEmpty(message = "First Name can't be empty!")
	private String firstName;

	@NotEmpty(message = "Last Name can't be empty!")
	private String lastName;

	@NotEmpty(message = "Email can't be empty!")
	@Indexed(unique = true)
	private String email;

	@NotEmpty(message = "LoginId can't be empty!")
	@Indexed(unique = true)
	private String loginId;

	@NotEmpty(message = "Password can't be empty!")
	private String password;

	@NotEmpty(message = "Confirm Password can't be empty!")
	private String confirmPassword;

	@NotNull(message = "Contact Number cannot be null!")
	private long contactNumber;

	private String role;

	private String otp;

	private LocalDateTime otpCreationDate;
}
