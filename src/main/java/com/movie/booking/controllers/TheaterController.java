package com.movie.booking.controllers;

import com.movie.booking.models.Theater;
import com.movie.booking.services.TheaterService;
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
public class TheaterController {

    @Autowired
    private TheaterService service;

    @PostMapping("/theater/add")
    public ResponseEntity<?> addTheater(@RequestBody @Valid Theater theater, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.addTheater(theater), HttpStatus.CREATED);
    }

    @GetMapping("/theater/{id}")
    public ResponseEntity<?> getTheaterById(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            return new ResponseEntity<>(service.findTheaterById(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No screening exist for this id !!");
        }
    }

    @GetMapping("/theaters/all")
    public ResponseEntity<?> getAllTheaters(Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.findAllTheaters(),HttpStatus.OK);
    }

    @PutMapping("/theater/update/{id}")
    public ResponseEntity<?> updateTheaterData(@PathVariable String id, @RequestBody @Valid  Theater theater, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            return new ResponseEntity<>(service.updateTheaterData(id, theater), HttpStatus.CREATED);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No theater exist for this id !!");
        }
    }

    @DeleteMapping("/theater/delete/{id}")
    public ResponseEntity<?> deleteTheater(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            service.deleteTheater(id);
            return new ResponseEntity<>("Deleted Successfully !!", HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No theater exist for this id !!");
        }
    }

    @GetMapping("/theater/count")
    public ResponseEntity<?> getTheaterCount(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.getTheaterCount(), HttpStatus.OK);
    }
}
