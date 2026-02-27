package com.ticket.booksystem.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticket.booksystem.domain.Concert;
import com.ticket.booksystem.repository.ConcertRepository;
import com.ticket.booksystem.service.ConcertService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository repo;

    public List<Concert> getAll() {
        return repo.findAll();
    }

    public Concert getById(Long id) {
        return repo.findById(id)
                .orElseThrow();
    }

    public Concert create(Concert c) {
        return repo.save(c);
    }

    public Concert update(Long id, Concert req) {
        Concert c = getById(id);
        c.setName(req.getName());
        c.setArtist(req.getArtist());
        c.setVenue(req.getVenue());
        c.setBasePrice(req.getBasePrice());
        return repo.save(c);
    }

}
