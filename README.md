# TODO Application

A simple TODO application built with Kotlin, Spring Boot 3, MyBatis, and H2 Database.

## Technologies Used

- Kotlin
- Spring Boot 3
- Gradle (Kotlin DSL)
- MyBatis
- H2 Database (in-memory)
- Thymeleaf (Web UI)
- Bootstrap 5.3.3 (CSS Framework)
- JUnit 5 & MockK for testing

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 7.x or higher (optional, project includes Gradle wrapper)

### Running the Application

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### Web Interface

Access the web interface at: `http://localhost:8080`

The web UI provides a modern, responsive interface built with Bootstrap for:
- Viewing all TODOs in a clean table layout
- Creating new TODOs with a form interface
- Editing existing TODOs
- Marking TODOs as completed with visual feedback
- Deleting TODOs with confirmation

### H2 Console

Access the H2 database console at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/todos` | Get all todos |
| GET | `/api/todos/{id}` | Get a specific todo |
| POST | `/api/todos` | Create a new todo |
| PUT | `/api/todos/{id}` | Update a todo |
| DELETE | `/api/todos/{id}` | Delete a todo |

### Example Requests

#### Create a TODO
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My new todo",
    "description": "Description of the todo",
    "completed": false
  }'
```

#### Get all TODOs
```bash
curl http://localhost:8080/api/todos
```

#### Update a TODO
```bash
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated todo",
    "description": "Updated description",
    "completed": true
  }'
```

#### Delete a TODO
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

## Running Tests

```bash
./gradlew test
```

## Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/todo/
│   │       ├── TodoApplication.kt
│   │       ├── controller/
│   │       │   ├── TodoController.kt      # REST API
│   │       │   └── WebController.kt       # Web UI
│   │       ├── service/
│   │       ├── mapper/
│   │       └── model/
│   └── resources/
│       ├── application.yml
│       ├── schema.sql
│       ├── static/
│       │   └── css/
│       │       └── style.css
│       ├── templates/
│       │   ├── layout.html
│       │   ├── index.html
│       │   └── form.html
│       └── mapper/
└── test/
    └── kotlin/
        └── com/example/todo/
```