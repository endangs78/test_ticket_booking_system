package com.ticket.booksystem.service;

import java.math.BigDecimal;
import java.util.Map;

public interface PricingService {

    public Map<String, BigDecimal> getPricing(Long concertId);
}
