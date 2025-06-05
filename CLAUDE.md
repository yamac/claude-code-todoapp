# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

**Note**: This documentation is written in English for consistency and clarity.

## Project Overview

This is a TODO application built with:
- **Language**: Kotlin
- **Framework**: Spring Boot 3
- **Build Tool**: Gradle (Kotlin DSL)
- **Database**: H2 (in-memory database)
- **ORM**: MyBatis
- **View Technology**: Thymeleaf
- **CSS Framework**: Bootstrap 5.3.3
- **Java Version**: 17 or higher

## Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/todo/
│   │       ├── TodoApplication.kt
│   │       ├── controller/
│   │       │   ├── TodoController.kt      # REST API controller
│   │       │   └── WebController.kt       # Web UI controller
│   │       ├── service/
│   │       │   └── TodoService.kt
│   │       ├── mapper/
│   │       │   └── TodoMapper.kt
│   │       └── model/
│   │           └── Todo.kt
│   └── resources/
│       ├── application.yml
│       ├── schema.sql
│       ├── static/
│       │   └── css/
│       │       └── style.css
│       ├── templates/
│       │   ├── layout.html               # Base layout template
│       │   ├── index.html                # Todo list page
│       │   └── form.html                 # Todo form page
│       └── mapper/
│           └── TodoMapper.xml
└── test/
    └── kotlin/
        └── com/example/todo/
```

## Common Commands

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test

# Clean build artifacts
./gradlew clean

# Generate executable JAR
./gradlew bootJar

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## Database Configuration

- H2 Console URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (empty)

## Web UI Routes

- `GET /` - Home page showing todo list
- `GET /todo/new` - Form to create a new todo
- `GET /todo/{id}/edit` - Form to edit existing todo
- `POST /todo` - Submit new todo
- `POST /todo/{id}` - Update existing todo
- `POST /todo/{id}/delete` - Delete todo
- `POST /todo/{id}/toggle` - Toggle todo completion status

## API Endpoints

- `GET /api/todos` - List all todos
- `GET /api/todos/{id}` - Get a specific todo
- `POST /api/todos` - Create a new todo
- `PUT /api/todos/{id}` - Update a todo
- `DELETE /api/todos/{id}` - Delete a todo

## Key Dependencies

- spring-boot-starter-web
- spring-boot-starter-thymeleaf
- spring-boot-starter-data-jdbc
- mybatis-spring-boot-starter
- h2 database
- spring-boot-starter-test
- kotlin-reflect
- jackson-module-kotlin

## Development Guidelines

1. Use Kotlin idioms (data classes, null safety, extension functions)
2. Follow Spring Boot conventions for REST endpoints
3. Use MyBatis XML mappers for complex queries
4. Keep business logic in service layer
5. Use constructor injection for dependencies
6. Write unit tests for services and integration tests for controllers
7. Use Bootstrap components and utilities for UI styling
8. Use Thymeleaf fragments for reusable UI components
9. Keep custom CSS minimal, preferring Bootstrap classes

## MyBatis Configuration

- Mapper interfaces should be annotated with `@Mapper`
- XML mapper files should be placed in `resources/mapper/`
- Use type aliases for model classes in MyBatis configuration
- Enable camel case mapping for database columns

## Testing Approach

- Unit tests: Use MockK for mocking dependencies
- Integration tests: Use `@SpringBootTest` with `@AutoConfigureMockMvc`
- Database tests: Use `@DataJdbcTest` with H2 in-memory database