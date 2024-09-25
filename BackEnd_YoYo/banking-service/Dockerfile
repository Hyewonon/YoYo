FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/banking-service-1.0.0.jar /app/banking-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "banking-service.jar"]
