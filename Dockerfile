FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/readingisgood-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]