package com.ticket.booksystem.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.booksystem.domain.Concert;
import com.ticket.booksystem.domain.TicketCategory;
import com.ticket.booksystem.repository.TicketCategoryRepository;
import com.ticket.booksystem.service.ConcertService;
import com.ticket.booksystem.service.PricingService;
import com.ticket.booksystem.service.SettlementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertsController {

    private final ConcertService service;
    private final PricingService pricingService;
    private final TicketCategoryRepository ticketRepo;
    private final SettlementService settlementService;

    @GetMapping
    public List<Concert> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Concert get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Concert create(@RequestBody Concert c) {
        return service.create(c);
    }

    @PutMapping("/{id}")
    public Concert update(@PathVariable Long id,
            @RequestBody Concert c) {
        return service.update(id, c);
    }

    @GetMapping("/{id}/pricing")
    public Map<String, BigDecimal> pricing(@PathVariable Long id) {
        return pricingService.getPricing(id);
    }

    @GetMapping("/{id}/availability")
    public List<TicketCategory> availability(@PathVariable Long id) {
        return ticketRepo.findByConcertId(id);
    }

    @GetMapping("/{id}/settlement")
    public Map<String, Object> settlement(@PathVariable Long id) {
        return settlementService.settlement(id);
    }
}
