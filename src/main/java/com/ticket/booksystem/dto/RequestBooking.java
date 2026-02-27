package com.ticket.booksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RequestBooking {

    private Long userId;
    private Long ticketId;
    private String categoryName;
    private Integer qty;
    private String idempotencyKey;
}
