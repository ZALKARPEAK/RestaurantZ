FROM openjdk:21
LABEL maintainer="javaguides.net"
ADD target/*.jar Restaurant.jar
ENTRYPOINT ["java", "-jar", "RestaurantZ.jar"]