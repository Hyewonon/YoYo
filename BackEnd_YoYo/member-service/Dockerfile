FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/member-service-1.0.0.jar /app/member-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "member-service.jar"]
