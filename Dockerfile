# Use the official OpenJDK image as a parent image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app
#VOLUME /tmp

# Copy the jar file into the container
COPY target/library-system-0.0.1-SNAPSHOT.jar library-system.jar

# Expose the port the application runs on
EXPOSE 8080

# Add pom.xml
ADD pom.xml pom.xml

# Run the jar file
ENTRYPOINT ["java", "-jar", "/library-system.jar"]