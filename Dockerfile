FROM amazoncorretto:11
MAINTAINER JIE
COPY target/jie-0.0.1-SNAPSHOT.jar jie-app.jar
ENTRYPOINT ["java","-jar","/jie-app.jar"]