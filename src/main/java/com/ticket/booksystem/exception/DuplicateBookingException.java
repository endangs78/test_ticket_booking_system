package com.ticket.booksystem.exception;

public class DuplicateBookingException extends BaseBusinessException {

    public DuplicateBookingException() {
        super(
                "BOOKING_DUPLICATE",
                "Booking already exists with this idempotency key",
                409);
    }
}
