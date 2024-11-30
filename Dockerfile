# AI generated dockerfile -mo
# Use the official Amazon Corretto 17 base image for building the project
FROM amazoncorretto:17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Accept build arguments
ARG KEY

# Set the environment variable inside the container
ENV KEY=${KEY}

# Copy the Gradle build files into the container
COPY build.gradle.kts settings.gradle.kts /app/  

# Copy Gradle wrapper (if you have it)
COPY gradlew gradlew.bat /app/
COPY gradle /app/gradle

# Download dependencies (cached unless build.gradle.kts changes)
RUN ./gradlew build --no-daemon

# Copy the Kotlin source code into the container
COPY src /app/src

# Build the project and generate the JAR file
RUN ./gradlew build --no-daemon

# Use Amazon Corretto 17 for the runtime (lighter final image)
FROM amazoncorretto:17

# Set the working directory for the final image
WORKDIR /app

# Copy the JAR file from the builder stage to the final image
COPY --from=builder /app/build/libs/mealPrepApp-1.0-SNAPSHOT.jar /app/mealPrepApp-1.0-SNAPSHOT.jar

# Expose the port (change if necessary)
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/mealPrepApp-1.0-SNAPSHOT.jar"]
