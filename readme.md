# Spring Boot REST API Store Management Tool

## A fully functional example project in Spring Boot implementing the basic functionalities of a store management tool.

This project was created as a demo showing how to create an OAuth secured Spring Boot Api with error handling, logging and unit tests.
The project implements the following:

* Basic CRUD functions, for example: create order, update product stock etc;
* A basic authentication mechanism and role based endpoint access using Spring Security;
* Error mechanism and handling using ControllerAdvice
* Logging;
* Unit tests using JUnit and Mockito;
* Java 9+ features;

##How to run the project

1. Clone this project
2. The project requires Java 17 and is using the H2 db so no other tools needed
3. Run the application
4. Import the postman collection and environment which can be found in the postman directory in the project resources
5. Register if you want to test the buyer endpoints
6. Use the auth endpoint to get a token
7. Test the endpoints
8. Go to http://localhost:8080/h2-console/ to access the database


