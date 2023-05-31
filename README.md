# My Very Own Portfolio Website!
## Sections
1. [Introduction](#introduction)<br>
2. [Project Descrpition](#project-description)
3. [Project Demo](#project-demo)
-----

## Introduction
<em>5/30/2023</em>

Before I start with Optum this upcoming July, I wanted to make something to review and learn HTML, CSS and Spring Boot. Prior to this project, I never had the opportunity to really try out web development during my school years, since I vaguely followed given templates to implement web interfaces whenever needed. Although I never saw myself being interested in front end development, it's something that I've always wanted to try, and I should definitely have working knowledge on it. 

In addition to this, I made a goal for myself of solidifying my understanding of dependency injection and the MVC architecture since I most recently worked on a project that involved those two principles and Microservices in my senior capstone project. During that senior capstone project, I used the Micronaut framework in our backend, and after seeing how great it was, I wanted to explore the Spring ecosystem!


I started this project on 5/6/2023 and finally finished on 5/29/2023.<br>
This project took around <em>22 hours</em> over the span of 11 working days.

This is going to be the first version of my portfolio website and I hope to continuously enhance this as I continue to grow as a developer!

***Currently this project is not publically hosted. I will most likely host the project in the future.***

-------

## Project Description
This is a full-stack web application developed using Java and the Spring Boot framework. 

The front end of this project serves as my web portfolio to showcase my basic contact info, links to GitHub and LinkedIn, projects I've worked on with basic descriptions, and includes a contact form to send me messages. The Bootstrap framework was used to style a majority of the webpages created. 

The back end of this project is written in Java and the Spring Boot framework in order to perform server-side data processing on the data sent from the Contact page. The data sent from the Contact page is sent to the back end through a POST request when the form is submitted. The fields are then automatically linked to a Ticket object by using @RequestBody and @ModelAttribute annotations in Spring Boot in a controller class dedicated for displaying our views, establishing the endpoints and making calls to our Service layer classes.

Then, the Ticket object is saved in a PostGreSQL database and an email is sent through the use of the Spring Boot Mail library over the GMail SMTP. The email message displays the Ticket attribute name and its value separated by a colon (ex. Name: Example Person).

The code contains general CRUD operations to perform on the ticket. Overall it follows the MVC architecture and dependency injection code patterns.

I also wrote test classes to automatically test the general methods found in our Service layer classes as well as ensuring that we are able to connect to our endpoints with the correct views and recieving the correct data.

***The code will not run if it is cloned since it is missing the application.properties file.***

### Tech Stack and Tools
* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* HTML / CSS / Bootstrap
* PostGreSQL (Relational Database)
* H2 (In-memory database for testing)
* JUnit / Mockito / AssertJ
* Maven (Depedency Management)


-----

## Project Demo

https://youtu.be/mrVDIW4cWIQ
