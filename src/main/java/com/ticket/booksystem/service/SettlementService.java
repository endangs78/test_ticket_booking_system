package com.ticket.booksystem.service;

import java.util.List;
import java.util.Map;

import com.ticket.booksystem.domain.Transaction;

public interface SettlementService {

    public Map<String, Object> settlement(Long concertId);

    public List<Transaction> transactions();

    public Map<String, Object> dashboard();
}
