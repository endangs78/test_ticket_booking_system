package com.ticket.booksystem.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "concerts")
@Data
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String artist;
    private String venue;

    @Column(name = "concert_time")
    private OffsetDateTime concertTime;

    private String timezone;
    private Integer capacity;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    private String status;
}