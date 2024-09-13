FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/scg-1.0.0.jar /app/scg.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "scg.jar"]
