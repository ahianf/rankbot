## Build
#FROM maven:3.8.6-eclipse-temurin-19 AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package -DskipTests -e

#Imagen
FROM eclipse-temurin:19-jre-jammy
COPY ./target/rankbot-0.9.0-BETA.jar /usr/local/lib/rankbot.jar
#COPY --from=build /home/app/target/rankbot-0.9.0-BETA.jar /usr/local/lib/rankbot.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/rankbot.jar"]