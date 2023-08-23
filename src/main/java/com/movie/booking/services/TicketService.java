package com.movie.booking.services;

import com.movie.booking.models.Screening;
import com.movie.booking.models.ShowTime;
import com.movie.booking.models.Ticket;
import com.movie.booking.repository.TicketRepository;
import com.movie.booking.request.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private ShowTimeService showTimeService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

    public Ticket addTicket(Screening screening, ShowTime showTime, TicketRequest ticketRequest){
            String currentDate = simpleDateFormat.format(new Date());
            Ticket ticket = new Ticket(ticketRequest.getId(), screening, showTime, currentDate, ticketRequest.getNoOfTickets(),
                    calculateTotalPrice(screening, ticketRequest), ticketRequest.getUsername());
            return repository.save(ticket);
    }

    public double calculateTotalPrice(Screening screening, TicketRequest ticketRequest){
        return ticketRequest.getNoOfTickets()*screening.getPrice();
    }

    public boolean ticketsAvailableOrNot(ShowTime showTime, TicketRequest ticketRequest){
        int availableTickets = showTime.getAvailableTickets();
        if(availableTickets>=ticketRequest.getNoOfTickets())
            return true;
        else
            return false;
    }

    public Ticket findTicketById(String id){
        return repository.findById(id).get();
    }

    public List<Ticket> getAllTicketsByUsername(String username){
        return repository.findAllByUsername(username);
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public long getTicketCount() {return repository.count();}

    public void deleteTicket(String id){
        Ticket ticket = findTicketById(id);
        showTimeService.updateNoOfBookedTicketsWhenDelete(ticket.getShowTime(),ticket.getNoOfTickets());
        showTimeService.updateAvailableTickets(ticket.getShowTime());
        repository.delete(ticket);
    }
}
