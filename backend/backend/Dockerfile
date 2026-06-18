# Faza 1: Build aplikacije pomoću Mavena i stabilne Temurin Jave 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Faza 2: Pokretanje aplikacije u ultra-laganom Alpine okruženju
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]