# Use Java runtime image
FROM eclipse-temurin:22-jdk-jammy

# Create working directory inside container
WORKDIR /app

# Copy jar from target folder into container
COPY target/*.jar app.jar

# Expose spring boot port
EXPOSE 8080

# Start application
ENTRYPOINT ["java","-jar","app.jar"]
