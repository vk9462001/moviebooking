package com.movie.booking.controllers;

import com.movie.booking.models.User;
import com.movie.booking.request.ChangePasswordRequest;
import com.movie.booking.request.JwtRequest;
import com.movie.booking.request.ForgotPasswordRequest;
import com.movie.booking.request.UserRequest;
import com.movie.booking.response.JwtResponse;
import com.movie.booking.response.SignUpResponse;
import com.movie.booking.services.EmailService;
import com.movie.booking.services.JwtService;
import com.movie.booking.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
@CrossOrigin(origins = {"*"})
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    String regex = "^(.+)@(.+)$";
    Pattern pattern = Pattern.compile(regex);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid JwtRequest jwtrequest){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(jwtrequest.getLoginId(),jwtrequest.getPassword());

        try{
            authenticationManager.authenticate(authenticationToken);
            String token = jwtService.generateToken(jwtrequest.getLoginId());
            User user = service.findUserByLoginId(jwtrequest.getLoginId());
            return new ResponseEntity<>(new JwtResponse(jwtrequest.getLoginId(),token, user.getRole()),HttpStatus.OK);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid Username or Password !!");

        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody @Valid User user){
        Matcher matcher = pattern.matcher(user.getEmail());
        try {
            if(user.getPassword().equals(user.getConfirmPassword()) && matcher.matches()) {
                User savedUser = service.saveUser(user);
                return new ResponseEntity<>(new SignUpResponse(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(),
                        savedUser.getEmail(), savedUser.getLoginId(), savedUser.getContactNumber(), savedUser.getRole()), HttpStatus.CREATED);
            }
            else if(!matcher.matches()){
                return new ResponseEntity<>("Invalid Email id !!", HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>("Password and Confirm Password must be same !!", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (DuplicateKeyException e){
            throw new DuplicateKeyException("Duplicate Email or LoginId !!");
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> sendOtp(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest){
        Matcher matcher = pattern.matcher(forgotPasswordRequest.getEmail());
        try {
            if(matcher.matches()) {
                User user = service.findUserByEmail(forgotPasswordRequest.getEmail());
                String otp = service.generateOtp();
                service.saveOtp(otp, user);
                emailService.sendSimpleEmail(forgotPasswordRequest.getEmail(), "Password Reset", "Your verification code is: " + otp);
                return new ResponseEntity<>("{otp:" + otp + "}", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Invalid Email id !!", HttpStatus.BAD_REQUEST);
            }
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("User not found with "+forgotPasswordRequest.getEmail());
        }
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest){
        try {
            User user = service.findUserByOtp(changePasswordRequest.getOtp());
            LocalDateTime otpCreationDate = user.getOtpCreationDate();
            if (service.isOtpExpired(otpCreationDate)) {
                return new ResponseEntity<>("OTP Expired !!", HttpStatus.BAD_REQUEST);
            }
            else {
                service.saveNewPassword(changePasswordRequest.getNewPassword(), user);
                return new ResponseEntity<>("Password changed successfully !!", HttpStatus.OK);
            }
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Wrong OTP entered !!");
        }
    }

    @GetMapping("/users/all")
    public ResponseEntity<?> findALlUsers(Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.findAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/updateuser/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody @Valid UserRequest userRequest, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            return new ResponseEntity<>(service.updateUser(username, userRequest), HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("No user exist with this username !!");
        }
    }

    @DeleteMapping("/deleteuser/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            service.deleteUser(username);
            return new ResponseEntity<>("Deleted Successfully !!", HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("No user exist with this username !!");
        }
    }

    @GetMapping("/user/count")
    public ResponseEntity<?> getUsersCount(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.getUsersCount(), HttpStatus.OK);
    }
}
