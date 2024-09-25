FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/config-server-1.0.0.jar /app/config-server.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "config-server.jar"]
