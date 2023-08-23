package com.movie.booking.controllers;

import com.movie.booking.models.Movie;
import com.movie.booking.request.MovieRequest;
import com.movie.booking.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
@CrossOrigin(origins = {"*"})
public class MovieController {

    @Autowired
    private MovieService service;

    @PostMapping("/movie/add")
    public ResponseEntity<?> addMovie(@RequestBody @Valid MovieRequest movieRequest, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.addMovie(movieRequest), HttpStatus.CREATED);
    }

    @GetMapping("/movies/search/{title}")
    public ResponseEntity<?> searchMovie(@PathVariable String title){
        List<Movie> movies = service.searchMovie(title);
        if(movies.size()!=0) {
            return new ResponseEntity<>(movies, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("No movie found with this title !!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/movies/all")
    public ResponseEntity<?> getAllMovies(){
        return new ResponseEntity<>(service.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable String id){
        try{
            return new ResponseEntity<>(service.findMovieById(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No movie exist with this id !!");
        }
    }

    @PutMapping("/movie/update/{id}")
    public ResponseEntity<?> updateMovieData(@PathVariable String id, @RequestBody @Valid MovieRequest movieRequest, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            return new ResponseEntity<>(service.updateMovieData(id, movieRequest), HttpStatus.CREATED);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No movie exist with this id !!");
        }
    }

    @DeleteMapping("/movie/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            service.deleteMovie(id);
            return new ResponseEntity<>("Deleted Successfully !!", HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No movie exist with this id !!");
        }
    }

    @GetMapping("/movie/count")
    public ResponseEntity<?> getMovieCount(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(service.getMovieCount(), HttpStatus.OK);
    }
}
