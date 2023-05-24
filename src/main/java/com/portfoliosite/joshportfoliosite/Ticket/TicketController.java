package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TicketController
{
    // TODO set up the controller code

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService)
    {
        this.ticketService = ticketService;
    }


}
