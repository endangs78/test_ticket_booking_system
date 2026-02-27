package com.ticket.booksystem.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ticket.booksystem.domain.Booking;
import com.ticket.booksystem.domain.Transaction;
import com.ticket.booksystem.repository.BookingRepository;
import com.ticket.booksystem.repository.TransactionRepository;
import com.ticket.booksystem.service.SettlementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final BookingRepository bookingRepo;
    private final TransactionRepository trxRepo;

    public Map<String, Object> settlement(Long concertId) {

        List<Booking> bookings = bookingRepo.findAll()
                .stream()
                .filter(b -> b.getConcert().getId().equals(concertId))
                .toList();

        BigDecimal revenue = bookings.stream()
                .map(Booking::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("totalBookings", bookings.size());
        result.put("revenue", revenue);

        return result;
    }

    public List<Transaction> transactions() {
        return trxRepo.findAll();
    }

    public Map<String, Object> dashboard() {

        BigDecimal totalRevenue = bookingRepo.findAll()
                .stream()
                .map(Booking::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("totalRevenue", totalRevenue);
        result.put("totalBookings", bookingRepo.count());

        return result;
    }
}
