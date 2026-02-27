package com.ticket.booksystem.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ticket.booksystem.domain.TicketCategory;
import com.ticket.booksystem.repository.TicketCategoryRepository;
import com.ticket.booksystem.service.PricingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final TicketCategoryRepository repo;

    public Map<String, BigDecimal> getPricing(Long concertId) {

        List<TicketCategory> list = repo.findByConcertId(concertId);

        Map<String, BigDecimal> result = new HashMap<>();

        for (TicketCategory t : list) {

            BigDecimal demand = BigDecimal.valueOf(
                    1.0 - ((double) t.getAvailableStock()
                            / t.getTotalStock()));

            BigDecimal multiplier = BigDecimal.ONE.add(demand);

            BigDecimal price = t.getConcert().getBasePrice()
                    .multiply(multiplier)
                    .multiply(t.getPriceMultiplier());

            result.put(t.getCategoryName(), price);
        }

        return result;
    }
}
