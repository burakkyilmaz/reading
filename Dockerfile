FROM openjdk:17-alpine

WORKDIR /app
COPY  target/reading*.jar /app/reading.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]