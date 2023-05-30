package com.portfoliosite.joshportfoliosite.Ticket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * The test suite to test our TicketRepo repository
 * For testing purposes, we're using an H2 in-memory database
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TicketRepoTest
{

    @Autowired
    private TicketRepo testTicketRepo;

    /**
     * Clears the H2 repository after each test for a clean slate
     */
    @AfterEach
    void tearDown()
    {
        testTicketRepo.deleteAll();
    }

    /**
     * Tests that the value returned when we use a String email query that don't exist in the database
     * returns a non-null value and checks that its list size is 0
     */
    @Test
    void testEmailDoesNotExist()
    {
        String emailQuery = "DoesNotExist@gmail.com";

        List<Ticket> actual = testTicketRepo.findTicketByEmail(emailQuery);

        int expectedSize = 0;
        int actualSize = actual.size();

        assertThat(actual).isNotNull();
        assertThat(actualSize).isEqualTo(expectedSize);
    }

    /**
     * Tests finding a single ticket by email
     */
    @Test
    void testFindTicketByEmailSingle()
    {
        String expectedEmail = "JoeSmith@gmail.com";

        Ticket ticket = new Ticket(1L, "Joe", "Smith",
                expectedEmail, "This is a test message.");

        testTicketRepo.save(ticket);
        List<Ticket> ticketList = testTicketRepo.findTicketByEmail(expectedEmail);

        // test for size to ensure that there is an index to grab from
        int expectedSize = 1;
        int actualSize = ticketList.size();
        assertThat(actualSize).isEqualTo(expectedSize);

        // Since we tested for size, we can use the 0th index since we know there's only one entry
        Ticket result = ticketList.get(0);
        String actualEmail = result.getEmail();

        assertThat(actualEmail).isEqualTo(expectedEmail);
    }

    /**
     * Tests that a correct list of tickets that correspond to a single email is returned
     * by checking each Ticket ID and email
     */
    @Test
    void testFindTicketByEmailMultiple()
    {
        String expectedEmail = "JoeSmith@gmail.com";

        Ticket ticketOne = new Ticket(1L, "Joe", "Smith",
                expectedEmail, "This is test message number one.");

        Ticket ticketTwo = new Ticket(2L, "Joe", "Smith",
                expectedEmail, "This is test message number two.");

        Ticket ticketThree = new Ticket(3L, "Joe", "Smith",
                expectedEmail, "This is test message number three.");

        // throw in a fake person to make sure it is not included in the list that is returned
        Ticket ticketFour = new Ticket(4L, "Fake", "Person",
                "FakePerson@gmail.com", "This is a fake message");

        List<Ticket> ticketsToAdd = List.of(ticketOne, ticketTwo, ticketThree, ticketFour);
        testTicketRepo.saveAll(ticketsToAdd);

        List<Ticket> listOfTicketsFound = testTicketRepo.findTicketByEmail(expectedEmail);

        int expectedSize = 3;
        int actualSize = listOfTicketsFound.size();

        assertThat(actualSize).isEqualTo(expectedSize);

        for (int i = 0; i < actualSize; i++)
        {
            Ticket expected = ticketsToAdd.get(i);
            Ticket actual = listOfTicketsFound.get(i);

            assertThat(actual.getId()).isEqualTo(expected.getId());
            assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        }
    }

    /**
     * Tests that the value returned when we use a date query that doesn't exist in the database
     * returns a non-null value and checks that its list size is 0
     */
    @Test
    void testDateDoesNotExist()
    {
        // This is a fake date
        LocalDate fakeDate = LocalDate.of(2022, 1, 1);

        List<Ticket> actual = testTicketRepo.findTicketByDate(fakeDate);
        assertThat(actual).isNotNull();

        int expectedSize = 0;
        int actualSize = actual.size();
        assertThat(actualSize).isEqualTo(expectedSize);
    }

    /**
     * Tests finding a single ticket that corresponds to a given date
     */
    @Test
    void testFindTicketByDateSingle()
    {
        // This is the date from when I am most recently writing this test
        LocalDate expectedDate = LocalDate.of(2023, 5, 28);

        Ticket ticket = new Ticket(1L, "Joe", "Smith",
                "JoeSmith@gmail.com", "This is a test message.", expectedDate);

        testTicketRepo.save(ticket);
        List<Ticket> foundByDate = testTicketRepo.findTicketByDate(expectedDate);

        Ticket ticketActual = foundByDate.get(0);
        LocalDate actualDate = ticketActual.getDateCreated();

        assertThat(actualDate).isEqualTo(expectedDate);
    }

    /**
     * Tests that a correct list of tickets that correspond to a single date is returned
     * by checking each ticket's date
     */
    @Test
    void testFindTicketByDateMultiple()
    {
        // This is the date from when I am most recently writing this test
        LocalDate expectedDate = LocalDate.of(2023, 5, 28);

        // This is a fake date (3 days prior expectedDate)
        LocalDate fakeDate = LocalDate.of(2023, 5, 25);

        Ticket ticketOne = new Ticket(1L, "Joe", "Smith",
                "JoeSmith@gmail.com", "Hi, I'm Joe Smith!", expectedDate);

        Ticket ticketTwo = new Ticket(2L, "Bob", "Jones",
                "BobJones@gmail.com", "Hi, I'm Bob Jones!", expectedDate);

        Ticket ticketThree = new Ticket(3L, "Johnny", "Test",
                "JohnnyTest@gmail.com", "Hi, I'm Johnny Test!", expectedDate);

        Ticket ticketFour = new Ticket(4L, "Fake", "Person",
                "FakePerson@gmail.com", "Hi, I'm a Fake Person!", fakeDate);

        testTicketRepo.saveAll(List.of(ticketOne, ticketTwo, ticketThree, ticketFour));

        List<Ticket> foundByDate = testTicketRepo.findTicketByDate(expectedDate);
        int expectedSize = 3;
        int actualSize = foundByDate.size();

        assertThat(actualSize).isEqualTo(expectedSize);

        for (Ticket ticket : foundByDate)
        {
            LocalDate actualDate = ticket.getDateCreated();
            assertThat(actualDate).isEqualTo(expectedDate);
        }
    }
}