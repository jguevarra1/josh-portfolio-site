package com.portfoliosite.joshportfoliosite.Ticket;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Ticket
{
    // TODO Set up the database sequence here
    private Long Id;
    private String firstName;
    private String lastName;
    private String message;

    private LocalDate dateCreated;

    public Ticket()
    {
        ;
    }
    public Ticket(String firstName, String lastName, String message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
        this.dateCreated = LocalDate.now();
    }

    public Ticket(String firstName, String lastName, String message, LocalDate dateCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString()
    {
        return "Ticket{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", message='" + message + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
