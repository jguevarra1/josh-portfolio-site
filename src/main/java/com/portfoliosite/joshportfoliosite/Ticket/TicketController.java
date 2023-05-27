package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

/**
 * Controller class responsible for establishing endpoints and making calls to our service classes
 */
@RestController
public class TicketController
{
    /* The service class to handle tickets */
    private final TicketService ticketService;

    /* The service class to handle sending notifications */
    private final NotifyService notifyService;

    /**
     * Constructor injection for our service classes
     * @param ticketService The service class to handle tickets
     * @param notifyService The service class to handle sending notifications
     */
    @Autowired
    public TicketController(TicketService ticketService, NotifyService notifyService)
    {
        this.ticketService = ticketService;
        this.notifyService = notifyService;
    }

    /**
     * Displays our contact.html web form template
     * @return Returns a ModelAndView, though the Model aspect isn't used
     */
    @GetMapping(value = "/contact")
    public ModelAndView showContactPage()
    {
        return new ModelAndView("contact.html");
    }

    /**
     * Redirects our view to the submit.html page after the contact form is submitted.
     * Once that occurs, the Ticket is added to the database and an email notification is sent.
     * We use @ RequestBody and @ ModelAttribute to bind the fields to our Ticket object's fields.
     *
     * @param ticket The Ticket object to add to the database
     * @return The redirect view after the "Submit" button is pressed in contact.html
     */
    @PostMapping(value = "/submit")
    public RedirectView addTicket(@RequestBody @ModelAttribute Ticket ticket)
    {
        ticket.setDateCreated(LocalDate.now());
        ticketService.addTicket(ticket);
        notifyService.sendMessage(ticket);
        return new RedirectView("/submit.html");
    }
}
