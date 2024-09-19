FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/common-1.0.0-plain.jar /app/common.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "common.jar"]
