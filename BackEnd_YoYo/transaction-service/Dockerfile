FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/transaction-service-1.0.0.jar /app/transaction-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]
