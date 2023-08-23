package com.movie.booking.services;

import com.movie.booking.models.User;
import com.movie.booking.repository.UserRepository;
import com.movie.booking.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    Random random = new Random();

    private static final long EXPIRE_OTP_AFTER_MINUTES = 10;

    public User saveUser(User user){
        String role = "";
        if(user.getRole()==null){
            role="ROLE_USER";
        }
        else{
            role=user.getRole();
        }
        User newUser = new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getLoginId(),
                encoder.encode(user.getPassword()), encoder.encode(user.getConfirmPassword()), user.getContactNumber(), role, null, null);
        return repo.save(newUser);
    }

    public User findUserByEmail(String email){
        return repo.findByEmail(email).get();
    }

    public User findUserByLoginId(String username) {
        return repo.findByLoginId(username).get();
    }

    public String generateOtp(){
        int otp = random.nextInt(9999-1000)+1000;
        return Integer.toString(otp);
    }

    public void saveOtp(String otp, User user){
        user.setOtp(otp);
        user.setOtpCreationDate(LocalDateTime.now());
        repo.save(user);
    }

    public List<User> findAllUsers(){
        return repo.findAll();
    }

    public User findUserByOtp(String otp){
        return repo.findByOtp(otp).get();
    }

    public boolean isOtpExpired(final LocalDateTime otpCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(otpCreationDate, now);

        return diff.toMinutes() >= EXPIRE_OTP_AFTER_MINUTES;
    }

    public void saveNewPassword(String newPassword, User user){
        user.setPassword(encoder.encode(newPassword));
        user.setConfirmPassword(encoder.encode(newPassword));
        user.setOtp(null);
        user.setOtpCreationDate(null);
        repo.save(user);
    }

    public User updateUser(String username, UserRequest userRequest){
        User userToBeUpdated = findUserByLoginId(username);
        userToBeUpdated.setFirstName(userRequest.getFirstName());
        userToBeUpdated.setLastName(userRequest.getLastName());
        userToBeUpdated.setEmail(userRequest.getEmail());
        userToBeUpdated.setContactNumber(userRequest.getContactNumber());
        return repo.save(userToBeUpdated);
    }

    public void deleteUser(String username){
        User user = findUserByLoginId(username);
        repo.delete(user);
    }

    public long getUsersCount() {
        return repo.count();
    }
}
