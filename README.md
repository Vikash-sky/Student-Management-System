[README.md](https://github.com/user-attachments/files/33206349/README.md)
# Student Management System

A RESTful backend application for managing students, departments and courses, built with **Spring Boot**, **Spring Data JPA** and **MySQL**. The API is secured with **JWT (JSON Web Token)** authentication and follows a clean layered architecture (Controller, Service, Repository).

---

## Features

- **JWT authentication**: stateless login with email and password, token sent as a `Bearer` header
- **Student CRUD**: create, read, full update (`PUT`), partial update (`PATCH`) and delete
- **Departments and Courses**: create and fetch, linked to students
- **Relationships**: Student has One-to-One `Address`, Many-to-One `Department`, Many-to-Many `Courses`
- **Multiple ways to query data**:
  - Derived queries and JPQL (`@Query`)
  - Native SQL queries
  - JPA **Specifications** for dynamic filtering (name, email, course)
  - Custom repository implementation
  - Projections and DTO projections
  - `@EntityGraph` for fetching related data efficiently
  - Sorting and **pagination**
- **Validation**: request validation using Bean Validation (strong password rules, valid email, required fields)
- **Global exception handling**: consistent JSON error responses (`404`, `409`, `400`)
- **Auditing**: automatic `createdAt`, `updatedAt`, `createdBy` and `lastModifiedBy`
- **Optimistic locking** with `@Version` and a `deleted` flag on students
- **Standard response wrapper** (`ApiResponse`) for all main endpoints
- **Tests**: unit tests (JUnit 5 + Mockito) and integration tests (H2 in-memory database)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 |
| Web | Spring Web MVC |
| Persistence | Spring Data JPA (Hibernate) |
| Database | MySQL (H2 for tests) |
| Security | Spring Security + JWT (jjwt 0.13.0), BCrypt password hashing |
| Validation | Spring Boot Starter Validation |
| Utilities | Lombok |
| Build tool | Maven (Maven Wrapper included) |
| Testing | JUnit 5, Mockito, H2 |

---

## Project Structure

```
src/main/java/com/spring/Boot/Student_system_managment
├── config/          # Security, password encoder and auditing configuration
├── controller/      # REST controllers (Auth, Student, Department, Course)
├── dto/             # Request and response DTOs
├── Entity/          # JPA entities (Student, Address, Department, Course)
├── exception/       # Custom exceptions and global exception handler
├── payLoad/         # Generic ApiResponse wrapper
├── Projection/      # Spring Data projections
├── repository/      # JPA repositories (+ custom implementation)
├── security/        # JWT service, JWT filter, UserDetailsService
├── service/         # Business logic (interfaces + implementations)
└── specification/   # JPA Specifications for dynamic search
```

---

## Database Design

```
Department 1 ──────< Student >────── * Course        (via student_courses join table)
                        │
                        1
                        │
                     Address
```

- **Student**: `id`, `name`, `email`, `password`, `course`, audit fields, `deleted`, `version`
- **Address**: `id`, `city`, `state`, `country`
- **Department**: `id`, `departmentName`
- **Course**: `id`, `courseName`, `duration`, `fees`, `instructorName`

Tables are created automatically by Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

---

## Getting Started

### Prerequisites

- JDK 21 or higher
- MySQL 8+ running locally
- Maven (or just use the included `mvnw` wrapper)

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/Student-system-managment.git
cd Student-system-managment
```

### 2. Create the database

```sql
CREATE DATABASE Student_Management;
```

### 3. Configure the application

Open `src/main/resources/application.properties` and set your own values:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Student_Management
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>

jwt.secret=<a-long-random-secret-key>
jwt.expiration=86400000
```

> **Security tip:** never commit real passwords or secrets to GitHub. Use environment variables instead, for example `SPRING_DATASOURCE_PASSWORD` and `JWT_SECRET`. Spring Boot picks them up automatically.

### 4. Run the application

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The server starts at **http://localhost:8080**.

### 5. Run the tests

```bash
./mvnw test
```

---

## Authentication Flow

1. Register a student with `POST /api/students` (this endpoint is public).
2. Log in with `POST /auth/login` to receive a JWT token.
3. Send the token with every protected request:

```
Authorization: Bearer <your-token>
```

**Login request**

```json
{
  "email": "john@example.com",
  "password": "John@1234"
}
```

**Login response**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## API Endpoints

### Auth

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/auth/login` | Login and get a JWT token | Public |

### Students (`/api/students`)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/students` | Create a student (public) |
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get a student by ID |
| GET | `/api/students/email/{email}` | Get a student by email |
| PUT | `/api/students/{id}` | Full update |
| PATCH | `/api/students/{id}` | Partial update |
| DELETE | `/api/students/{id}` | Delete by ID |
| DELETE | `/api/students/email/{email}` | Delete by email |
| GET | `/api/students/count?courseName=` | Count students in a course |
| GET | `/api/students/search?name=&course=` | Search by name and course |
| GET | `/api/students/search/specification?name=&email=&course=` | Dynamic search (all params optional) |
| GET | `/api/students/native/email/{email}` | Native SQL query by email |
| GET | `/api/students/native/id/{id}` | Native SQL query by ID |
| GET | `/api/students/sort` | All students sorted by name |
| GET | `/api/students/page?page=0&size=5&sort=name` | Paginated list |
| GET | `/api/students/projection/dto` | Only name and email (DTO projection) |
| GET | `/api/students/custom` | Custom repository query |
| GET | `/api/students/custom/department?departmentName=` | Students by department |
| GET | `/api/students/header` | Reads the `x-client-version` request header |

### Departments (`/api/departments`)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/departments` | Create a department |
| GET | `/api/departments` | Get all departments |
| GET | `/api/departments/{id}` | Get a department by ID |

### Courses (`/api/courses`)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/courses` | Create a course |
| GET | `/api/courses` | Get all courses |
| GET | `/api/courses/{id}` | Get a course by ID |

All endpoints except `/auth/login` and `/api/students` require a valid JWT token.

---

## Sample Requests

**Create a department**

```json
POST /api/departments
{
  "departmentName": "Computer Science"
}
```

**Create a course**

```json
POST /api/courses
{
  "courseName": "Java Full Stack",
  "duration": "6 months",
  "fees": 25000.0,
  "instructorName": "Rahul Sharma"
}
```

**Create a student**

```json
POST /api/students
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "John@1234",
  "address": {
    "city": "Ludhiana",
    "state": "Punjab",
    "country": "India"
  },
  "departmentId": 1,
  "courseIds": [1]
}
```

**Standard response format**

```json
{
  "success": true,
  "timestamp": "2026-10-08T12:30:00",
  "status": 201,
  "message": "Student saved successfully",
  "data": { }
}
```

**Validation error example**

```json
{
  "success": false,
  "timestamp": "2026-10-08T12:31:00",
  "status": 400,
  "message": "validation failed",
  "data": {
    "password": "pleas enter a strong password e.g.  A@8&h1!hA%"
  }
}
```

### Password rules

Minimum 8 characters, with at least one uppercase letter, one lowercase letter, one digit and one special character from `@#$%^&!=+`.

---

## Error Handling

| Status | When |
|---|---|
| `400 Bad Request` | Validation failed |
| `404 Not Found` | Student or resource does not exist |
| `409 Conflict` | Duplicate email |

---

## Future Improvements

- Role-based access control (Admin / Student)
- Swagger / OpenAPI documentation
- Docker support
- Frontend (React / Angular) integration

---

## Author

**<Your Name>**

- GitHub: (https://github.com/<Vikash-sky>)

---

## License

This project is open source and available under the [MIT License](LICENSE).
