package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface responsible for initializing our PSQL Database using Spring Data JPA
 */
@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long>
{
    /* Custom query to find the list of tickets that correspond to a given email address */
    @Query("SELECT t FROM Ticket t WHERE t.email = :email")
    public List<Ticket> findTicketByEmail(@Param("email") String email);

    /* Custom query to find the list of tickets that correspond to a given date */
    @Query("SELECT t FROM Ticket t WHERE t.dateCreated = :dateCreated")
    public List<Ticket> findTicketByDate(@Param("dateCreated") LocalDate date);

}
