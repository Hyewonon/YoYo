FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/event-service-1.0.0.jar /app/event-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "event-service.jar"]
