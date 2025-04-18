# Stage 1: Build base setup
FROM maven:3.9.2-eclipse-temurin-17 AS base
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src

# Set the profile 'test' for testing
ENV SPRING_PROFILES_ACTIVE=test

# Stage 2: Run tests without building the image
FROM base AS test
RUN mvn clean verify -Ptest

# Stage 3: Build
FROM base AS build
ARG SKIP_TESTS=false
RUN if [ "$SKIP_TESTS" = "false" ]; then mvn clean verify -Ptest; fi
RUN mvn clean install -DskipTests

# Stage 4 : Runtime
FROM eclipse-temurin:17-jdk AS runtime
WORKDIR /app
RUN mkdir -p /app/data
COPY --from=build /app/target/product-app.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "product-app.jar"]