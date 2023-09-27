# User Authentication and Vehicle Management System

This application is built using Spring Boot and includes features for user authentication, vehicle management, and user cart functionality. It consists of multiple microservices that interact with each other to provide a seamless user experience.

## Features

- User Authentication:
    - User registration with email, password, and username.
    - User login with JWT-based authentication and token generation.
    - JWT tokens for secure communication between services.

- Vehicle Management:
    - Adding, updating, and deleting vehicles.
    - Storing vehicle details in a MongoDB database.
    - Handling exceptions for vehicle not found and conflicts.

- User Cart Functionality:
    - Adding vehicles to the user's cart.
    - Retrieving the list of vehicles in the user's cart.
    - Deleting vehicles from the user's cart.
    - Using JWT tokens for user identification.

- API Gateway:
    - Routing requests to different services based on path prefixes.
    - Global CORS configuration for handling cross-origin requests.

## Modules

- `UserAuthenticationVA`:
    - Contains user authentication related components.
    - User registration, login, and token generation.
    - Exception handling for user-related cases.

- `APIGatewayVA`:
    - Spring Cloud Gateway configuration for routing requests.
    - Global CORS settings for cross-origin requests.

- `VehicleAppForUSerAndAdmin`:
    - Handles vehicle management and user cart features.
    - Controllers for user registration, vehicle management, and cart functionality.
    - MongoDB entities for storing user and vehicle information.
    - Exception handling for vehicle-related cases.

## How to Run

1. Clone the repository to your local machine.
2. Configure MongoDB and MySql connection settings in `application.properties`.
3. Start each microservice separately, for example: cd UserAuthenticationVA
   ./mvnw spring-boot:run
4. Access the API endpoints using appropriate tools like Postman or a web browser.

## Dependencies

- Spring Boot
- Spring Cloud Gateway
- Spring Data MongoDB
- Feign (For service communication)
- Lombok (For generating boilerplate code)
- JSON Web Tokens (JWT) for authentication

## Author

Sujit Bondage


