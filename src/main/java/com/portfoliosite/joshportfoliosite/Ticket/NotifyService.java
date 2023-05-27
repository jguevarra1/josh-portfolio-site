package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The service class responsible for sending email notifications when a ticket
 * is added to our PSQL databse
 */
@Service
public class NotifyService
{

    /* The bean used to execute sending the email */
    private final JavaMailSender javaMailSender;

    /**
     * Constructor injection to initialize our JavaMailSender bean
     * @param javaMailSender The bean used to execute sending the email
     */
    @Autowired
    public NotifyService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends an email when a ticket is added to our PSQL database
     * @param ticket The ticket object to extract information from to include in the email
     */
    public void sendMessage(Ticket ticket)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("joshportfoliosite@gmail.com");
        message.setTo("joshuaguevarra@gmail.com");
        message.setSubject("New Message from " + ticket.getFirstName() + " " + ticket.getLastName());
        message.setText("You have received a new message.\n" +
                        "Name: " + ticket.getFirstName() + " " + ticket.getLastName() +
                        "\n" + "Date Sent: " + ticket.getDateCreated() +
                        "\n" + "Ticket ID: " + ticket.getId() +
                        "\n" + "Email: " + ticket.getEmail() +
                        "\n\n" + "Message: " + ticket.getMessage()
                        );

        javaMailSender.send(message);
    }
}
