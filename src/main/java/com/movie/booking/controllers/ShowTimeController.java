package com.movie.booking.controllers;

import com.movie.booking.request.ShowTimeRequest;
import com.movie.booking.services.ShowTimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
@CrossOrigin(origins = {"*"})
public class ShowTimeController {

    @Autowired
    private ShowTimeService showTimeService;

    @PostMapping("/showtime/add")
    public ResponseEntity<?> addShowTime(@RequestBody @Valid ShowTimeRequest showTimeRequest, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(showTimeService.addShowTime(showTimeRequest), HttpStatus.CREATED);
    }

    @GetMapping("/showtime/all")
    public ResponseEntity<?> getAllShowtime(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(showTimeService.findAllShowtime(), HttpStatus.OK);
    }

    @GetMapping("/showtime/{id}")
    public ResponseEntity<?> getShowTimeById(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            return new ResponseEntity<>(showTimeService.findShowTimeById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No show time exist for this is id !!");
        }
    }

    @DeleteMapping("/showtime/delete/{id}")
    public ResponseEntity<?> deleteShowTime(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            showTimeService.deleteShowTime(id);
            return new ResponseEntity<>("Deleted Successfully !!", HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No show time exist for this id !!");
        }
    }

    @GetMapping("/showtime/count")
    public ResponseEntity<?> getShowTimeCount(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(showTimeService.getShowTimeCount(), HttpStatus.OK);
    }
}
