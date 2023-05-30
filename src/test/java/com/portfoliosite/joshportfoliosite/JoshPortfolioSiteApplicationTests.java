package com.portfoliosite.joshportfoliosite;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = JoshPortfolioSiteApplication.class
)
@AutoConfigureMockMvc
class JoshPortfolioSiteApplicationTests {

	@Test
	void contextLoads() {
	}

}
