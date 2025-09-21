# Use Oracle JDK 17 base image
FROM container-registry.oracle.com/java/jdk:17

# Set working directory inside container
WORKDIR /app

# Copy the built JAR from target folder
COPY target/banking-transaction-system-1.0-SNAPSHOT.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
