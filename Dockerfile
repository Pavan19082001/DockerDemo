FROM openjdk:17-jdk-slim

COPY ./target/Assignment-0.0.1-SNAPSHOT.jar Assignment-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "Assignment-0.0.1-SNAPSHOT.jar"]