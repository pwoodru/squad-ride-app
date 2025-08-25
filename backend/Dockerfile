FROM openjdk:17-jdk-slim

WORKDIR /app

# Install curl and bash for health checks and Maven wrapper compatibility
RUN apt-get update && apt-get install -y curl bash && rm -rf /var/lib/apt/lists/*

# Copy Maven wrapper and pom.xml
COPY mvnw* ./
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies using bash for compatibility
RUN bash ./mvnw dependency:go-offline -B

# Expose port
EXPOSE 8080

# Use Maven to run with hot reload (no JAR building)
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-Dspring.profiles.active=docker'"]
