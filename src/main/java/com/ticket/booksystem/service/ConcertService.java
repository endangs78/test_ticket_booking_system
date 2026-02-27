package com.ticket.booksystem.service;

import java.util.List;

import com.ticket.booksystem.domain.Concert;

public interface ConcertService {

    public List<Concert> getAll();

    public Concert getById(Long id);

    public Concert create(Concert c);

    public Concert update(Long id, Concert req);
}
