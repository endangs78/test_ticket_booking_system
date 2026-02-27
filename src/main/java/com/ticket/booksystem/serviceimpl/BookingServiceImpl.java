package com.ticket.booksystem.serviceimpl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ticket.booksystem.domain.Booking;
import com.ticket.booksystem.domain.TicketCategory;
import com.ticket.booksystem.dto.RequestBooking;
import com.ticket.booksystem.exception.DuplicateBookingException;
import com.ticket.booksystem.exception.SoldOutException;
import com.ticket.booksystem.repository.BookingRepository;
import com.ticket.booksystem.repository.TicketCategoryRepository;
import com.ticket.booksystem.service.BookingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final TicketCategoryRepository ticketRepo;
    private final BookingRepository bookingRepo;

    @Transactional
    public Booking createBooking(RequestBooking requestBooking) {

        bookingRepo.findByIdempotencyKey(requestBooking.getIdempotencyKey())
                .ifPresent(b -> {
                    throw new DuplicateBookingException();
                });

        TicketCategory category = ticketRepo
                .findByConcertIdForUpdate(requestBooking.getTicketId(), requestBooking.getCategoryName())
                .orElseThrow();

        if (category.getAvailableStock() < requestBooking.getQty())
            throw new SoldOutException();

        category.setAvailableStock(
                category.getAvailableStock() - requestBooking.getQty());

        BigDecimal price = category.getConcert()
                .getBasePrice()
                .multiply(category.getPriceMultiplier());

        Booking booking = new Booking();
        booking.setUserId(requestBooking.getUserId());
        booking.setConcert(category.getConcert());
        booking.setStatus("CONFIRMED");
        booking.setIdempotencyKey(requestBooking.getIdempotencyKey());
        booking.setExpiresAt(
                OffsetDateTime.now().plusMinutes(5));
        booking.setTotalAmount(
                price.multiply(BigDecimal.valueOf(requestBooking.getQty())));

        return bookingRepo.save(booking);
    }

    public Booking get(Long id) {
        return bookingRepo.findById(id).orElseThrow();
    }

    public List<Booking> getUser(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    @Transactional
    public void cancel(Long id) {
        Booking b = get(id);
        b.setStatus("CANCELLED");
    }
}
