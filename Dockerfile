FROM openjdk:17-jdk-slim

ADD target/roll-down-simulator-*.jar /app.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000","-jar","/app.jar"]

EXPOSE 8080
EXPOSE 8443
EXPOSE 8009
EXPOSE 8008
