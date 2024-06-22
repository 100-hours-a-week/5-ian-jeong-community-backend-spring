FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/community-0.0.1-SNAPSHOT.jar odop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "odop.jar"]