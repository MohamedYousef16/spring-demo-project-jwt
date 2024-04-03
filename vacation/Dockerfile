FROM openjdk:17-jdk

WORKDIR /app

COPY  target/docker_spring.jar  /app/demo.jar

EXPOSE 8080

CMD [ "java","-jar","demo.jar" ]