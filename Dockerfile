FROM openjdk:17 as buildstage
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package
COPY ./target/readingisgood-1.0.jar app.jar

FROM openjdk:17
COPY --from=buildstage /app/app.jar .
ENTRYPOINT ["java", "-jar", "app.jar"]