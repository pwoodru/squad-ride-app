FROM openjdk:17-jdk-slim

WORKDIR /app

# Install bash and curl (required for mvnw)
RUN apt-get update && apt-get install -y bash curl && rm -rf /var/lib/apt/lists/*

# Copy Maven wrapper and pom.xml
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .

# Convert mvnw to Unix line endings (in case it's CRLF)
RUN sed -i 's/\r$//' mvnw

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/squad-ride-backend-1.0.0.jar"]