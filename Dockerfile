# Stage 1: Build
FROM maven:3.9.9-amazoncorretto-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy Maven configuration and source code into the working directory
COPY pom.xml .
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime image
COPY --from=builder /app/target/sitekeeper-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
