package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TicketService
{
    // TODO finish all the business logic classes here
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo)
    {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> findAll()
    {
        return Collections.unmodifiableList(ticketRepo.findAll());
    }

    public void addTicket(Ticket ticket)
    {
        ticketRepo.save(ticket);
    }

}
