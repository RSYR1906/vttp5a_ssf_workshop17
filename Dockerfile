# Base image with JDK used to build and run the java application
FROM maven:3.9.9-eclipse-temurin-23

# Labelling the dockerfile with some information
LABEL description="This is VTTP5 SSF Workshop 17"
LABEL name="vttp5a-ssf-workshop17"

ARG APP_DIR=/app

# Directory where the source code will reside
WORKDIR ${APP_DIR}

# Copy the required files and/or directories into the image
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY src src
COPY .mvn .mvn

# Package the application
RUN mvn clean package -Dmaven.test.skip=true

# Set server port
ENV SERVER_PORT=8080

# Expose the application port
EXPOSE ${SERVER_PORT}

# Run the application
ENTRYPOINT ["java", "-jar", "target/Workshop-17-0.0.1-SNAPSHOT.jar", "--server.port=${SERVER_PORT}"]