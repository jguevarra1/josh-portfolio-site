package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@RestController
public class TicketController
{
    // TODO set up the controller code

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService)
    {
        this.ticketService = ticketService;
    }

    @GetMapping(value = "/contact")
    public ModelAndView showContactPage()
    {
        ModelAndView mav = new ModelAndView("contact.html");
        Ticket ticket = new Ticket();
        mav.addObject("ticket", ticket);
        return mav;
    }
    @PostMapping(value = "/submit")
    public RedirectView addTicket(@RequestBody @ModelAttribute Ticket ticket)
    {
        ticket.setDateCreated(LocalDate.now());
        ticketService.addTicket(ticket);
        return new RedirectView("/submit.html");
    }

}
