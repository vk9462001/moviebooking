package com.movie.booking.controllers;

import com.movie.booking.models.Screening;
import com.movie.booking.models.ShowTime;
import com.movie.booking.models.Ticket;
import com.movie.booking.request.TicketRequest;
import com.movie.booking.services.ScreeningService;;
import com.movie.booking.services.ShowTimeService;
import com.movie.booking.services.TicketService;
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
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private ShowTimeService showTimeService;

    @PostMapping("/ticket/add")
    public ResponseEntity<?> addTicket(@RequestBody @Valid TicketRequest ticketRequest, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        Screening screening = screeningService.findScreeningById(ticketRequest.getScreeningId());
        ShowTime showTime = showTimeService.findShowTimeById(ticketRequest.getShowTimeId());
        if (ticketService.ticketsAvailableOrNot(showTime, ticketRequest)) {
            showTimeService.updateNoOfBookedTicketsWhenAdd(showTime, ticketRequest.getNoOfTickets());
            showTimeService.updateAvailableTickets(showTime);
            return new ResponseEntity<>(ticketService.addTicket(screening, showTime, ticketRequest), HttpStatus.CREATED);
        } else {
            int availableTickets = showTime.getAvailableTickets();
            return new ResponseEntity<>("Only " + availableTickets + " tickets are available !!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tickets/all/{username}")
    public ResponseEntity<?> getAllTicketsByUsername(@PathVariable String username, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        List<Ticket> ticketList = ticketService.getAllTicketsByUsername(username);
        if(ticketList.size()!=0){
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("No ticket exist for this user !!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tickets/all")
    public ResponseEntity<?> getALLTickets(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @DeleteMapping("/ticket/delete/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable String id, Principal principal){
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        try {
            ticketService.deleteTicket(id);
            return new ResponseEntity<>("Deleted Successfully !!", HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("No ticket exist for this id !!");
        }
    }

    @GetMapping("/ticket/count")
    public ResponseEntity<?> getTicketCount(Principal principal) {
        String currentUser = "";
        try {
            currentUser = principal.getName();
        } catch (NullPointerException e){
            throw new NullPointerException("Login First !!");
        }

        return new ResponseEntity<>(ticketService.getTicketCount(), HttpStatus.OK);
    }
}
