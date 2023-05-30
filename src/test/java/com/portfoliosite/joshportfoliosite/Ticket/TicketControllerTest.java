package com.portfoliosite.joshportfoliosite.Ticket;

import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@WebMvcTest(TicketController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TicketControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private NotifyService notifyService;

    /**
     * Tests that the contact page is correctly shown by checking that the status code returned is OK
     * and that the view name matches contact.html
     * @throws Exception Throws an exception if there is an issue with making the request
     */
    @Test
    void testShowContactPage() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("contact.html"));
    }

    /**
     * Tests that a ticket is added by making a POST request at /submit with a JSON payload
     * representing a ticket object sent from the contact.html page
     *
     * It then checks if the page is redirected at the URL "/submit.html
     * @throws Exception Throws an exception if there is an issue performing the request
     */
    @Test
    void testAddTicket() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/submit")
                        .contentType("application/json")
                        .content("{" +
                                    "\"firstName\": \"Joe\", " +
                                    "\"lastName\": \"Smith\", " +
                                    "\"email\": \"JoeSmith@gmail.com\", " +
                                    "\"message\": \"This is a test message\"" +
                                  "}"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/submit.html"));
    }
}