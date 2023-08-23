package com.movie.booking.services;

import com.movie.booking.models.Movie;
import com.movie.booking.models.Screening;
import com.movie.booking.models.ShowTime;
import com.movie.booking.repository.MovieRepository;
import com.movie.booking.repository.ScreeningRepository;
import com.movie.booking.repository.ShowTimeRepository;
import com.movie.booking.request.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;


    public Movie addMovie(MovieRequest movieRequest){

        List<Screening> screenings = new ArrayList<>();
        Movie movie = new Movie(movieRequest.getId(), movieRequest.getTitle(), movieRequest.getReleaseDate(),
                movieRequest.getOriginalLanguage(), movieRequest.getOverview(), movieRequest.getCategory(),
                movieRequest.getPoster(), movieRequest.getBackdropPath(), screenings);

        return movieRepository.save(movie);
    }

    public List<Movie> searchMovie(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }

    public long getMovieCount() {return movieRepository.count();}

    public Movie findMovieById(String id){
        return movieRepository.findById(id).get();
    }

    public Movie updateMovieData(String id,MovieRequest movieRequest){

        Movie movieToBeUpdated = findMovieById(id);
        movieToBeUpdated.setTitle(movieRequest.getTitle());
        movieToBeUpdated.setReleaseDate(movieRequest.getReleaseDate());
        movieToBeUpdated.setOriginalLanguage(movieRequest.getOriginalLanguage());
        movieToBeUpdated.setOverview(movieRequest.getOverview());
        movieToBeUpdated.setCategory(movieRequest.getCategory());
        movieToBeUpdated.setPoster(movieRequest.getPoster());
        movieToBeUpdated.setBackdropPath(movieRequest.getBackdropPath());

        return movieRepository.save(movieToBeUpdated);
    }

    public void deleteMovie(String id){
        Movie movie = findMovieById(id);
        List<Screening> screenings = screeningRepository.findAllByMovieName(movie.getTitle());
        for (Screening screening:screenings) {
            List<ShowTime> showTimes = showTimeRepository.findAllByScreeningId(screening.getId());
            for (ShowTime showTime:showTimes) {
                showTimeRepository.delete(showTime);
            }
            screeningRepository.delete(screening);
        }
        movieRepository.delete(movie);
    }
}
