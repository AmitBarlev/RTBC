# Use the official Maven image to build the application
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Use a smaller base image for the final application image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/rtbc-0.0.1-SNAPSHOT.jar rtbc.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "rtbc.jar"]