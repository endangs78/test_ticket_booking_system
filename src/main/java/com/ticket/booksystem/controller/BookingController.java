package com.ticket.booksystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.booksystem.domain.Booking;
import com.ticket.booksystem.dto.RequestBooking;
import com.ticket.booksystem.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @PostMapping
    public Booking create(@RequestBody RequestBooking requestBooking) {
        return service.createBooking(requestBooking);
    }

    @GetMapping("/{id}")
    public Booking get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<Booking> user(@RequestParam Long userId) {
        return service.getUser(userId);
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        service.cancel(id);
    }
}
