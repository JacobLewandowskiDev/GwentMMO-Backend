# We only need the JRE (Runtime) since the JAR is already compiled
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# 1. Create a non-root user and group for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 2. Copy the JAR from your local target folder directly into the image
# We do this as root first to ensure we can set permissions
COPY target/gwent-app.jar gwent-app.jar

# 3. Give the non-root user ownership of the application file
RUN chown appuser:appgroup gwent-app.jar

# 4. Switch to the non-root user
USER appuser

# Document the port
EXPOSE 8080

# 5. Java 21 optimization flags for container memory management
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "gwent-app.jar"]