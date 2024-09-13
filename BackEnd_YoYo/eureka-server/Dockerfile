FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/eureka-server-1.0.0.jar /app/eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
