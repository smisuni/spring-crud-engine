# 🚀 Spring Boot REST API with In-Memory Cache

This project demonstrates a Spring Boot REST API with in-memory caching for improved performance.

## 🛠️ Prerequisites
- Java 17+
- Maven 3.8+
- Git
- IntelliJ

## 🔧 Setup
Download Repository and clone it to your local machine:
```shell
git clone https://github.com/smisuni/spring-crud-engine.git
cd spring-crud-engine/
```

### Building the project

Use Maven to resolve dependencies and build the project:
```shell
mvn clean package
```
### Running the project

1. Open the project in IntelliJ IDEA.
2. Navigate to the main application class located in src/main/java/.
3. Run the main class to start the Spring Boot application.

### Running Unit Tests
Use Maven to perform unit testing and code coverage using JaCoCo.
```shell
mvn clean install
```
Code coverage reports are available at `target/site/jacoco/index.html`.

## 📘 API Documentation

Swagger UI is available at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🧪 Testing with Swagger

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
