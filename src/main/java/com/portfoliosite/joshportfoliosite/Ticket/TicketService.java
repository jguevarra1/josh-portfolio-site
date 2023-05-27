package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The Service class containing the implementations for Create, Read, and Delete operations.
 */
@Service
public class TicketService
{
    /** The PSQL database to interact with this website */
    private final TicketRepo ticketRepo;

    /**
     * Inject our ticket repo into this service class
     * @param ticketRepo The PSQL database to interact with this website
     */
    @Autowired
    public TicketService(TicketRepo ticketRepo)
    {
        this.ticketRepo = ticketRepo;
    }

    /**
     * Adds a new single ticket to the database
     * @param ticket The ticket to be added
     */
    public void addTicket(Ticket ticket)
    {
        ticketRepo.save(ticket);
    }

    /**
     * Adds a list of tickets to the database
     * @param tickets The list of tickets to be added
     */
    public void addTicket(List<Ticket> tickets)
    {
        ticketRepo.saveAll(tickets);
    }

    /**
     * Finds a ticket with of a specific ID or throws an exception if the ID is invalid or does not exist
     * @param id The id to search against the database
     * @return Returns a ticket that corresponds to a given ID
     */
    public Ticket findTicket(Long id)
    {
        if (id <= 0)
        {
            throw new IllegalArgumentException("The ID " + id + "is invalid!");
        }

        Optional<Ticket> ticket = ticketRepo.findById(id);

        if (!ticket.isPresent())
        {
            throw new NoSuchElementException("The ID " + id + " does not exist in the database!");
        }

        return ticket.get();
    }

    /**
     * Finds all tickets in a database that correspond with a given email or
     * throws an exception if the email entered is invalid
     *
     * @param email The email to search against the database
     * @return Returns an unmodifiable List of all tickets found in the database that correspond with the given email
     */
    public List<Ticket> findTicket(String email)
    {
        if (email == null || email.isBlank() || email.isEmpty())
        {
            throw new IllegalArgumentException("The email + " + email + " is invalid!");
        }

        List<Ticket> tickets = ticketRepo.findTicketByEmail(email);

        if (tickets.isEmpty())
        {
            throw new NoSuchElementException("The email" + email + " does not exist in the database!");
        }

        return Collections.unmodifiableList(tickets);
    }

    /**
     * Finds all tickets that correspond with a given date, or throws an exception the date entered is invalid
     * @param date The date to search against the database
     * @return Returns an unmodifiable List of all tickets found that correspond with the given date
     */
    public List<Ticket> findTicket(LocalDate date)
    {
        LocalDate dateProjectCreated = LocalDate.of(2023, 5, 10);
        LocalDate today = LocalDate.now();

       if (date.isBefore(dateProjectCreated))
       {
           throw new IllegalArgumentException("The date" + date + " is an earlier date than when this website was created!");
       }

       if (date.isAfter(today))
       {
           throw new IllegalArgumentException("The date + " + date + " is a future date!");
       }

       List<Ticket> tickets = ticketRepo.findTicketByDate(date);

       if (tickets.isEmpty())
       {
           throw new NoSuchElementException("There were no tickets found with the date " + date + " .");
       }

       return Collections.unmodifiableList(tickets);
    }

    /**
     * Finds and returns an unmodifiable view of the list of tickets in the database
     * or throws an exception if the database is empty.
     * @return Returns an unmodifiable list of all tickets in the database
     */
    public List<Ticket> findAll()
    {
        List<Ticket> ticket = ticketRepo.findAll();

        if (ticket.isEmpty())
        {
            throw new NoSuchElementException("The database is empty.");
        }

        return Collections.unmodifiableList(ticket);
    }

    /**
     * Deletes a ticket from the database that corresponds with the given ID
     * @param id The id to search against the database
     */
    public void deleteTicket(Long id)
    {
        Ticket ticketToDelete = findTicket(id);
        ticketRepo.delete(ticketToDelete);
    }

    /**
     * Deletes all tickets from the database that correspond with a given email
     * @param email The email to search against the database
     */
    public void deleteTicket(String email)
    {
        List<Ticket> ticketsToDelete = findTicket(email);
        ticketRepo.deleteAll(ticketsToDelete);
    }

    /**
     * Deletes all tickets from the database that correspond with a given date
     * @param date The date to search against the database
     */
    public void deleteTicket(LocalDate date)
    {
        List<Ticket> ticketsToDelete = findTicket(date);
        ticketRepo.deleteAll(ticketsToDelete);
    }
}
