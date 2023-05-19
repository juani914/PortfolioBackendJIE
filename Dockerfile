FROM amazoncorretto:17-alpine-jdk //de que imagen partimos
MAINTAINER JIE //quien es el dueño
COPY target/jie-0.0.1-SNAPSHOT.jar jie-app.jar        //va a copiar el empaquetado github
ENTRYPOINT ["java","-jar","/jie-app.jar"]                                        //es la instruccion que se va a ejecutar primero