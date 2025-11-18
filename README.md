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
- Schema SQL: `src/main/resources/db/schema.sql`

### Apply the schema (MySQL client)

If you have the MySQL CLI installed, run:

```
mysql -u root -p < src/main/resources/db/schema.sql
```

Enter your password (e.g., `000000` per your setup). This creates the database and `users` table.

## Development & Debugging

- Use your IDE's Spring Boot run/debug features
- Logs: see console output or configure logging in `application.properties`

## Front-End (Vue 3 + Vite)

The project includes a decoupled Vue 3 single-page application located in `frontend/` consuming the REST API.

### Frontend Structure

```
frontend/
  package.json
  vite.config.js            # Dev server + proxy to backend
  index.html
  src/
    main.js                 # App bootstrap
    App.vue                 # Layout + navigation
    router.js               # Route definitions
    style.css               # Global minimal styles
    services/api.js         # Axios instance + auth header
    components/
      Login.vue             # Login form (JWT)
      Register.vue          # Registration form
      UserList.vue          # List users
      UserDetail.vue        # Show single user
      UserForm.vue          # Add / edit user
```

### Development Run (Two Terminals)

Backend (port 8081 after migration):

```
mvn spring-boot:run
```

Frontend (port 5173 with proxy to backend):

```
cd frontend
npm install
npm run dev
```

Open: http://localhost:5173

### Production Build (Frontend)

```
cd frontend
npm run build
```

The build output will be in `frontend/dist/`. You can serve it with Nginx or any static server and point `/api` calls to the backend (e.g. via reverse proxy).

### API Proxy (Dev)

`vite.config.js` configures a proxy so that calls to `/api/*` from the front-end dev server are forwarded to `http://localhost:8081` (no CORS issue during development).

### Authentication Flow

1. User registers via `POST /api/users/register`.
2. User logs in via `POST /api/users/login` → receives `{ token }`.
3. Token stored in `localStorage` and added as `Authorization: Bearer <token>` via Axios interceptor.
4. Protected operations (create/update/delete) require valid token; public read enabled for demo.

### Environment Variables (Optional Enhancements)

You can create a `.env` inside `frontend/` to override base API:

```
VITE_API_BASE=/
```

And in `api.js` use `import.meta.env.VITE_API_BASE` (not yet wired—simple base '/' used now).

### Basic UX Notes

The UI is intentionally minimal to focus on experiment objectives: separation, API consumption, CRUD, auth state, and routing.

## Smoke Test (Experiment 2)

1. Start backend (`mvn spring-boot:run`). Ensure DB `online_education` and `users` table exist.
2. Start frontend (`npm run dev` inside `frontend`).
3. Visit http://localhost:5173
4. Register a new user → Success message appears.
5. Login using the new credentials → Navigated to User List.
6. Create a user via Add User form → Appears in list.
7. View user detail → Correct data displayed.
8. Edit user → Changes reflected.
9. Delete user → Removed from list.
10. Logout → Login/Register links return, protected actions fail (Delete/Add prompts).

## CORS & Security Adjustments

- Spring Security allows: `/`, `/api/users/login`, `/api/users/register`, public GET `/api/users`.
- OPTIONS requests permitted for preflight.
- Axios dev proxy avoids manual CORS header management in development.

## Potential Improvements / Next Steps

- Persist roles & add role-based authorization.
- Add pagination & search to `UserList`.
- Central error & toast notification system.
- Extract JWT secret to environment variable.
- Add refresh token mechanism & token expiry handling on frontend.
- Docker Compose for full stack.

## New Pages and Sample Data

### Pages added

- Dashboard (`/`): shows current user info and total users (requires login)
- Users (`/users`): list of users (requires login)
- Add/Edit User (`/add`, `/edit/:id`): protected forms
- My Profile (`/profile`): reads from `/api/users/me` (requires login)
- Not Found: catch-all for unknown routes

Global toasts show success/error messages. Unauthorized requests trigger a toast and redirect to login. After login, you’re sent back to the original page you wanted.

### Sample data seeding

On backend startup, if the `users` table is empty, three sample users are created automatically:

- `alice` / `alice123`
- `bob` / `bob123`
- `charlie` / `charlie123`

Passwords are stored securely (BCrypt). If the table already has users, seeding is skipped.

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
