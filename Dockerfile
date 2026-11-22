FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/access-control-app-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
