package com.movie.booking.repository;

import com.movie.booking.models.Screening;
import com.movie.booking.models.Theater;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends MongoRepository<Screening, String> {

    List<Screening> findAllByMovieName(String movieName);

    Screening findByTheater(Theater theater);
}
