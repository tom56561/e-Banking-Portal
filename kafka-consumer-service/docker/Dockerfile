# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/kafka-consumer-service-0.0.1-SNAPSHOT.jar /app/kafka-consumer-service-0.0.1-SNAPSHOT.jar

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the app using this docker container
ENTRYPOINT ["java", "-jar", "/app/kafka-consumer-service-0.0.1-SNAPSHOT.jar"]