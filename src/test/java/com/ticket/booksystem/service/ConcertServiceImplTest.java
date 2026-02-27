package com.ticket.booksystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ticket.booksystem.domain.Concert;
import com.ticket.booksystem.repository.ConcertRepository;
import com.ticket.booksystem.serviceimpl.ConcertServiceImpl;

@ExtendWith(MockitoExtension.class)
class ConcertServiceImplTest {

    @Mock
    private ConcertRepository repo;

    @InjectMocks
    private ConcertServiceImpl service;

    private Concert concert;

    @BeforeEach
    void setUp() {
        concert = new Concert();
        concert.setId(1L);
        concert.setName("Coldplay Live");
        concert.setArtist("Coldplay");
        concert.setVenue("GBK Stadium");
        concert.setBasePrice(BigDecimal.valueOf(1000000));
    }

    // GET ALL
    @Test
    void getAll_success() {

        when(repo.findAll())
                .thenReturn(List.of(concert));

        List<Concert> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Coldplay Live", result.get(0).getName());

        verify(repo).findAll();
    }

    // GET BY ID SUCCESS
    @Test
    void getById_success() {

        when(repo.findById(1L))
                .thenReturn(Optional.of(concert));

        Concert result = service.getById(1L);

        assertNotNull(result);
        assertEquals("Coldplay", result.getArtist());

        verify(repo).findById(1L);
    }

    // GET BY ID NOT FOUND
    @Test
    void getById_notFound_shouldThrowException() {

        when(repo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getById(1L));
    }

    // CREATE
    @Test
    void create_success() {

        when(repo.save(concert))
                .thenReturn(concert);

        Concert result = service.create(concert);

        assertNotNull(result);
        assertEquals("Coldplay Live", result.getName());

        verify(repo).save(concert);
    }

    // UPDATE SUCCESS
    @Test
    void update_success() {

        Concert updatedReq = new Concert();
        updatedReq.setName("Bruno Mars Live");
        updatedReq.setArtist("Bruno Mars");
        updatedReq.setVenue("JIS Stadium");
        updatedReq.setBasePrice(BigDecimal.valueOf(2000000));

        when(repo.findById(1L))
                .thenReturn(Optional.of(concert));

        when(repo.save(any(Concert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Concert result = service.update(1L, updatedReq);

        assertEquals("Bruno Mars Live", result.getName());
        assertEquals("Bruno Mars", result.getArtist());
        assertEquals("JIS Stadium", result.getVenue());
        assertEquals(0, result.getBasePrice()
                .compareTo(BigDecimal.valueOf(2000000)));

        verify(repo).save(concert);
    }

    // UPDATE NOT FOUND
    @Test
    void update_notFound_shouldThrowException() {

        when(repo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.update(1L, new Concert()));
    }
}