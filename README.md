# Online Education Platform - Team Cryptonics

## Team Project Training - Zhejiang Normal University

**Course:** Team Project Training (Part II)  
**Project Type:** Comprehensive Java Web Online Education Platform  
**Team Size:** 5 members  
**Duration:** 16 weeks (1 semester)  
**Submission Date:** December 21, 2025  

## Project Overview

The Online Education Platform is a comprehensive web-based system designed to provide flexible, convenient, and efficient online learning experiences. Built with modern web technologies, the platform integrates user management, course management, online learning, communication, and interaction features to create an efficient and personalized learning environment.

## 🎯 Project Objectives

- **Professional Skills**: Master Java Web development using SSM framework (Spring, Spring MVC, MyBatis)
- **Engineering Quality**: Develop requirements analysis and software design capabilities
- **Teamwork**: Practice communication and collaboration in team development
- **Git Collaboration**: Understand and apply version control in team projects

## 📋 Deliverables Checklist

### ✅ Requirements Analysis and Design Documents
- **[Requirements_Analysis_and_Design_Specification.md](Requirements_Analysis_and_Design_Specification.md)**
  - Complete requirements specification
  - Use case analysis and diagrams
  - Software architecture design
  - Detailed design specifications

### ✅ Project Source Code
- **Backend**: Spring Boot application with REST APIs
- **Frontend**: Vue.js single-page application
- **Database**: MySQL schema and initialization scripts
- **Build System**: Maven configuration and dependencies

### ✅ Test Documents
- **[Test_Documents.md](Test_Documents.md)**
  - Comprehensive test plan and test cases
  - Unit, integration, and system testing results
  - Performance and security testing reports

### ✅ Personal Summaries
- **[Personal_Summary_AL_RAIMI_ABDULLAH.md](Personal_Summary_AL_RAIMI_ABDULLAH.md)** - Team Leader & Backend Developer
- **[Personal_Summary_AL_AHMADI_ABDULRAHMAN.md](Personal_Summary_AL_AHMADI_ABDULRAHMAN.md)** - Frontend Developer
- **[Personal_Summary_Mohammed_Jawo.md](Personal_Summary_Mohammed_Jawo.md)** - Database Administrator
- **[Personal_Summary_UDE_Raphael_IKECHUKWU.md](Personal_Summary_UDE_Raphael_IKECHUKWU.md)** - Quality Assurance Specialist

### ✅ Project Summary
- **[Project_Deliverables_Summary.md](Project_Deliverables_Summary.md)**
  - Complete project overview and achievements
  - Technical implementation details
  - Future enhancement recommendations

## 🏗️ System Architecture

### Technology Stack
- **Backend**: Java 21, Spring Boot 2.7.18, Spring Security, MyBatis
- **Frontend**: Vue.js 3, Vite, Axios, Vue Router
- **Database**: MySQL 8.0 with connection pooling
- **Build Tool**: Maven 3.6+ with frontend plugin
- **Authentication**: JWT tokens with role-based access control

### System Modules

#### 1. User Management Module
- User registration and authentication
- Role-based access control (Admin, Teacher, Student)
- Profile management and password recovery
- User administration and permission management

#### 2. Course Management Module
- Course creation and content management
- Course approval and publishing workflow
- Course categorization and search functionality
- Multimedia content support (videos, documents)

#### 3. Online Learning Module
- Video streaming with progress tracking
- Interactive quiz system with automatic grading
- Assignment submission and manual grading
- Learning progress monitoring and analytics

#### 4. Interactive Communication Module
- Course discussion forums
- Direct messaging between users
- Teacher-student communication channels
- Community interaction features

#### 5. Administrator Module
- System-wide user management
- Course content moderation and approval
- Analytics dashboard and reporting
- System configuration and maintenance

## 🚀 Quick Start

### Prerequisites
- **Java**: OpenJDK 21
- **Database**: MySQL 8.0
- **Build Tool**: Maven 3.6+
- **Node.js**: Version 18+ (for frontend development)

### Installation Steps

1. **Clone Repository**
   ```bash
   git clone <repository-url>
   cd OnlineEducationPlatform
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE online_education;
   -- Tables and sample data are automatically created
   ```

3. **Backend Setup**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   Server starts on: http://localhost:8081

4. **Frontend Setup** (New Terminal)
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   Development server: http://localhost:5173

### Default Test Accounts

| Role | Username | Password | Description |
|------|----------|----------|-------------|
| Admin | `admin` | `admin123` | Full system access |
| Teacher | `teacher` | `teacher123` | Course creation and management |
| Student | `student` | `student123` | Learning and course enrollment |

## 📊 System Features

### Core Functionality
- ✅ **User Management**: Registration, authentication, role management
- ✅ **Course Management**: CRUD operations, content management, approval workflow
- ✅ **Learning System**: Video playback, quizzes, assignments, progress tracking
- ✅ **Communication**: Forums, messaging, teacher-student interaction
- ✅ **Administration**: User management, analytics, system monitoring

### Technical Features
- ✅ **Security**: JWT authentication, password encryption, role-based access
- ✅ **Performance**: Optimized queries, connection pooling, caching
- ✅ **Scalability**: Modular architecture, RESTful APIs, stateless design
- ✅ **Usability**: Responsive design, intuitive UI, accessibility compliance

## 🧪 Testing Results

### Test Coverage Summary
- **Unit Tests**: 15 cases - 93.3% pass rate
- **Integration Tests**: 8 cases - 87.5% pass rate
- **System Tests**: 12 cases - 91.7% pass rate
- **Performance Tests**: 3 cases - 100% pass rate
- **Security Tests**: 5 cases - 100% pass rate
- **Overall Pass Rate**: 93.0%

### Key Test Achievements
- All core business functionality validated
- Authentication and authorization working correctly
- Performance requirements met (< 3s response time)
- Security vulnerabilities addressed and mitigated

## 📁 Project Structure

```
OnlineEducationPlatform/
├── pom.xml                                    # Maven configuration
├── README.md                                  # Project documentation
├── Requirements_Analysis_and_Design_Specification.md
├── Test_Documents.md
├── Personal_Summary.md
├── Project_Deliverables_Summary.md
├── src/
│   └── main/
│       ├── java/com/example/onlineeducationplatform/
│       │   ├── config/                        # Spring configuration
│       │   ├── controller/                    # REST controllers
│       │   ├── mapper/                        # MyBatis mappers
│       │   ├── model/                         # Entity classes
│       │   ├── security/                      # Security services
│       │   └── service/                       # Business logic
│       └── resources/
│           ├── application.properties         # App configuration
│           ├── db/                           # Database scripts
│           └── mappers/                      # MyBatis XML files
└── frontend/
    ├── package.json                          # Node dependencies
    ├── vite.config.js                       # Build configuration
    └── src/
        ├── components/                       # Vue components
        ├── layouts/                         # Page layouts
        ├── pages/                           # Route pages
        ├── services/                        # API services
        └── stores/                          # State management
```

## 🔗 API Endpoints

### Authentication
- `POST /api/users/login` - User login
- `POST /api/users/register` - User registration
- `GET /api/users/me` - Current user profile

### Course Management
- `GET /api/courses` - Public courses
- `GET /api/courses/my-courses` - Teacher's courses
- `GET /api/courses/enrolled` - Student's courses
- `POST /api/courses` - Create course

### Learning Activities
- `GET /api/quizzes/{courseId}` - Course quizzes
- `POST /api/quizzes/{id}/submit` - Submit quiz
- `GET /api/assignments/{courseId}` - Course assignments
- `POST /api/assignments/{id}/submit` - Submit assignment

## 👥 Team Members

1. **AL RAIMI ABDULLAH** (Team Leader) - Backend Developer - Authentication, API development, security, project coordination
2. **AL-AHMADI ABDULRAHMAN MOHAMMED HAMED** - Frontend Developer - Vue.js implementation, UI/UX design
3. **Mohammed Jawo** - Database Administrator - Schema design, optimization, data management
4. **UDE Raphael IKECHUKWU** - Quality Assurance - Testing, bug tracking, validation

## 📈 Project Metrics

- **Lines of Code**: ~15,000+ lines (Backend + Frontend)
- **Test Coverage**: 93% pass rate across all test categories
- **Performance**: < 3 seconds average response time
- **Security**: JWT implementation with role-based access
- **Scalability**: Supports 100+ concurrent users

## 🔮 Future Enhancements

### Planned Features
- Mobile application development
- Real-time video conferencing
- AI-powered learning recommendations
- Advanced analytics and reporting
- Multi-language support

### Technical Improvements
- Microservices architecture migration
- Cloud deployment (AWS/Azure)
- Redis caching implementation
- WebSocket for real-time features

## 📚 Learning Outcomes

### Technical Skills
- Full-stack web development (Java + Vue.js)
- RESTful API design and implementation
- Database design and optimization
- Security best practices and implementation
- Modern development tools and workflows

### Professional Skills
- Team collaboration and communication
- Project management and planning
- Requirements analysis and design
- Quality assurance and testing
- Documentation and presentation

## 🏆 Project Achievements

- ✅ **Complete System**: Fully functional online education platform
- ✅ **Modern Architecture**: Spring Boot + Vue.js with best practices
- ✅ **Security Implementation**: Comprehensive authentication and authorization
- ✅ **Quality Assurance**: Thorough testing with high pass rates
- ✅ **Documentation**: Complete requirements and design specifications
- ✅ **Team Collaboration**: Successful team project with Git workflow

## 📞 Support

For technical questions or issues:
- Check the [Test Documents](Test_Documents.md) for known issues
- Review the [Requirements Specification](Requirements_Analysis_and_Design_Specification.md)
- Contact team members through the project repository

## 📄 License

This project is developed as part of Zhejiang Normal University Team Project Training course.

---

**🎓 Zhejiang Normal University**  
**Team Project Training (Part II)**  
**December 2025**

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
2. Run Dev: `mvn spring-boot:run` (backend on 8081)
3. Frontend Dev: `cd frontend && npm install && npm run dev` (Vite on 5175)
4. Open: `http://localhost:5175`

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

Backend (port 8081):

```
mvn spring-boot:run
```

Frontend (port 5175 with proxy to backend):

```
cd frontend
npm install
npm run dev
```

Open: http://localhost:5175

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

### Sample Accounts (Demo)

The database is auto-initialized with rich sample data and clear demo accounts for each role:

- Admin: `admin` / `admin123`
- Teacher: `teacher` / `teacher123`
- Student: `student` / `student123`

Additional users exist (e.g., `john_doe`, `jane_smith`) to populate enrollments, purchases, and activities.

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
