package com.movie.booking.services;

import com.movie.booking.models.*;
import com.movie.booking.repository.*;
import com.movie.booking.request.ScreeningRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreeningService {

    @Autowired
    private ScreeningRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Screening addScreening(ScreeningRequest screeningRequest){

        Theater theater = theaterRepository.findById(screeningRequest.getTheaterId()).get();
        List<ShowTime> showTimes = new ArrayList<>();
        Screening screening = new Screening(screeningRequest.getId(),screeningRequest.getMovieName(), screeningRequest.getScreeningDate(), showTimes,
        screeningRequest.getPrice(), theater);
        Screening savedScreening = repository.save(screening);

        Movie movie = movieRepository.findByTitle(screeningRequest.getMovieName()).get();
        List<Screening> screenings = movie.getScreenings();
        screenings.add(savedScreening);
        movie.setScreenings(screenings);
        movieRepository.save(movie);

        return savedScreening;
    }

    public Screening findScreeningById(String id){
        return repository.findById(id).get();
    }

    public List<Screening> findAllScreenings() {
        return repository.findAll();
    }

    public Screening findByTheater(Theater theater) {
        return repository.findByTheater(theater);
    }

    public long getScreeningCount() {return repository.count();}

    public Screening updateScreeningData(String id, ScreeningRequest screeningRequest){

        Screening screeningToBUpdated = findScreeningById(id);
        Theater theater = theaterRepository.findById(screeningRequest.getTheaterId()).get();

        screeningToBUpdated.setMovieName(screeningRequest.getMovieName());
        screeningToBUpdated.setScreeningDate(screeningRequest.getScreeningDate());
        screeningToBUpdated.setPrice(screeningRequest.getPrice());
        screeningToBUpdated.setTheater(theater);

        Movie movie = movieRepository.findByTitle(screeningRequest.getMovieName()).get();
        List<Screening> screenings = movie.getScreenings();
        screenings.add(screeningToBUpdated);
        movie.setScreenings(screenings);
        movieRepository.save(movie);

        return repository.save(screeningToBUpdated);
    }

    public void deleteScreening(String id){
        Screening screeningToBeDeleted = findScreeningById(id);
        Movie movie = movieRepository.findByTitle(screeningToBeDeleted.getMovieName()).get();
        List<Screening> screenings = movie.getScreenings();
        int index = 0;
        for(int i=0;i<screenings.size();i++){
            if(screenings.get(i).getId()==id){
                index=i;
                break;
            }
        }
        screenings.remove(index);
        movie.setScreenings(screenings);
        movieRepository.save(movie);

        List<ShowTime> showTimes = showTimeRepository.findAllByScreeningId(screeningToBeDeleted.getId());
        for (ShowTime showTime:showTimes) {
            showTimeRepository.delete(showTime);
        }

        List<Ticket> ticketList = ticketRepository.findAll();
        for (Ticket ticket:ticketList) {
            if (ticket.getScreening().getId().equals(id)) {
                ticketRepository.delete(ticket);
            }
        }

        repository.delete(screeningToBeDeleted);
    }
}
