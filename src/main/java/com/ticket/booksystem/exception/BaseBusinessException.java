package com.ticket.booksystem.exception;

import lombok.Getter;

@Getter
public abstract class BaseBusinessException extends RuntimeException {

    private final String code;
    private final int status;

    protected BaseBusinessException(String code, String message, int status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
