FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/MongoDB_MicroService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
