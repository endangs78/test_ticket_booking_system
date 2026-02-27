package com.ticket.booksystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.booksystem.domain.Transaction;
import com.ticket.booksystem.service.SettlementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyticsController {
    private final SettlementService service;

    @GetMapping("/transactions")
    public List<Transaction> transactions() {
        return service.transactions();
    }

    @GetMapping("/analytics/dashboard")
    public Map<String, Object> dashboard() {
        return service.dashboard();
    }
}
