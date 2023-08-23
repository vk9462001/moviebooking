FROM openjdk:8
EXPOSE 8080
ADD target/moviebook.jar moviebook.jar
ENTRYPOINT ["java","-jar","/moviebook.jar"]