FROM amazoncorretto:17-alpine-jdk
MAINTAINER JIE
COPY target/jie-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/jie-app.jar"]