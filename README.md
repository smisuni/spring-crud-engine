# 🚀 Spring Boot REST API for CRUD Operations using H2 Persistent Storage

![Build Status](https://github.com/smisuni/spring-crud-engine/actions/workflows/build.yml/badge.svg)
[![codecov](https://codecov.io/gh/smisuni/spring-crud-engine/branch/main/graph/badge.svg)](https://codecov.io/gh/smisuni/spring-crud-engine)
![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)
![License](https://img.shields.io/github/license/smisuni/spring-crud-engine.svg)

This project demonstrates how to build a Spring Boot REST API with essential CRUD operations.

## 🛠️ Prerequisites
- **Git** (2.0+)
- **Docker** (20.10+)

---

## 🖥️ Running the Application Locally

### Cloning the Repository
Clone the repository to your local machine:
```shell
git clone https://github.com/smisuni/spring-crud-engine.git
cd spring-crud-engine/
```

### Building the Docker Image
Run the following command to build the Docker image:
```shell
docker build -t spring-crud-engine .
```

### Running the Docker Container
Start the container and expose it to port 8080:
```shell
docker run -p 8080:8080 -v /data:/app/data spring-crud-engine
```
**Note**: 
- When running via Docker, the database is persisted in a mounted volume `/data` on host. This ensures that data is not lost when the container stops or restarts.
- For testing purposes, the application uses an in-memory H2 database, meaning no data is persisted between test runs.

### 🧪 Running Unit and Integration Tests
A dedicated application profile named `test` has been created to run with an in-memory H2 database for fast and isolated testing.
To run tests inside a disposable container without requiring Java or Maven installed locally, use the following command with Docker Compose:
```shell
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```
This will execute all tests defined in the project and generate coverage reports at `target/site/jacoco/index.html`.

---

## 🗄️ Database

- **Type**: H2 (File based, persistent)
- **Persistence**: Data is saved locally and remains even after the app stops and restarts.
- **Storage Location**: Database file is created at `./data/testdb.mv.db` inside the project folder.
- **In-memory Database for Testing**: An H2 in-memory database is used for unit and integration tests, automatically initialized and cleared after each run.
- **H2 Console**:
    - URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - JDBC URL: `jdbc:h2:file:./data/testdb`
    - Username: `username`
    - Password: `password`

These credentials can be modified inside `application.properties`.

---

## 📘 API Documentation

Swagger UI is available at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 🧪 Testing with Swagger

To test API endpoints:

1. Navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in a browser.
2. Expand the desired endpoint.
3. Click "Try it out" to input parameters and execute requests.
4. View responses directly within the interface.

---

## ✅ CI/CD

This project uses [GitHub Actions](https://github.com/features/actions) for continuous integration. Every push or pull request to the main branch triggers:

- **Install dependencies**
- **Build the application**
- **Run unit and integration tests**
- **Upload code coverage report**

See `.github/workflows/build.yml` for details.

---

## 📦 Key Dependencies: 
The following dependencies are used in this project:
- **Lombok**: For reducing boilerplate code.
- **Spring Boot Starter Web**: For building web applications with Spring Boot.
- **JaCoCo** : For code coverage.
- **Mapstruct**: For DTO entity mapping.
- **Swagger UI**: For generating interactive API documentation.
- **Jakarta Validation**: For input validation and enforcing constraints on Java objects.
