FROM openjdk:21-jdk-alpine
EXPOSE 8080
VOLUME /tmp
COPY build/libs/*.jar demo.jar
ENTRYPOINT ["java", "-jar", "/demo.jar"]