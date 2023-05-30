package com.portfoliosite.joshportfoliosite.Ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

// This annotation allows us to have an AutoCloseable object that closes resources after each test
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TicketServiceTest {

    // The TicketService under test
    private TicketService testTicketService;

    // We have already tested the ticketRepo, so then we can just mock and can skip all the configurations
    // (creating the database, inserting tables, adding Ticket objects, etc.)
    @Mock
    private TicketRepo ticketRepo;

    // Sets up a fresh instance of our database every time a test is started
    @BeforeEach
    void setUp()
    {
        testTicketService = new TicketService(ticketRepo);
    }

    /**
     * Returns a list of invalid dates that are before the date of project creation
     * (database shouldn't have entries earlier than that)
     *
     * @return Returns a list of dates before the date of project creation
     */
    static List<LocalDate> invalidBeforeDates()
    {
        return List.of(
                // Before dates
                LocalDate.of(2023, 5, 9),
                LocalDate.of(2022, 5, 9),
                LocalDate.of(1999, 1, 1),
                LocalDate.of(2023, 4, 30),
                LocalDate.of(2023, 5, 8)
                );
    }

    /**
     * Returns a list of invalid dates that are after the current date
     * NOTE: The "After" dates may need to be updated since the current date may pass those values.
     *
     * @return Returns a list of dates that are after the current date
     */
    static List<LocalDate> invalidAfterDates()
    {
        return List.of(
                // After dates
                LocalDate.of(3000, 1, 1),
                LocalDate.of(2023, 5, 30),
                LocalDate.of(2024, 5, 9),
                LocalDate.of(2026, 1, 1)
                );
    }

    /**
     * Tests that a single ticket is added
     *
     * 1) The service invokes the addTicket method and passes in the ticket
     * object we created to save to the database
     *
     * 2) We create an ArgumentCaptor of type ticket for the Ticket class to capture
     * the value passed in
     *
     * 3) We verify that the ticketRepo.save() method is invoked and capture the
     * argument that was passed in from the service class.
     *
     * 4) We extract the value from the ArgumentCaptor object and handle it accordingly.
     *
     * We test in this fashion because we're invoking a ticketRepo method when we call addTicket(),
     * and since ticketRepo methods are obviously already tested, there's no need to test that it works
     */
    @Test
    void testAddTicket()
    {
        Ticket expectedTicket = new Ticket(1L, "Joe", "Smith",
                "JoeSmith@gmail.com", "This is test message number one.");

        testTicketService.addTicket(expectedTicket);
        ArgumentCaptor<Ticket> ticketArgumentCaptor =
                ArgumentCaptor.forClass(Ticket.class);

        verify(ticketRepo).save(ticketArgumentCaptor.capture());
        Ticket capturedTicket = ticketArgumentCaptor.getValue();

        assertThat(capturedTicket).isEqualTo(expectedTicket);
    }


    /**
     * Tests adding multiple tickets to the database
     * NOTE: See notes in first test in this class about ArgumentCaptor
     */
    @Test
    void testAddTicketMultiple()
    {
        LocalDate today = LocalDate.now();

        Ticket ticketOne = new Ticket(1L, "Joe", "Smith",
                "JoeSmith@gmail.com", "Hi, I'm Joe Smith!", today);

        Ticket ticketTwo = new Ticket(2L, "Bob", "Jones",
                "BobJones@gmail.com", "Hi, I'm Bob Jones!", today);

        Ticket ticketThree = new Ticket(3L, "Johnny", "Test",
                "JohnnyTest@gmail.com", "Hi, I'm Johnny Test!", today);

        List<Ticket> expectedTickets = List.of(ticketOne, ticketTwo, ticketThree);

        testTicketService.addTicket(expectedTickets);
        ArgumentCaptor<List<Ticket>> listArgumentCaptor =
                ArgumentCaptor.forClass(List.class);

        verify(ticketRepo).saveAll(listArgumentCaptor.capture());

        List<Ticket> actualTickets = listArgumentCaptor.getValue();
        assertThat(actualTickets).isEqualTo(expectedTickets);
    }


    /**
     * Tests that using the findTicket(Long) method throws an IllegalArgumentException
     * when using invalid ID arguments
     *
     * @param invalidId The input to test against the findTicket(Long) method
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-5L, 0L})
    void testFindTicketInvalidId(Long invalidId)
    {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.findTicket(invalidId));
    }

    /**
     * Tests that using the findTicket(Long) method throws a NoSuchElementException
     * when using an ID query that does not exist in the database
     */
    @Test
    void testFindTicketDoesNotExist()
    {
        Long id = 5L;
        Assertions.assertThrows(NoSuchElementException.class,
                () -> testTicketService.findTicket(id));
    }

    /**
     * Tests finding a ticket by its ID by checking that the expected ID matches
     * the actual ID of the ticket that is retrieved
     *
     * We use Mockito to mock the behavior of our ticketRepo to define the behavior
     * of our database when we call the findById(Long) method
     *
     * (The ticketRepo is already fully tested, and we can assume define its behavior)
     */
    @Test
    void testFindTicketById()
    {
        Long expectedId = 1L;
        Ticket expectedTicket = new Ticket(expectedId, "Joe", "Smith",
                "JoeSmith@gmail.com", "This is test message number one.");

        Mockito.when(ticketRepo.findById(expectedId)).thenReturn(Optional.of(expectedTicket));
        Ticket actualTicket = testTicketService.findTicket(expectedId);
        Long actualId = actualTicket.getId();

        assertThat(actualId).isEqualTo(expectedId);
    }

    /**
     * Tests that using the findTicket(String) method with null, empty, and blank strings
     * throws an IllegalArgumentException
     *
     * @ ParameterizedTest changes the input for the test
     * @ NullSource provides a null value since ValueSource values must be constant
     * @ ValueSource allows us to put in the values for the parameterized test
     *
     * @param input The String input to test against the findTicket(String) method
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "      "})
    void testFindTicketInvalidString(String input)
    {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.findTicket(input));
    }

    /**
     * Tests that the findTicket(String) method works correctly with a valid String input
     */
    @Test
    void testFindTicketValidString()
    {
        String expectedEmail = "JoeSmith@gmail.com";
        Ticket ticketOne = new Ticket(1L, "Joe", "Smith",
                expectedEmail, "This is test message number one.");

        Ticket ticketTwo = new Ticket(2L, "Joe", "Smith",
                expectedEmail, "This is test message number two.");

        Ticket ticketThree = new Ticket(3L, "Joe", "Smith",
                expectedEmail, "This is test message number three.");

        List<Ticket> expectedTickets = List.of(ticketOne, ticketTwo, ticketThree);

        Mockito.when(ticketRepo.findTicketByEmail(expectedEmail)).thenReturn(expectedTickets);

        List<Ticket> actualTickets = testTicketService.findTicket(expectedEmail);
        verify(ticketRepo).findTicketByEmail(expectedEmail);
        assertThat(actualTickets).isEqualTo(expectedTickets);
    }

    /**
     * Tests that the findTicket(LocalDate) method throws an IllegalArgumentException
     * when using a null date argument
     */
    @Test
    void testFindTicketInvalidNullDate()
    {
        LocalDate nullDate = null;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.findTicket(nullDate),
                "A date was not entered.");
    }

    /**
     * Tests that a date entry that is before when the project was created is an invalid date
     * and throws an IllegalArgumentException with the correct message
     *
     * @param date The invalid before date
     */
    @ParameterizedTest
    @MethodSource("invalidBeforeDates")
    void testFindTicketInvalidBeforeDate(LocalDate date)
    {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.findTicket(date));

        String expectedMessage = "The date " + date + " is an earlier date than when this website was created!";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    /**
     * Tests that a date entry that is after the current date is an invalid date
     * and throws an IllegalArgumentException with the correct message
     *
     * @param date The invalid after date
     */
    @ParameterizedTest
    @MethodSource("invalidAfterDates")
    void testFindTicketInvalidAfterDate(LocalDate date)
    {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.findTicket(date));

        String expectedMessage = "The date " + date + " is a future date!";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    /**
     * Tests that findTicket(LocalDate) returns the correct list of tickets
     */
    @Test
    void testFindTicketValidDate()
    {
        // This is the date from when I am most recently writing this test
        LocalDate expectedDate = LocalDate.of(2023, 5, 29);

        // This is a fake date (3 days prior expectedDate)
        LocalDate fakeDate = LocalDate.of(2023, 5, 25);

        Ticket ticketOne = new Ticket(1L, "Joe", "Smith",
                "JoeSmith@gmail.com", "Hi, I'm Joe Smith!", expectedDate);

        Ticket ticketTwo = new Ticket(2L, "Bob", "Jones",
                "BobJones@gmail.com", "Hi, I'm Bob Jones!", expectedDate);

        Ticket ticketThree = new Ticket(3L, "Johnny", "Test",
                "JohnnyTest@gmail.com", "Hi, I'm Johnny Test!", expectedDate);

        List<Ticket> expectedTickets = List.of(ticketOne, ticketTwo, ticketThree);
        Mockito.when(ticketRepo.findTicketByDate(expectedDate)).thenReturn(expectedTickets);

        List<Ticket> actualTickets = testTicketService.findTicket(expectedDate);
        verify(ticketRepo).findTicketByDate(expectedDate);

        assertThat(actualTickets).isEqualTo(expectedTickets);
    }

    /**
     * We don't want to test the ticketRepo when we're testing the ticketService
     * since we already tested the ticketRepo (the findAll method uses a ticketRepo findAll() method call)
     *
     * Instead, we can just mock its implementation for faster unit testing
     * (no need to create a new database, define table, insert entities, etc)
     *
     * If we changed the method that verify invokes, the test would fail
     */
    @Test
    void testFindAll()
    {
        testTicketService.findAll();
        verify(ticketRepo).findAll();
    }

    /**
     * Tests that deleteTicket(Long) works correctly by checking that the tickets to be deleted
     * are the same
     *
     * Again, since the method invokes the ticketRepo.delete(Entity) method, which is a predefined method
     * and is fully tested, we simply need to capture the argument that gets passed into the call to ensure the
     * matches the argument we expect.
     */
    @Test
    void testDeleteTicketById()
    {
        Long expectedId = 1L;
        Ticket expectedTicket = new Ticket(1L, "Joe", "Smith",
                "JoeSmith@gmail.com", "Hi, I'm Joe Smith!");

        Mockito.when(ticketRepo.findById(expectedId)).thenReturn(Optional.of(expectedTicket));

        ArgumentCaptor<Ticket> argumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        testTicketService.deleteTicket(expectedId);
        verify(ticketRepo).delete(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(expectedTicket);

    }

    /**
     * Tests that when the deleteTicket(Long) method is called with an invalid Ticket ID,
     * an IllegalArgumentException is thrown.
     */
    @Test
    void testDeleteTicketByInvalidId()
    {
        Long invalidId = -1L;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.deleteTicket(invalidId));
    }

    /**
     * Tests that when the deleteTicket(Long) method is called with a non-existent Ticket ID,
     * a NoSuchElementException is thrown.
     */
    @Test
    void testDeleteTicketByIdDoesNotExist()
    {
        Long nonExistent = 5L;
        Assertions.assertThrows(NoSuchElementException.class,
                () -> testTicketService.deleteTicket(nonExistent));
    }

    /**
     * Tests that the deleteTicket(String) method works correctly
     * See previous tests for notes about Mockito and ArgumentCaptor
     */
    @Test
    void testDeleteTicketByString()
    {
        String expectedEmail = "JoeSmith@gmail.com";

        Ticket ticketOne = new Ticket(1L, "Joe", "Smith",
                expectedEmail, "This is test message number one.");

        Ticket ticketTwo = new Ticket(2L, "Joe", "Smith",
                expectedEmail, "This is test message number two.");

        Ticket ticketThree = new Ticket(3L, "Joe", "Smith",
                expectedEmail, "This is test message number three.");

        List<Ticket> expectedTickets = List.of(ticketOne, ticketTwo, ticketThree);

        Mockito.when(ticketRepo.findTicketByEmail(expectedEmail)).thenReturn(expectedTickets);
        ArgumentCaptor<List<Ticket>> argumentCaptor =
                ArgumentCaptor.forClass(List.class);

        testTicketService.deleteTicket(expectedEmail);
        verify(ticketRepo).deleteAll(argumentCaptor.capture());

        List<Ticket> actualTickets = argumentCaptor.getValue();

        assertThat(actualTickets).isEqualTo(expectedTickets);
    }

    /**
     * Tests that when deleteTicket(String) method is called on an invalid String input,
     * an IllegalArgumentException is thrown
     * @param email The String containing bad input
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "      "})
    void testDeleteTicketByInvalidString(String email)
    {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.deleteTicket(email));
    }

    /**
     * Tests that deleteTicket(LocalDate) works correctly
     * See previous tests for notes about Mockito and ArgumentCaptor
     */
    @Test
    void testDeleteTicketByDate()
    {
        // This is the date from when I am most recently writing this test
        LocalDate expectedDate = LocalDate.of(2023, 5, 29);

        Ticket ticketOne = new Ticket(1L, "Joe", "Smith",
                "JoeSmith@gmail.com", "Hi, I'm Joe Smith!", expectedDate);

        Ticket ticketTwo = new Ticket(2L, "Bob", "Jones",
                "BobJones@gmail.com", "Hi, I'm Bob Jones!", expectedDate);

        Ticket ticketThree = new Ticket(3L, "Johnny", "Test",
                "JohnnyTest@gmail.com", "Hi, I'm Johnny Test!", expectedDate);


        List<Ticket> expectedTickets = List.of(ticketOne, ticketTwo, ticketThree);

        Mockito.when(ticketRepo.findTicketByDate(expectedDate)).thenReturn(expectedTickets);

        ArgumentCaptor<List<Ticket>> argumentCaptor =
                ArgumentCaptor.forClass(List.class);

        testTicketService.deleteTicket(expectedDate);
        verify(ticketRepo).deleteAll(argumentCaptor.capture());

        List<Ticket> actualTickets = argumentCaptor.getValue();

        assertThat(actualTickets).isEqualTo(expectedTickets);
    }

    /**
     * Tests that when the deleteTicket(LocalDate) method is used with a date that is before project
     * creation, then an IllegalArgumentException is thrown with the specified message
     * @param date The invalid before date
     */
    @ParameterizedTest
    @MethodSource("invalidBeforeDates")
    void testDeleteTicketByInvalidBeforeDate(LocalDate date)
    {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.deleteTicket(date));

        String expectedMessage = "The date " + date + " is an earlier date than when this website was created!";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    /**
     * Tests that when deleteTicket(LocalDate) method is used with a date that is after the current date,
     * then an IllegalArgumentException is thrown with the specified message
     * @param date The invalid after date
     */
    @ParameterizedTest
    @MethodSource("invalidAfterDates")
    void testDeleteTicketByInvalidAfterDate(LocalDate date)
    {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> testTicketService.deleteTicket(date));

        String expectedMessage = "The date " + date + " is a future date!";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}