FROM maven:latest as build
COPY . rankbot
WORKDIR rankbot
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:20-jre-jammy as final
LABEL owner="ahianf"
COPY --from=build rankbot/target/rankbot-0.9.0-BETA.jar /usr/local/rankbot-0.9.0-BETA.jar
ENTRYPOINT ["java", "-jar", "/usr/local/rankbot-0.9.0-BETA.jar"]