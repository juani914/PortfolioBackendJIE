FROM amazoncorretto:17-alpine-jdk
MAINTAINER JIE
COPY jie-0.0.1-SNAPSHOT
ENTRYPOINT ["java","-jar","/jie-app.jar"]