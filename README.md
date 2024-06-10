# Library Management System RESTful APIs
A simple library management system built with Spring Boot. This project demonstrates the use of RESTful APIs to manage books and borrowers, and includes a CI/CD pipeline using GitHub Actions and Docker.

## Features

- Register new borrowers
- Register new books
- Get a list of all books
- Borrow a book
- Return a borrowed book

## Technologies Used

- Java 17
- Spring Boot
- PostgreSQL
- Docker
- GitHub Actions

## Getting Started

### Prerequisites

- JDK 17
- Maven
- Docker
- Docker Compose (optional for local multi-container setup)
- PostgreSQL

### Installation

1. **Clone the repository**

    ```bash
    git clone <REPOSITORY_URL>
    cd library-system
    ```

2. **Build the project**

    ```bash
    mvn clean install
    ```

3. **Run the application**

   You can run the application using Maven:

    ```bash
    mvn spring-boot:run
    ```

   Or you can build the Docker image and run it in a container:

    ```bash
    docker build -t library-system:latest .
    docker run -p 8080:8080 library-system:latest
    ```

4. **Database Configuration**

   Ensure your PostgreSQL database is running and update the `application.properties` with your database credentials:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

### Docker Compose (Optional)

The application run along with a PostgreSQL database using Docker Compose, create a `docker-compose.yml` file:

```yaml
version: '1.0'

services:
  app:
    image: library-system:latest
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/librarydb
      SPRING_DATASOURCE_USERNAME: yourusername
      SPRING_DATASOURCE_PASSWORD: yourpassword

  db:
    image: postgres:16
    environment:
      POSTGRES_DB: librarydb
      POSTGRES_USER: yourusername
      POSTGRES_PASSWORD: yourpassword
    ports:
      - "5432:5432"
```

### Assumptions
1. Borrower should have a unique id, a name and an email address.
2. Book should have a unique id, an ISBN number, a title, and an author.
3. Multiple copies of books with the same ISBN are allowed but have unique ids.
4. A book can be borrowed by only one borrower at a time.
5. ISBN number uniquely identifies a book in the following way:
   - 2 books with the same title and same author but different ISBN numbers are considered
   as different books
   - 2 books with the same ISBN numbers must have the same title and same author
6. Only one member is borrowing the same book (same book id) at a
time.
7. Unit tests & unit test coverage included using JUnit and Mockito
8. Validations are in place to ensure data integrity.
9. Best practices are followed to ensure Clean code, readability and maintainability.
10. Dockerfile included for containerization and deployment.
