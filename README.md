# ai-upskill-backend
Leveling up AI skill with Spring Boot (Wizard World)

## Project Overview

This project is a minimal Spring Boot REST API designed to help users upskill in AI. It provides user registration, login, and JWT-based authentication, and exposes endpoints for basic user management. The backend uses Spring Boot, Spring Data JPA, and an H2 in-memory database for development.

### Key Features
- User registration and login endpoints
- JWT-based authentication
- RESTful API with Swagger/OpenAPI documentation
- In-memory H2 database for development

## Project Structure

```
ai-upskill-backend/
├── pom.xml
├── README.md
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/aiupskillbackend/
│       │       ├── AiUpskillBackendApplication.java  # Main Spring Boot application
│       │       ├── config/
│       │       │   └── SwaggerConfig.java           # Swagger/OpenAPI configuration
│       │       ├── controller/
│       │       │   ├── HelloWorldController.java    # Example hello world endpoint
│       │       │   └── UserController.java          # User registration/login endpoints
│       │       ├── dto/
│       │       │   ├── LoginRequest.java
│       │       │   ├── LoginResponse.java
│       │       │   └── UserRegistrationRequest.java
│       │       ├── model/
│       │       │   └── User.java                    # User entity
│       │       ├── repository/
│       │       │   └── UserRepository.java          # JPA repository for User
│       │       └── util/
│       │           └── JwtUtil.java                 # JWT utility class
│       └── resources/
│           └── application.properties               # App configuration
└── target/                                          # Build output
```

## Getting Started

1. Clone the repository
2. Build with Maven: `mvn clean install`
3. Run the application: `mvn spring-boot:run`
4. Access Swagger UI at `http://localhost:8080/swagger-ui.html`

## API Endpoints

- `/api/hello` - Hello world endpoint
- `/api/users/register` - Register a new user
- `/api/users/login` - Login and receive JWT token

## Technologies Used
- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Swagger/OpenAPI

---
For more details, see the source code and comments in each file.
