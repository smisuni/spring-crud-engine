# Spring Boot REST API with In-Memory Cache

This project demonstrates a Spring Boot REST API with in-memory caching for improved performance.

## Prerequisites:
- Java (JDK 17+)
- Maven
- IntelliJ

## Setup
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

## Dependencies: 
The following dependencies are used in this project:
- Lombok: For reducing boilerplate code.
- spring-boot-starter-web: For building web applications with Spring Boot.
- jacoco-maven-plugin : For code coverage using JaCoCo.