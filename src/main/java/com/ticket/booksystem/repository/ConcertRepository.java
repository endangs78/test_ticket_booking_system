package com.ticket.booksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.booksystem.domain.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
