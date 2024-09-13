FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/notification-service-1.0.0.jar /app/notification-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
