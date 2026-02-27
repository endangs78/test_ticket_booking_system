package com.ticket.booksystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ticket.booksystem.domain.Booking;
import com.ticket.booksystem.domain.Concert;
import com.ticket.booksystem.domain.TicketCategory;
import com.ticket.booksystem.dto.RequestBooking;
import com.ticket.booksystem.exception.DuplicateBookingException;
import com.ticket.booksystem.exception.SoldOutException;
import com.ticket.booksystem.repository.BookingRepository;
import com.ticket.booksystem.repository.TicketCategoryRepository;
import com.ticket.booksystem.serviceimpl.BookingServiceImpl;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private TicketCategoryRepository ticketRepo;

    @Mock
    private BookingRepository bookingRepo;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private RequestBooking requestBooking;
    private TicketCategory category;
    private Concert concert;

    @BeforeEach
    void setUp() {

        concert = new Concert();
        concert.setId(1L);
        concert.setBasePrice(BigDecimal.valueOf(1000000));

        category = new TicketCategory();
        category.setId(10L);
        category.setConcert(concert);
        category.setAvailableStock(10);
        category.setPriceMultiplier(BigDecimal.valueOf(2.0));

        requestBooking = new RequestBooking();
        requestBooking.setUserId(100L);
        requestBooking.setTicketId(1L);
        requestBooking.setCategoryName("VIP");
        requestBooking.setQty(2);
        requestBooking.setIdempotencyKey("idem-123");
    }

    // SUCCESS BOOKING
    @Test
    void createBooking_success() {

        when(bookingRepo.findByIdempotencyKey("idem-123"))
                .thenReturn(Optional.empty());

        when(ticketRepo.findByConcertIdForUpdate(1L, "VIP"))
                .thenReturn(Optional.of(category));

        when(bookingRepo.save(any(Booking.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Booking result = bookingService.createBooking(requestBooking);

        assertNotNull(result);
        assertEquals("CONFIRMED", result.getStatus());
        assertEquals(0, result.getTotalAmount().compareTo(BigDecimal.valueOf(4000000)));
        assertEquals(8, category.getAvailableStock());

        verify(ticketRepo).findByConcertIdForUpdate(1L, "VIP");
        verify(bookingRepo).save(any(Booking.class));
    }

    // DUPLICATE BOOKING
    @Test
    void createBooking_duplicate_shouldThrowException() {

        when(bookingRepo.findByIdempotencyKey("idem-123"))
                .thenReturn(Optional.of(new Booking()));

        assertThrows(DuplicateBookingException.class, () -> bookingService.createBooking(requestBooking));

        verify(bookingRepo, never()).save(any());
    }

    // SOLD OUT
    @Test
    void createBooking_soldOut_shouldThrowException() {

        category.setAvailableStock(1);

        when(bookingRepo.findByIdempotencyKey("idem-123"))
                .thenReturn(Optional.empty());

        when(ticketRepo.findByConcertIdForUpdate(1L, "VIP"))
                .thenReturn(Optional.of(category));

        assertThrows(SoldOutException.class, () -> bookingService.createBooking(requestBooking));

        verify(bookingRepo, never()).save(any());
    }

    // GET BOOKING
    @Test
    void getBooking_success() {

        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepo.findById(1L))
                .thenReturn(Optional.of(booking));

        Booking result = bookingService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    // GET USER BOOKINGS
    @Test
    void getUser_success() {

        when(bookingRepo.findByUserId(100L))
                .thenReturn(List.of(new Booking(), new Booking()));

        List<Booking> result = bookingService.getUser(100L);

        assertEquals(2, result.size());
    }

    // CANCEL BOOKING
    @Test
    void cancel_success() {

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus("CONFIRMED");

        when(bookingRepo.findById(1L))
                .thenReturn(Optional.of(booking));

        bookingService.cancel(1L);

        assertEquals("CANCELLED", booking.getStatus());
    }
}