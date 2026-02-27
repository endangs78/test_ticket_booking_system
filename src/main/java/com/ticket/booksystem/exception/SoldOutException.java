package com.ticket.booksystem.exception;

public class SoldOutException extends BaseBusinessException {

    public SoldOutException() {
        super(
                "TICKET_SOLD_OUT",
                "Ticket is sold out",
                400);
    }
}
