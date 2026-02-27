package com.ticket.booksystem.service;

import java.util.List;

import com.ticket.booksystem.domain.Booking;
import com.ticket.booksystem.dto.RequestBooking;

public interface BookingService {

    public Booking createBooking(RequestBooking requestBooking);

    public Booking get(Long id);

    public List<Booking> getUser(Long userId);

    public void cancel(Long id);

}
