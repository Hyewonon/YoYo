FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/payment-service-1.0.0.jar /app/payment-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "payment-service.jar"]
