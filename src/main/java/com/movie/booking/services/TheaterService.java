package com.movie.booking.services;

import com.movie.booking.models.Screening;
import com.movie.booking.models.Theater;
import com.movie.booking.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository repository;

    @Autowired
    private ScreeningService screeningService;

    public Theater addTheater(Theater theater){
        return repository.save(theater);
    }

    public Theater findTheaterById(String id){
        return repository.findById(id).get();
    }

    public List<Theater> findAllTheaters(){
        return  repository.findAll();
    }

    public Theater updateTheaterData(String id, Theater theater){
        Theater theaterToBeUpdated = findTheaterById(id);
        theaterToBeUpdated.setTheaterName(theater.getTheaterName());
        theaterToBeUpdated.setTheaterLocation(theater.getTheaterLocation());
        return repository.save(theaterToBeUpdated);
    }

    public long getTheaterCount() {return repository.count();}

    public void deleteTheater(String id){
        Theater theater = findTheaterById(id);
        Screening screening = screeningService.findByTheater(theater);
        if(screening!=null) {
            screeningService.deleteScreening(screening.getId());
        }
        repository.delete(theater);
    }
}
