
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/minipay-psp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

