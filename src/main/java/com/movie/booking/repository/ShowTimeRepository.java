package com.movie.booking.repository;

import com.movie.booking.models.Screening;
import com.movie.booking.models.ShowTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends MongoRepository<ShowTime, String> {

    List<ShowTime> findAllByScreeningId(String id);
}
