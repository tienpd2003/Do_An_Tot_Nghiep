FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .
RUN mvn clean package -pl auth -am -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/auth/target/auth-1.0.0.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]