FROM amazoncorretto:17
MAINTAINER JIE
COPY target/jie-0.0.1-SNAPSHOT.jar jie-app.jar
ENTRYPOINT ["java","-jar","/jie-app.jar"]
EXPOSE 8080