FROM openjdk:17
EXPOSE 8080
ADD target/booking-0.0.1-SNAPSHOT.jar booking-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/booking-0.0.1-SNAPSHOT.jar"]