FROM openjdk:22-jdk-slim
WORKDIR /app
COPY build/libs/TaskManager-0.0.1-SNAPSHOT.jar /app/TaskManager.jar
ENTRYPOINT ["java", "-jar", "/app/TaskManager.jar"]
EXPOSE 8080