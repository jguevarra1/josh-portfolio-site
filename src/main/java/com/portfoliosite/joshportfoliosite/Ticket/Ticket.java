package com.portfoliosite.joshportfoliosite.Ticket;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entity class representing the main object for our database, Ticket
 *
 * Ticket is comprised of 6 attributes:
 * 1. The ID of the ticket
 * 2. The first name of the person who filled the contact form
 * 3. The last name of the person who filled the contact form.
 * 4. The date the contact form was completed.
 * 5. The person's email address.
 * 6. Their message.
 *
 * We use @ Entity here to tell Spring Data JPA that those fields will be columns in our PSQL database
 */
@Entity
@Table
public class Ticket
{
    /*
       @ Id is applied to the Long id variable to identify it as the primary key for our database
       @ SequenceGenerator Defines our primary key generation strategy
     */
    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )

    /* @ GeneratedValue is used here to specify our primary key generation strategy */
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )

    /* The primary key for our database */
    private Long Id;

    /* The first name of the person who sent the contact form */
    private String firstName;

    /* The last name of the person who sent the contact form */
    private String lastName;

    /* The message from the contact form */
    private String message;

    /* The person's email address */
    private String email;

    /* The date that the contact form was submitted */
    private LocalDate dateCreated;

    /* Blank constructor */
    public Ticket()
    {
        ;
    }

    /**
     * Constructor for our ticket class
     * This version of the constructor sets the date to today
     *
     * @param firstName The first name of the person who sent the contact form
     * @param lastName The last name of the person who sent the contact form
     * @param email The person's email address
     * @param message The message from the contact form
     */
    public Ticket(String firstName, String lastName, String email, String message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.message = message;
        this.dateCreated = LocalDate.now();
    }

    /**
     * Constructor for our ticket class
     * This version of the constructor sets a manual date
     *
     * @param firstName The first name of the person who sent the contact form
     * @param lastName The last name of the person who sent the contact form
     * @param email The person's email address
     * @param message The message from the contact form
     * @param dateCreated A date to set when the ticket was created
     */
    public Ticket(String firstName, String lastName, String email, String message, LocalDate dateCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.message = message;
        this.dateCreated = dateCreated;
    }

    /**
     * Gets the ID of the ticket
     * @return Returns the ID of the ticket
     */
    public Long getId() {
        return Id;
    }

    /**
     * Sets the ID of the ticket
     * @param id The ID to set for the ticket
     */
    public void setId(Long id) {
        Id = id;
    }

    /**
     * Gets the first name of the person who sent the contact form
     * @return Returns the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person of who sent the contact form
     * @param firstName The first name to set on the ticket object
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *  Gets the last name of the person who sent the contact form
     * @return Returns the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person of who sent the contact form
     * @param lastName The last name to set on the ticket object
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the message from the contact form
     * @return The message from the contact form
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets a new message for the ticket object
     * @param message The message to set on the ticket object
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the date from when the contact form was submitted
     * @return The date of when the contact form was submitted
     */
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the a new date for when the contact form was submitted
     * @param dateCreated The new date to set on the ticket object
     */
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Gets the email of the person who filled out the contact form
     * @return The email of the person who filled out the contact form
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a new email address for the person who sent the contact form
     * @param email The new email to set on the ticket object
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Represents the ticket object in a String format
     * @return The ticket object in String format
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
