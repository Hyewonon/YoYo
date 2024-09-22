FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/eureka-1.0.0.jar /app/scg.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "scg.jar"]
