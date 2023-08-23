package com.movie.booking.controllers;

import com.movie.booking.request.ScreeningRequest;
import com.movie.booking.services.ScreeningService;
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
public class ScreeningController {

    @Autowired
    private ScreeningService service;

    @PostMapping("/screening/add")
    public ResponseEntity<?> addScreening(@RequestBody @Valid ScreeningRequest screeningRequest, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.addScreening(screeningRequest), HttpStatus.CREATED);
    }

    @GetMapping("/screenings/all")
    public ResponseEntity<?> getAllScreenings(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.findAllScreenings(), HttpStatus.OK);
    }

    @GetMapping("/screening/{id}")
    public ResponseEntity<?> getScreeningById(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            return new ResponseEntity<>(service.findScreeningById(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No screening exist for this id !!");
        }
    }

    @PutMapping("/screening/update/{id}")
    public ResponseEntity<?> updateScreening(@PathVariable String id, @RequestBody @Valid ScreeningRequest screeningRequest, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            return new ResponseEntity<>(service.updateScreeningData(id, screeningRequest), HttpStatus.CREATED);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No screening exist for this id !!");
        }
    }

    @DeleteMapping("/screening/delete/{id}")
    public ResponseEntity<?> deleteScreening(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            service.deleteScreening(id);
            return new ResponseEntity<>("Deleted Successfully !!", HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No screening exist for this id !!");
        }
    }

    @GetMapping("/screening/count")
    public ResponseEntity<?> getScreeningCount(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.getScreeningCount(), HttpStatus.OK);
    }
}
