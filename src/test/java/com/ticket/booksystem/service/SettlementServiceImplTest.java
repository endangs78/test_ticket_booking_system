package com.ticket.booksystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ticket.booksystem.domain.Booking;
import com.ticket.booksystem.domain.Concert;
import com.ticket.booksystem.domain.Transaction;
import com.ticket.booksystem.repository.BookingRepository;
import com.ticket.booksystem.repository.TransactionRepository;
import com.ticket.booksystem.serviceimpl.SettlementServiceImpl;

@ExtendWith(MockitoExtension.class)
class SettlementServiceImplTest {

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private TransactionRepository trxRepo;

    @InjectMocks
    private SettlementServiceImpl service;

    private Booking booking1;
    private Booking booking2;
    private Concert concert1;
    private Concert concert2;

    @BeforeEach
    void setup() {

        concert1 = new Concert();
        concert1.setId(1L);

        concert2 = new Concert();
        concert2.setId(2L);

        booking1 = new Booking();
        booking1.setConcert(concert1);
        booking1.setTotalAmount(BigDecimal.valueOf(1_000_000));

        booking2 = new Booking();
        booking2.setConcert(concert1);
        booking2.setTotalAmount(BigDecimal.valueOf(2_000_000));
    }

    // SETTLEMENT SUCCESS
    @Test
    void settlement_success() {

        when(bookingRepo.findAll())
                .thenReturn(List.of(booking1, booking2));

        Map<String, Object> result = service.settlement(1L);

        assertEquals(2, result.get("totalBookings"));

        BigDecimal revenue = (BigDecimal) result.get("revenue");

        assertEquals(0,
                revenue.compareTo(BigDecimal.valueOf(3_000_000)));

        verify(bookingRepo).findAll();
    }

    // SETTLEMENT EMPTY
    @Test
    void settlement_noBookings() {

        when(bookingRepo.findAll())
                .thenReturn(List.of());

        Map<String, Object> result = service.settlement(1L);

        assertEquals(0, result.get("totalBookings"));

        BigDecimal revenue = (BigDecimal) result.get("revenue");

        assertEquals(0,
                revenue.compareTo(BigDecimal.ZERO));
    }

    // TRANSACTIONS
    @Test
    void transactions_success() {

        when(trxRepo.findAll())
                .thenReturn(List.of(new Transaction(), new Transaction()));

        List<Transaction> result = service.transactions();

        assertEquals(2, result.size());

        verify(trxRepo).findAll();
    }

    // DASHBOARD SUCCESS
    @Test
    void dashboard_success() {

        when(bookingRepo.findAll())
                .thenReturn(List.of(booking1, booking2));

        when(bookingRepo.count())
                .thenReturn(2L);

        Map<String, Object> result = service.dashboard();

        BigDecimal totalRevenue = (BigDecimal) result.get("totalRevenue");

        assertEquals(0,
                totalRevenue.compareTo(BigDecimal.valueOf(3_000_000)));

        assertEquals(2L, result.get("totalBookings"));

        verify(bookingRepo).count();
    }
}