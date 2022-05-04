#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

#
# Package stage
#

FROM eclipse-temurin:17.0.3_7-jre-focal
COPY --from=build /home/app/target/rankbot-0.0.1-SNAPSHOT.jar /usr/local/lib/rankbot.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/rankbot.jar"]