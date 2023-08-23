package com.movie.booking.services;

import com.movie.booking.models.Screening;
import com.movie.booking.models.ShowTime;
import com.movie.booking.repository.ScreeningRepository;
import com.movie.booking.repository.ShowTimeRepository;
import com.movie.booking.request.ShowTimeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ShowTimeService {

    @Autowired
    private ShowTimeRepository showTimeRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
    SimpleDateFormat stf = new SimpleDateFormat("hh:mm");


    public ShowTime addShowTime(ShowTimeRequest showTimeRequest) {

        String screeningTime = "";
        try {
            screeningTime = simpleDateFormat.format(stf.parse(showTimeRequest.getScreeningTime()));

        } catch (ParseException e) {
            throw new RuntimeException("Date or time format is wrong !!");
        }

        Screening screening = screeningRepository.findById(showTimeRequest.getScreeningId()).get();
        String theater = screening.getTheater().getTheaterName() + ", " + screening.getTheater().getTheaterLocation();
        ShowTime showTime = new ShowTime(showTimeRequest.getId(), screening.getMovieName(), screening.getScreeningDate(),
                theater, screeningTime,
                showTimeRequest.getTotalTickets(), 0, showTimeRequest.getTotalTickets(), showTimeRequest.getScreeningId());
        ShowTime savedShowTime = showTimeRepository.save(showTime);

        List<ShowTime> showTimes = screening.getShowTime();
        showTimes.add(savedShowTime);
        screening.setShowTime(showTimes);
        screeningRepository.save(screening);

        return savedShowTime;
    }

    public long getShowTimeCount() {return showTimeRepository.count();}

    public void updateNoOfBookedTicketsWhenAdd(ShowTime showTime, int noOfTickets){
        showTime.setBookedTickets(showTime.getBookedTickets()+noOfTickets);
        showTimeRepository.save(showTime);
    }

    public void updateNoOfBookedTicketsWhenDelete(ShowTime showTime, int noOfTickets){
        showTime.setBookedTickets(showTime.getBookedTickets()-noOfTickets);
        showTimeRepository.save(showTime);
    }

    public void updateAvailableTickets(ShowTime showTime){
        showTime.setAvailableTickets(showTime.getTotalTickets()-showTime.getBookedTickets());
        showTimeRepository.save(showTime);
    }

    public ShowTime findShowTimeById(String id){
        return showTimeRepository.findById(id).get();
    }

    public List<ShowTime> findAllShowtime() {
        return showTimeRepository.findAll();
    }

    public void deleteShowTime(String id){
        ShowTime showTimeToBeDeleted = findShowTimeById(id);
        Screening screeningFromWhichDelete = screeningRepository.findById(showTimeToBeDeleted.getScreeningId()).get();
        List<ShowTime> showTimes1 = screeningFromWhichDelete.getShowTime();
        int index = 0;
        for (int i=0;i<showTimes1.size();i++){
            if(showTimes1.get(i).getId()==id){
                index=i;
                break;
            }
        }
        showTimes1.remove(index);
        screeningFromWhichDelete.setShowTime(showTimes1);
        screeningRepository.save(screeningFromWhichDelete);
        showTimeRepository.delete(showTimeToBeDeleted);
    }
}
