package com.portfoliosite.joshportfoliosite.Ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Class responsible for configuring our JavaMailSender bean
 * (This wasn't working with the configurations in the properties file, so I had to manually configure it)
 */
@Configuration
public class MailSenderConfig
{
    @Value("${EMAIL_USERNAME}")
    private String username;

    @Value("${EMAIL_PASSWORD}")
    private String password;

    /**
     * Bean used to send emails using Gmail SMTP
     *
     * See <a href="https://support.google.com/mail/answer/7104828?hl=en&rd=3&visit_id=638208134803543552-1686357182">https://support.google.com/mail/answer/7104828?hl=en&rd=3&visit_id=638208134803543552-1686357182</a>
     * See <a href="https://www.baeldung.com/spring-email">https://www.baeldung.com/spring-email</a>
     * @return Returns a JavaMailSender object used to send emails
     */
    @Bean
    public JavaMailSender javaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
