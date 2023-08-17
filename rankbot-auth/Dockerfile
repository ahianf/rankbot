FROM maven:latest as build
COPY . rankbot-auth
WORKDIR rankbot-auth
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:20-jre-jammy as final
LABEL owner="ahianf"
COPY --from=build rankbot-auth/target/rankbot-auth-1.0.0-SNAPSHOT.jar /usr/local/rankbot-auth-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/local/rankbot-auth-1.0.0-SNAPSHOT.jar"]