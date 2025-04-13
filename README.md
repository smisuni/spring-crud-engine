# 🚀 Spring Boot REST API with In-Memory Cache

This project demonstrates a Spring Boot REST API with in-memory caching for improved performance.

## 🛠️ Prerequisites
- **Git** (2.0+)
- **Docker** (20.10+)

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
docker run -p 8080:8080 spring-crud-engine
```

### 🧪 Running Unit and Integration Tests
To run tests inside a disposable container without requiring Java or Maven installed locally, use the following command with Docker Compose:
```shell
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```
This will execute all tests defined in the project and generate coverage reports at `target/site/jacoco/index.html`.

## 📘 API Documentation

Swagger UI is available at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 🧪 Testing with Swagger

To test API endpoints:

1. Navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in a browser.
2. Expand the desired endpoint.
3. Click "Try it out" to input parameters and execute requests.
4. View responses directly within the interface.

## 📦 Key Dependencies: 
The following dependencies are used in this project:
- **Lombok**: For reducing boilerplate code.
- **Spring Boot Starter Web**: For building web applications with Spring Boot.
- **JaCoCo** : For code coverage.
- **Mapstruct**: For DTO entity mapping.
- **Swagger UI**: For generating interactive API documentation.
