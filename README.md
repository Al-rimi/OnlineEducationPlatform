# OnlineEducationPlatform - Lockdown README

## Overview

OnlineEducationPlatform is a full-stack web application for online education, built with Spring Boot (Java), MyBatis, MySQL, and secured with Spring Security and JWT. The backend exposes RESTful APIs for user management and authentication, and is ready for integration with a Vue.js front-end.

## Features

- User registration, login, and JWT-based authentication
- User CRUD operations (create, read, update, delete)
- Role-based access control (Spring Security ready)
- MyBatis for ORM and SQL mapping
- MySQL database integration
- CORS enabled for front-end/back-end separation
- Ready for deployment as a Spring Boot JAR

## Tech Stack

- Java 8+
- Spring Boot 2.7.x
- Spring Security
- MyBatis
- MySQL 8.x
- JWT (io.jsonwebtoken)
- Maven

## Project Structure

```
src/
  main/
    java/com/example/onlineeducationplatform/
      config/         # Spring Security config
      controller/     # REST controllers
      mapper/         # MyBatis mappers
      model/          # Entity classes
      service/        # Service interfaces/impl
      OnlineEducationPlatformApplication.java # Main class
    resources/
      application.properties # Spring Boot config
      mappers/              # MyBatis XML mappers
```

## Configuration

Edit `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/online_education?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
mybatis.mapper-locations=classpath:mappers/*.xml
mybatis.type-aliases-package=com.example.onlineeducationplatform.model
```

## Running the Project

1. Build: `mvn clean package`
2. Run: `java -jar target/OnlineEducationPlatform-1.0-SNAPSHOT.jar`
3. API base URL: `http://localhost:8080/api/users`

## API Endpoints

- `POST /api/users/login` - User login (returns JWT)
- `POST /api/users/register` - User registration
- `GET /api/users` - List all users (auth required)
- `GET /api/users/{id}` - Get user by ID (auth required)
- `POST /api/users` - Add user (auth required)
- `PUT /api/users/{id}` - Update user (auth required)
- `DELETE /api/users/{id}` - Delete user (auth required)

## Security

- Passwords are stored using BCrypt hashing
- JWT tokens are used for stateless authentication
- Spring Security restricts access to authenticated users except for login/register

## Database

- MySQL database: `online_education`
- User table: `users` (id, username, password, email)
- See `mappers/UserMapper.xml` for SQL

## Development & Debugging

- Use your IDE's Spring Boot run/debug features
- Logs: see console output or configure logging in `application.properties`

## Front-End Integration

- CORS is enabled for all origins
- Designed for use with a Vue.js (or similar) front-end
- Use Axios or Fetch API to call backend endpoints

## Git & Version Control

- Use `git init` to initialize repo
- Commit and push code to your remote repository (GitHub, GitLab, etc.)
- Use feature branches for new features

## Deployment

- Deploy as a standalone JAR (Spring Boot)
- Can be containerized with Docker if needed
- For production, configure environment variables for DB credentials

## Authors & License

- Author: [Your Name]
- License: MIT (or your choice)

---

This README is auto-generated and should be updated as the project evolves.
