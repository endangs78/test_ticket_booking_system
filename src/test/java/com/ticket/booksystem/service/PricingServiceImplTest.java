package com.ticket.booksystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.ticket.booksystem.domain.Concert;
import com.ticket.booksystem.domain.TicketCategory;
import com.ticket.booksystem.repository.TicketCategoryRepository;
import com.ticket.booksystem.serviceimpl.PricingServiceImpl;

@ExtendWith(MockitoExtension.class)
class PricingServiceImplTest {

    @Mock
    private TicketCategoryRepository repo;

    @InjectMocks
    private PricingServiceImpl service;

    private TicketCategory vipCategory;
    private Concert concert;

    @BeforeEach
    void setup() {

        concert = new Concert();
        concert.setId(1L);
        concert.setBasePrice(BigDecimal.valueOf(1_000_000));

        vipCategory = new TicketCategory();
        vipCategory.setCategoryName("VIP");
        vipCategory.setConcert(concert);
        vipCategory.setTotalStock(100);
        vipCategory.setAvailableStock(50); // 50% sold
        vipCategory.setPriceMultiplier(BigDecimal.valueOf(2));
    }

    // SUCCESS TEST
    @Test
    void getPricing_success() {

        when(repo.findByConcertId(1L))
                .thenReturn(List.of(vipCategory));

        Map<String, BigDecimal> result = service.getPricing(1L);

        assertNotNull(result);
        assertTrue(result.containsKey("VIP"));

        BigDecimal price = result.get("VIP");

        /*
         * demand = 1 - (50/100) = 0.5
         * multiplier = 1 + 0.5 = 1.5
         * price = 1.000.000 × 1.5 × 2
         * = 3.000.000
         */

        assertEquals(0,
                price.compareTo(BigDecimal.valueOf(3_000_000)));

        verify(repo).findByConcertId(1L);
    }

    // EMPTY CATEGORY
    @Test
    void getPricing_emptyList() {

        when(repo.findByConcertId(1L))
                .thenReturn(List.of());

        Map<String, BigDecimal> result = service.getPricing(1L);

        assertTrue(result.isEmpty());
    }
}