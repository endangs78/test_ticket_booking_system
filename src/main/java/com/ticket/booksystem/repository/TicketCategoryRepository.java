package com.ticket.booksystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ticket.booksystem.domain.TicketCategory;

import jakarta.persistence.LockModeType;

@Repository
public interface TicketCategoryRepository
        extends JpaRepository<TicketCategory, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM TicketCategory t WHERE t.id = :id")
    Optional<TicketCategory> findByIdForUpdate(Long id);

    List<TicketCategory> findByConcertId(Long concertId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM TicketCategory t WHERE t.concert.id = :concertId and t.categoryName = :categoryName")
    Optional<TicketCategory> findByConcertIdForUpdate(@Param("concertId") Long concertId,
            @Param("categoryName") String categoryName);
}
